package cc.onelooker.kaleido.thread;

import lombok.Data;

import java.util.List;

/**
 * Created by cyetstar on 2021/1/8.
 */
@Data
public class ProcessMessage extends ResultMessage {

    private boolean completed;
    private boolean infinity;
    private String percent;
    private String duration;
    private List<String> errorRecords;

}
