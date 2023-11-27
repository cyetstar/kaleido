package cc.onelooker.kaleido.dto.system.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2022-11-16 12:37:00
 * @Description TODO
 */
@Data
public class SysMenuTreeResp {

    private Long id;

    private Long parentId;

    private String title;

    private String name;

    private String path;

    private String permission;

    private String isHidden;

    private List<SysMenuTreeResp> children;
}
