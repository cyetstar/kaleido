package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.web.socket.WebSocket;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.zjjcnt.common.core.utils.ApplicationContextHelper;
import com.zjjcnt.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cyetstar on 2021/1/27.
 */
@Slf4j
public class ActionContext {

    private static final Map<Action, ActionState> container = new ConcurrentHashMap<>();
    private static final Map<Action, ActionRunnable> runnableContainer = new ConcurrentHashMap<>();

    private static final WebSocket webSocket;

    static {
        webSocket = ApplicationContextHelper.getBean(WebSocket.class);
    }

    public static void start(Action action, ActionRunnable actionRunnable) {
        ActionState actionState = new ActionState();
        actionState.setAction(action);
        actionState.setMessage(null);
        actionState.setPercent(null);
        actionState.setRunning(true);
        actionState.setComplete(false);
        actionState.setStartTime(Instant.now());
        container.put(actionState.getAction(), actionState);
        runnableContainer.put(actionState.getAction(), actionRunnable);
        broadcast();
    }

    public static void finish(Action action) {
        ActionState actionState = get(action);
        if (actionState.getPercent() != null) {
            actionState.setPercent(100f);
        }
        actionState.setRunning(false);
        actionState.setComplete(true);
        container.put(actionState.getAction(), actionState);
        broadcast();
    }

    public static void update(ActionState actionState) {
        container.put(actionState.getAction(), actionState);
        broadcast();
    }

    public static void clear(Action action) {
        container.remove(action);
        runnableContainer.remove(action);
        broadcast();
    }

    public static ActionState get(Action action) {
        return container.get(action);
    }

    public static ActionRunnable getRunnable(Action action) {
        return runnableContainer.get(action);
    }

    public static void broadcast() {
        try {
            webSocket.sendAllMessage(JsonUtils.toJsonString(container.values()));
        } catch (Exception e) {
            log.error("广播发生错误，{}", ExceptionUtil.getMessage(e));
        }
    }

}
