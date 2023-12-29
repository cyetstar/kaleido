package cc.onelooker.kaleido.thread;

import lombok.Data;

/**
 * Created by cyetstar on 2021/1/8.
 */
@Data
public class ResultMessage {

    private boolean success;
    private String message;
    private String detailMessage;

}
