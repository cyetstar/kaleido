package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.dto.req.ActionStartReq;
import cc.onelooker.kaleido.dto.req.ActionStopReq;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.thread.ActionContext;
import cc.onelooker.kaleido.thread.ActionRunnable;
import com.zjjcnt.common.core.domain.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by cyetstar on 2020/12/22.
 */
@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private List<ActionRunnable> actionRunnableList;

    @PostMapping("start")
    public CommonResult<Boolean> start(@RequestBody ActionStartReq req) throws IOException {
        Action action = Action.valueOf(req.getAction());
        if (ActionContext.getRunnable(action) != null) {
            return CommonResult.success(true);
        }
        Optional<ActionRunnable> runnableOptional = actionRunnableList.stream().filter(actionRunnable -> actionRunnable.getAction().equals(action)).findFirst();
        if (runnableOptional.isPresent()) {
            ActionRunnable actionRunnable = runnableOptional.get();
            actionRunnable.setParams(req.getParams());
            new Thread(actionRunnable).start();
        }
        return CommonResult.success(true);
    }

    @PostMapping("stop")
    public CommonResult<Boolean> stop(@RequestBody ActionStopReq req) throws IOException {
        Action action = Action.valueOf(req.getAction());
        ActionRunnable actionRunnable = ActionContext.getRunnable(action);
        if (actionRunnable != null) {
            actionRunnable.stop();
        }
        return CommonResult.success(true);
    }

}
