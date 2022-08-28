package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2022-04-26 10:43:00
 * @Description TODO
 */
@Data
public class SysUserRegisterReq {

    private String username;

    private String password;

    private String password2;

    private String name;

    private String mobile;
}
