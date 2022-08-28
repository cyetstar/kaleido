package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.dto.SysUserDTO;
import cc.onelooker.kaleido.dto.SysUserRoleDTO;
import cc.onelooker.kaleido.dto.req.SysUserRegisterReq;
import cc.onelooker.kaleido.dto.resp.SysUserListByRoleIdResp;
import cc.onelooker.kaleido.service.SysRoleService;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.dto.CommonResult;
import com.zjjcnt.common.core.dto.PageParam;
import com.zjjcnt.common.core.dto.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.convert.SysUserConvert;
import cc.onelooker.kaleido.dto.SysRoleDTO;
import cc.onelooker.kaleido.service.SysUserRoleService;
import cc.onelooker.kaleido.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 用户表前端控制器
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController extends CrudController<Long, SysUserDTO> {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    protected IBaseService getService() {
        return sysUserService;
    }

    @Override
    protected PageResult<SysUserDTO> doList(SysUserDTO dto, PageParam pageParam, Model model) {
        PageResult<SysUserDTO> pageResult = super.doList(dto, pageParam, model);
        for (SysUserDTO sysUserDTO : pageResult.getRecords()) {
            List<SysRoleDTO> sysRoleDTOList = sysRoleService.listByUserId(sysUserDTO.getId());
            sysUserDTO.setSysRoleDTOList(sysRoleDTOList);
        }
        return pageResult;
    }

    @Override
    public String create(SysUserDTO dto, RedirectAttributes redirectAttributes) {
        if (!StringUtils.equals(dto.getPassword(), dto.getPassword2())) {
            throw new RuntimeException("两次密码不一致");
        }
        return super.create(dto, redirectAttributes);
    }

    @Override
    protected SysUserDTO doEdit(Long id, Model model) {
        SysUserDTO sysUserDTO = super.doEdit(id, model);
        List<SysUserRoleDTO> sysUserRoleDTOList = sysUserRoleService.listByUserId(id);
        Long[] roleIds = sysUserRoleDTOList.stream().map(SysUserRoleDTO::getRoleId).toArray(Long[]::new);
        sysUserDTO.setRoleIds(roleIds);
        return sysUserDTO;
    }

    @GetMapping("/setPassword")
    public String setPassword(Long id, Model model) {
        SysUserDTO sysUserDTO = doEdit(id, model);
        model.addAttribute("sysUserDTO", sysUserDTO);
        return getViewPath("setPassword");
    }

    @ResponseBody
    @PostMapping(value = "setPassword.json", produces = "application/json")
    public HttpEntity<CommonResult> setPassword(SysUserDTO dto) {
        return getHttpEntity(() -> {
            if (!StringUtils.equals(dto.getPassword(), dto.getPassword2())) {
                throw new RuntimeException("两次密码不一致");
            }
            sysUserService.updatePassword(dto);
            return ResponseEntity.ok(CommonResult.success(true));
        });
    }

    @ResponseBody
    @PostMapping(value = "lock.json", produces = "application/json")
    public HttpEntity<CommonResult> lock(Long id, boolean locked) {
        return getHttpEntity(() -> {
            sysUserService.lock(id, locked);
            return ResponseEntity.ok(CommonResult.success(true));
        });
    }

    @GetMapping("/register")
    public String register() {
        return getViewPath("register");
    }

    @PostMapping("/register")
    public String register(SysUserRegisterReq sysUserRegisterReq,
                           RedirectAttributes redirectAttributes) {
        SysUserDTO sysUserDTO = SysUserConvert.INSTANCE.convertToDTO(sysUserRegisterReq);
        sysUserService.register(sysUserDTO);
        redirectAttributes.addFlashAttribute("operatorCode", "0");
        return "redirect:/login";
    }

    @ResponseBody
    @GetMapping(value = "listByRoleId.json", produces = "application/json")
    public HttpEntity<CommonResult> listByRoleId(Long roleId) {
        return getHttpEntity(() -> {
            List<SysUserDTO> sysUserDTOList = sysUserService.listByRoleId(roleId);
            List<SysUserListByRoleIdResp> respList = Lists.newArrayList();
            for (SysUserDTO sysUserDTO : sysUserDTOList) {
                respList.add(SysUserConvert.INSTANCE.convertToListByRoleIdResp(sysUserDTO));
            }
            return ResponseEntity.ok(CommonResult.success(respList));
        });
    }
}