package cc.onelooker.kaleido.dto.system.req;

import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2022-11-15 22:48:00
 * @Description TODO
 */
@Data
public class SysMenuInitReq {

    private String name;

    private String title;

    private String path;

    private String permission;

    private List<SysMenuInitReq> children;
}
