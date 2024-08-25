package cc.onelooker.kaleido.dto.req;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2022-11-15 22:48:00
 * @Description TODO
 */
@Data
public class SysMenuInitReq {

    private String name;

    private String title;

    private String path;

    private String permission;

    private String app;

    private List<SysMenuInitReq> children;
}
