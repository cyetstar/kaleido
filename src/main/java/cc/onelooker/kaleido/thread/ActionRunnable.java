package cc.onelooker.kaleido.thread;

/**
 * Created by cyetstar on 2021/1/27.
 */
public interface ActionRunnable extends Runnable {

    Action getAction();

    void stop();

    boolean isStop();

}
