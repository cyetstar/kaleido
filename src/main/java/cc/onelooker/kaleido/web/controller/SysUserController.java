package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.SysUserConvert;
import cc.onelooker.kaleido.dto.SysResourceDTO;
import cc.onelooker.kaleido.dto.SysRoleDTO;
import cc.onelooker.kaleido.dto.SysUserDTO;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.SysUserCreateResp;
import cc.onelooker.kaleido.dto.resp.SysUserInfoResp;
import cc.onelooker.kaleido.dto.resp.SysUserPageResp;
import cc.onelooker.kaleido.dto.resp.SysUserViewResp;
import cc.onelooker.kaleido.service.SysResourceService;
import cc.onelooker.kaleido.service.SysRoleService;
import cc.onelooker.kaleido.service.SysUserService;
import cc.onelooker.kaleido.utils.CurrentUserUtils;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.security.domain.CustomUserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表前端控制器
 *
 * @author cyetstar
 * @date 2022-11-10 16:46:50
 */

@Api(tags = "用户表")
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends AbstractCrudController<SysUserDTO> {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    protected IBaseService getService() {
        return sysUserService;
    }

    @GetMapping("page")
    @ApiOperation(value = "用户表分页查询")
    public CommonResult<PageResult<SysUserPageResp>> page(SysUserPageReq req, PageParam pageParam) {
        PageResult<SysUserPageResp> pageResult = super.doPage(req, pageParam, SysUserConvert.INSTANCE::convertToDTO, SysUserConvert.INSTANCE::convertToPageResp);
        for (SysUserPageResp resp : pageResult.getRecords()) {
            List<SysRoleDTO> sysRoleDTOList = sysRoleService.listByUserId(resp.getId());
            List<SysUserPageResp.SysRolePageResp> sysRoleList = Lists.newArrayList();
            for (SysRoleDTO sysRoleDTO : sysRoleDTOList) {
                sysRoleList.add(SysUserConvert.INSTANCE.convertToPageResp(sysRoleDTO));
            }
            resp.setSysRoleList(sysRoleList);
        }
        return CommonResult.success(pageResult);
    }

    @GetMapping("view")
    @ApiOperation(value = "用户表详细信息", hidden = true)
    public CommonResult<SysUserViewResp> view(Long id) {
        SysUserViewResp sysUserViewResp = super.doView(id, SysUserConvert.INSTANCE::convertToViewResp);
        List<SysRoleDTO> sysRoleDTOList = sysRoleService.listByUserId(id);
        sysUserViewResp.setRoleIds(sysRoleDTOList.stream().map(s -> String.valueOf(s.getId())).toArray(String[]::new));
        return CommonResult.success(sysUserViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增用户表", hidden = true)
    public CommonResult<SysUserCreateResp> create(@RequestBody SysUserCreateReq req) {
        if (StringUtils.isNotBlank(req.getPassword())) {
            req.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        return super.create(req, SysUserConvert.INSTANCE::convertToDTO, SysUserConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑用户表", hidden = true)
    public CommonResult<Boolean> update(@RequestBody SysUserUpdateReq req) {
        if (StringUtils.isNotBlank(req.getPassword())) {
            req.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        return super.update(req, SysUserConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除用户表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping("checkUnique")
    @ApiOperation(value = "校验用户名唯一性")
    public CommonResult<Boolean> checkUnique(String username) {
        SysUserDTO sysUserDTO = sysUserService.findByUsername(username);
        return CommonResult.success(sysUserDTO == null);
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping(value = "/info")
    public CommonResult<SysUserInfoResp> info() {
        CustomUserDetails currentUser = CurrentUserUtils.getUser();
        SysUserInfoResp sysUserInfoResp = new SysUserInfoResp();
        //注意：该接口返回的信息，前端会存储起来，返回身份证号不安全
        sysUserInfoResp.setUserId(currentUser.getUserId());
        sysUserInfoResp.setUsername(currentUser.getUsername());
        sysUserInfoResp.setName(currentUser.getName());
        sysUserInfoResp.setMobile(currentUser.getMobile());
        sysUserInfoResp.setDeptCode(currentUser.getDeptCode());
        sysUserInfoResp.setDeptName(currentUser.getDeptName());
        sysUserInfoResp.setSignup(currentUser.getSignUp());
        Collection<GrantedAuthority> authorities = currentUser.getAuthorities();
        sysUserInfoResp.setRoles(authorities.stream().map(s -> s.getAuthority())
                .collect(Collectors.toList()));
        List<SysResourceDTO> sysResourceDTOList = sysResourceService.listByUserId(currentUser.getUserId());
        sysUserInfoResp.setPermissions(sysResourceDTOList.stream().map(s -> s.getCode())
                .collect(Collectors.toList()));
        return CommonResult.success(sysUserInfoResp);
    }

    @ApiOperation("更新用户状态")
    @PostMapping("updateEnable")
    public CommonResult updateEnable(@RequestBody SysUserUpdateEnableReq req) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setEnabled(req.getEnable());
        sysUserDTO.setId(req.getUserId());
        boolean result = sysUserService.update(sysUserDTO);
        return result ? CommonResult.success("更新成功") : CommonResult.error(500, "更新失败");
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("修改密码")
    @PostMapping("modifyPassWord")
    public CommonResult<Boolean> modifyPassWord(@RequestBody @Validated SysUserModifyPassWordReq req) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setId(req.getId());
        sysUserDTO.setPassword(passwordEncoder.encode(req.getPassword()));
        return CommonResult.success(getService().update(sysUserDTO));
    }
}