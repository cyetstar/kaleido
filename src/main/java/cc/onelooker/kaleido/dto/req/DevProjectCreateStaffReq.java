package cc.onelooker.kaleido.dto.req;

import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2022-06-13 16:28:00
 * @Description TODO
 */
@Data
public class DevProjectCreateStaffReq {

    private Long projectId;

    private List<Item> staffs;

    @Data
    public static class Item {
        private String id;

        private String type;

    }

}
