package cc.onelooker.kaleido.web.controller;

import com.google.common.collect.Sets;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.dto.CommonResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.utils.ApplicationContextHelper;
import com.zjjcnt.common.util.constant.Constants;
import cc.onelooker.kaleido.dto.SysResourceDTO;
import cc.onelooker.kaleido.service.SysResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 资源表前端控制器
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 */

@Controller
@RequestMapping("/sysResource")
public class SysResourceController extends CrudController<Long, SysResourceDTO> {

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    protected IBaseService getService() {
        return sysResourceService;
    }

    @ResponseBody
    @PostMapping(value = "reload.json", produces = "application/json")
    public HttpEntity<CommonResult> reload() {
        return getHttpEntity(() -> {
            Set<String> urls = getUrls();
            List<SysResourceDTO> sysResourceDTOList = sysResourceService.list(null);
            for (Iterator<SysResourceDTO> iterator = sysResourceDTOList.iterator(); iterator.hasNext(); ) {
                SysResourceDTO next = iterator.next();
                if (!urls.contains(next.getUrl())) {
                    sysResourceService.deleteById(next.getId());
                }
            }
            List<String> existUrls = sysResourceDTOList.stream().map(SysResourceDTO::getUrl).collect(Collectors.toList());
            for (String url : urls) {
                if (StringUtils.startsWith(url, "/error")) {
                    continue;
                }
                if (!existUrls.contains(url)) {
                    SysResourceDTO sysResourceDTO = new SysResourceDTO();
                    sysResourceDTO.setUrl(url);
                    sysResourceDTO.setCode(toCode(url));
                    String type = StringUtils.substringBefore(sysResourceDTO.getCode(), Constants.COLON);
                    String name = StringUtils.substringAfter(sysResourceDTO.getCode(), Constants.COLON);
                    sysResourceDTO.setType(Dictionary.getValue("zymc", type));
                    sysResourceDTO.setName(Dictionary.getValue("zycz", name));
                    sysResourceDTO.setMethod("GET");
                    sysResourceService.insert(sysResourceDTO);
                }
            }
            return ResponseEntity.ok(CommonResult.success(true));
        });
    }

    private String toCode(String url) {
        url = StringUtils.removeStart(url, Constants.SLASH);
        return StringUtils.replace(url, Constants.SLASH, Constants.COLON);
    }

    private Set<String> getUrls() {
        Set<String> urls = Sets.newLinkedHashSet();
        RequestMappingHandlerMapping mapping = ApplicationContextHelper.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (RequestMappingInfo requestMappingInfo : map.keySet()) {
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            for (String pattern : patterns) {
                pattern = StringUtils.removeEnd(pattern, ".json");
                urls.add(pattern);
            }
        }
        return urls;
    }
}