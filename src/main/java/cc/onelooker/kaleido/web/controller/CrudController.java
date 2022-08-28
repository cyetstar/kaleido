package cc.onelooker.kaleido.web.controller;

import com.zjjcnt.common.core.dto.BaseDTO;
import com.zjjcnt.common.core.dto.CommonResult;
import com.zjjcnt.common.core.dto.PageParam;
import com.zjjcnt.common.core.dto.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.security.domain.CustomUserDetails;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Serializable;

/**
 * @Author xiadawei
 * @Date 2021-08-12 14:22:00
 * @Description TODO
 */
public abstract class CrudController<ID extends Serializable, D extends BaseDTO> extends BaseController {

    public static final String ACTION_CREATE = "create";
    public static final String ACTION_UPDATE = "update";

    protected abstract IBaseService<D> getService();

    @GetMapping("list")
    public String list(@ModelAttribute D dto,
                       PageParam pageParam,
                       Model model) {
        PageResult<D> pageData = doList(dto, pageParam, model);
        model.addAttribute("pageData", pageData);
        return getViewPath("list");
    }

    @GetMapping("view")
    public String view(ID id, Model model) {
        D dto = doView(id, model);
        model.addAttribute(dto);
        return getViewPath("view");
    }

    @ResponseBody
    @GetMapping(value = "view.json", produces = "application/json")
    public HttpEntity<CommonResult> viewJson(ID id) {
        return getHttpEntity(() -> {
            D dto = getService().findById(id);
            CommonResult commonResult = CommonResult.success(dto);
            return ResponseEntity.ok(commonResult);
        });
    }

    @GetMapping("create")
    public String create(@ModelAttribute D dto, Model model) {
        doAdd(dto, model);
        model.addAttribute("action", ACTION_CREATE);
        return getViewPath("form");
    }

    @PostMapping("create")
    public String create(D dto, RedirectAttributes redirectAttributes) {
        getService().insert(dto);
        redirectAttributes.addFlashAttribute("result", 0);
        return "redirect:list";
    }

    @ResponseBody
    @PostMapping(value = "create.json", produces = "application/json")
    public HttpEntity<CommonResult> createJson(D dto) {
        return getHttpEntity(() -> {
            getService().insert(dto);
            return ResponseEntity.ok(CommonResult.success(true));
        });
    }

    @GetMapping("update")
    public String update(ID id, Model model) {
        D dto = doEdit(id, model);
        model.addAttribute(dto);
        model.addAttribute("action", ACTION_UPDATE);
        return getViewPath("form");
    }

    @PostMapping("update")
    public String update(@ModelAttribute D dto, RedirectAttributes redirectAttributes) {
        getService().update(dto);
        redirectAttributes.addFlashAttribute("result", 0);
        return "redirect:list";
    }

    @ResponseBody
    @PostMapping(value = "update.json", produces = "application/json")
    public HttpEntity<CommonResult> updateJson(D dto) {
        return getHttpEntity(() -> {
            getService().update(dto);
            return ResponseEntity.ok(CommonResult.success(true));
        });
    }

    @PostMapping("delete")
    public String delete(@RequestParam(name = "id") ID[] ids, RedirectAttributes redirectAttributes) {
        for (ID id : ids) {
            getService().deleteById(id);
        }
        redirectAttributes.addFlashAttribute("result", 0);
        return "redirect:list";
    }

    @ResponseBody
    @PostMapping(value = "delete.json", produces = "application/json")
    public HttpEntity<CommonResult> deleteJson(@RequestParam(name = "id") ID[] ids) {
        return getHttpEntity(() -> {
            for (ID id : ids) {
                getService().deleteById(id);
            }
            return ResponseEntity.ok(CommonResult.success(true));
        });
    }

    protected PageResult<D> doList(D dto, PageParam pageParam, Model model) {
        pageParam.setSearchCount(true);
        return getService().page(dto, pageParam.toPage());
    }

    protected D doView(ID id, Model model) {
        return getService().findById(id);
    }

    protected void doAdd(D dto, Model model) {

    }

    protected D doEdit(ID id, Model model) {
        return getService().findById(id);
    }

    protected CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
