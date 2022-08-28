package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.dto.SysDictTypeDTO;
import cc.onelooker.kaleido.service.SysDictTypeService;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.dto.CommonResult;
import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.SysDictDTO;
import cc.onelooker.kaleido.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 字典表类型表前端控制器
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */

@Controller
@RequestMapping("/sysDictType")
public class SysDictTypeController extends CrudController<Long, SysDictTypeDTO> {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private Dictionary dictionary;

    @Override
    protected IBaseService getService() {
        return sysDictTypeService;
    }

    @Override
    protected SysDictTypeDTO doView(Long id, Model model) {
        SysDictTypeDTO sysDictTypeDTO = super.doView(id, model);
        List<SysDictDTO> sysDictDTOList = sysDictService.listByDictType(sysDictTypeDTO.getType());
        model.addAttribute("sysDictDTOList", sysDictDTOList);
        return sysDictTypeDTO;
    }

    @PostMapping("reload.json")
    public HttpEntity<CommonResult> reload() {
        return getHttpEntity(() -> {
            dictionary.loadAll();
            return new ResponseEntity(CommonResult.success(true), HttpStatus.OK);
        });
    }
}