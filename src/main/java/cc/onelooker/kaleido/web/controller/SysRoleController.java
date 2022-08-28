package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.service.SysRoleService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.dto.CommonResult;
import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.SysResourceDTO;
import cc.onelooker.kaleido.dto.SysRoleDTO;
import cc.onelooker.kaleido.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色表前端控制器
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */

@Controller
@RequestMapping("/sysRole")
public class SysRoleController extends CrudController<Long, SysRoleDTO> {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    protected IBaseService getService() {
        return sysRoleService;
    }

    @GetMapping("authorize")
    public String authorize(Long id, Model model) {
        List<SysResourceDTO> sysResourceDTOList = sysResourceService.list(null);
        List<SysResourceDTO> checkedList = sysResourceService.listByRoleId(id);
        List<Long> checkedIdList = checkedList.stream().map(SysResourceDTO::getId).collect(Collectors.toList());
        Collections.sort(sysResourceDTOList);
        Map<String, List<SysResourceDTO>> map = Maps.newTreeMap();
        for (SysResourceDTO sysResourceDTO : sysResourceDTOList) {
            List<SysResourceDTO> list = map.getOrDefault(sysResourceDTO.getType(), Lists.newArrayList());
            if (checkedIdList.contains(sysResourceDTO.getId())) {
                sysResourceDTO.setChecked(true);
            }
            list.add(sysResourceDTO);
            map.put(sysResourceDTO.getType(), list);
        }
        model.addAttribute("sysResourceMap", map);
        return getViewPath("authorize");
    }

    @ResponseBody
    @PostMapping(value = "authorize.json", produces = "application/json")
    public HttpEntity<CommonResult> authorize(Long id, HttpServletRequest request) {
        return getHttpEntity(() -> {
            String[] resourceIds = request.getParameterValues("resourceId");
            List<Long> resourceIdList = Arrays.stream(resourceIds).map(Long::parseLong).collect(Collectors.toList());
            sysRoleService.authorize(id, resourceIdList);
            return ResponseEntity.ok(CommonResult.success(true));
        });
    }

}