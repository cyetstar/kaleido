package cc.onelooker.kaleido.dto.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-10-08 15:24:00
 * @Description TODO
 */
@Data
public class ApiThreadViewResp {

    private String id;

    private String title;

    private String url;

    private String status;

    private String movieBasicId;

    private String comicSeriesId;

    private List<String> filenameList;

    private List<ApiThreadViewResp> threadList;

}
