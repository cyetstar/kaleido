package cc.onelooker.kaleido.web.controller;

import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author xiadawei
 * @Date 2022-02-03 19:46:00
 * @Description TODO
 */
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {

    @Autowired
    private Dictionary dictionary;

    @GetMapping("dictionary")
    public String dictionary() {
        return getViewPath("dictionary");
    }

    @PostMapping("loadDictionary.json")
    public HttpEntity<CommonResult> loadDictionary() {
        return getHttpEntity(() -> {
            dictionary.loadAll();
            return new ResponseEntity(CommonResult.success(true), HttpStatus.OK);
        });
    }

}
