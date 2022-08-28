package cc.onelooker.kaleido.web.controller;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.SysDictDTO;
import cc.onelooker.kaleido.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 字典表前端控制器
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */

@Controller
@RequestMapping("/sysDict")
public class SysDictController extends CrudController<Long, SysDictDTO> {

    @Autowired
    private SysDictService sysDictService;

    @Override
    protected IBaseService getService() {
        return sysDictService;
    }

}