package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 系统配置表请求对象
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("系统配置表请求对象")
public class SysConfigfindByKeysResp {

    private String movieLibraryPath;

    private String movieExcludePath;

    private String musicLibraryPath;

    private String musicExcludePath;

    private String plexUrl;

    private String plexToken;

    private String plexMovieLibraryId;

    private String plexMovieLibraryPath;

    private String plexMusicLibraryId;

    private String plexMusicLibraryPath;

    private String neteaseUrl;
}
