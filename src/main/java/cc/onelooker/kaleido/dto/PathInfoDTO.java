package cc.onelooker.kaleido.dto;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.String;

/**
 * 目录信息DTO
 *
 * @author cyetstar
 * @date 2024-03-23 23:08:41
 * @see cc.onelooker.kaleido.entity.PathInfoDO
 */
@Data
public class PathInfoDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 1340514064419215452L;

    /**
     * 主键
     */
    private String id;

    /**
     * 路径
     */
    private String path;

    /**
     * IMDb编号
     */
    private String imdbId;

    /**
     * 豆瓣编号
     */
    private String doubanId;

    /**
     * TMDB编号
     */
    private String tmdbId;

    /**
     * 番组计划编号
     */
    private String bgmId;

    // ------ 非数据库表字段 -------


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
