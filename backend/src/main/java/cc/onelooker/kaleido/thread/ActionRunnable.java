package cc.onelooker.kaleido.thread;

import java.util.Map;

/**
 * Created by cyetstar on 2021/1/27.
 */
public interface ActionRunnable extends Runnable {

    Action getAction();

    void setParams(Map<String, String> params);

    void stop();

    boolean isStop();

}
