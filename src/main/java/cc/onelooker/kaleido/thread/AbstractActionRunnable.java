package cc.onelooker.kaleido.thread;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by cyetstar on 2021/1/14.
 */
@Slf4j
public abstract class AbstractActionRunnable implements ActionRunnable {

    private volatile boolean stopFlag = false;

    @Override
    public void stop() {
        stopFlag = true;
    }

    @Override
    public boolean isStop() {
        return stopFlag;
    }

    @Override
    public void run() {
        try {
            log.info("【{}】开始执行", getAction());
            ActionContext.start(getAction(), this);
            beforeRun();
            innerRun();
            afterRun();
            ActionContext.finish(getAction());
        } catch (Exception e) {
            log.error("【{}】执行发生错误，{}", getAction(), ExceptionUtil.getMessage(e));
        } finally {
            ActionContext.clear(getAction());
            log.info("【{}】执行完毕", getAction());
        }
    }

    protected void beforeRun() {
    }

    /**
     * 内部运行
     */
    abstract void innerRun();

    protected void afterRun() {
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
