package cc.onelooker.kaleido.dto.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-06-08 18:58:00
 * @Description TODO
 */
@Data
public class SysLogReadResp {

    private List<String> logs;
    private String fileName;
    private int lineNumber;
}
