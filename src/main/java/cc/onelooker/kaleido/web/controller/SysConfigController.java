package cc.onelooker.kaleido.web.controller;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统配置表前端控制器
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */

@Controller
@RequestMapping("/sysConfig")
public class SysConfigController extends CrudController<Long, SysConfigDTO> {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    protected IBaseService getService() {
        return sysConfigService;
    }
}