package cc.onelooker.kaleido.thread;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by cyetstar on 2021/1/14.
 */
@Data
@Slf4j
public abstract class AbstractEntityActionRunnable<T> extends AbstractActionRunnable {

    private int sleepSecond = 0;

    /**
     * 负责处理单条记录
     *
     * @param entity
     */
    protected abstract void processEntity(Map<String, String> params, T entity) throws Exception;

    protected abstract PageResult<T> page(Map<String, String> params, int pageNumber, int pageSize);

    protected String getMessage(T entity) {
        return entity.toString();
    }

    @Override
    public void innerRun(Map<String, String> params) {
        int pageNumber = 1;
        int pageSize = 1000;
        int num = 0;
        while (true) {
            PageResult<T> pageResult = page(params, pageNumber, pageSize);
            if (pageResult == null || pageResult.isEmpty()) {
                break;
            }
            long total = pageResult.isSearchCount() ? pageResult.getTotal() : -1;
            for (T entity : pageResult.getRecords()) {
                try {
                    if (entity == null) {
                        continue;
                    }
                    updateActionState(getMessage(entity), total, num++);
                    if (isStop()) {
                        break;
                    }
                    processEntity(params, entity);
                } catch (Exception e) {
                    processError(params, entity, e);
                } finally {
                    sleep();
                }
            }
            if (isStop()) {
                break;
            }
            pageNumber++;
        }
    }

    protected void updateActionState(String message, long total, int num) {
        // 保留一位小数
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String percent = decimalFormat.format((float) num * 100 / total);
        super.updateActionState(message, Float.valueOf(percent));
    }

    protected void processError(Map<String, String> params, T entity, Exception e) {
        log.error("【{}】>>> {} 执行发生错误，{}", getAction(), getMessage(entity), ExceptionUtil.getMessage(e));
        e.printStackTrace();
    }

    protected void sleep() {
        if (getSleepSecond() > 0) {
            ThreadUtil.sleep(getSleepSecond() * 1000L);
        }
    }

}
