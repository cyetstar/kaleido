package cc.onelooker.kaleido.thread;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created by cyetstar on 2021/1/14.
 */
@Slf4j
public abstract class AbstractActionRunnable implements ActionRunnable {

    private volatile boolean stopFlag = false;

    private Map<String, String> params;

    @Override
    public void stop() {
        stopFlag = true;
    }

    @Override
    public boolean isStop() {
        return stopFlag;
    }

    @Override
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public void run() {
        try {
            log.info("【{}】开始执行", getAction());
            ActionContext.start(getAction(), this);
            if (!isStop()) {
                beforeRun(params);
            }
            if (!isStop()) {
                innerRun(params);
            }
            if (!isStop()) {
                afterRun(params);
            }
            ActionContext.finish(getAction());
        } catch (Exception e) {
            log.error("【{}】执行发生错误，{}", getAction(), ExceptionUtil.getMessage(e));
        } finally {
            ActionContext.clear(getAction());
            this.stopFlag = false;
            log.info("【{}】执行完毕", getAction());
        }
    }

    protected void beforeRun(@Nullable Map<String, String> params) {
    }

    /**
     * 内部运行
     */
    abstract void innerRun(@Nullable Map<String, String> params);

    protected void afterRun(@Nullable Map<String, String> params) {
    }

    public boolean isNeedRun() {
        return true;
    }

    protected void updateActionState(String message, Float percent) {
        ActionState actionState = ActionContext.get(getAction());
        if (actionState != null) {
            actionState.setMessage(message);
            actionState.setPercent(percent);
        }
        ActionContext.update(actionState);
    }

}
