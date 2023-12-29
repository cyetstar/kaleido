package cc.onelooker.kaleido.web.controller.common;

import cc.onelooker.kaleido.dto.common.req.ActionStartReq;
import cc.onelooker.kaleido.dto.common.req.ActionStopReq;
import cc.onelooker.kaleido.thread.Action;
import cc.onelooker.kaleido.thread.ActionContext;
import cc.onelooker.kaleido.thread.ActionRunnable;
import cc.onelooker.kaleido.thread.ActionState;
import cc.onelooker.kaleido.thread.movie.MovieCheckThreadStatusRunnable;
import cc.onelooker.kaleido.thread.movie.MovieReadNFORunnable;
import cc.onelooker.kaleido.thread.movie.MovieSyncPlexRunnable;
import cc.onelooker.kaleido.thread.movie.MovieUpdateSourceRunnable;
import com.zjjcnt.common.core.domain.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cyetstar on 2020/12/22.
 */
@RestController
@RequestMapping("/action")
public class ActionController {

    Map<Action, ActionRunnable> running = new ConcurrentHashMap<>();

    @PostMapping("start")
    public CommonResult<Boolean> start(@RequestBody ActionStartReq req) throws IOException {
        Action action = Action.valueOf(req.getAction());
        ActionRunnable actionRunnable = generateRunnable(action);
        new Thread(actionRunnable).start();
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

    private ActionRunnable generateRunnable(Action action) {
        switch (action) {
            case movieUpdateSource: {
                return new MovieUpdateSourceRunnable();
            }
            case movieCheckThreadStatus: {
                return new MovieCheckThreadStatusRunnable();
            }
            case movieReadNFO: {
                return new MovieReadNFORunnable();
            }
            case movieSyncPlex: {
                return new MovieSyncPlexRunnable();
            }
            default:
                return null;

        }
    }

}
