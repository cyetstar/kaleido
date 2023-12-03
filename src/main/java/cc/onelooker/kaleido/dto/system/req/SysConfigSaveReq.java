package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 系统配置表请求对象
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("系统配置表请求对象")
public class SysConfigSaveReq {

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

    private String plexTvshowLibraryId;

    private String neteaseUrl;

    private String doubanApikey;
}
