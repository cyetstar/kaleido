package cc.onelooker.kaleido.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 目录信息DO
 *
 * @author cyetstar
 * @date 2024-03-23 23:08:41
 * @see cc.onelooker.kaleido.dto.PathInfoDTO
 */
@Data
@TableName("path_info")
public class PathInfoDO implements IdEntity<String> {
    private static final long serialVersionUID = 6295175199855093379L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * IMDb编号
     */
    @TableField(value = "imdb_id")
    private String imdbId;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * TMDB编号
     */
    @TableField(value = "tmdb_id")
    private String tmdbId;

    /**
     * 番组计划编号
     */
    @TableField(value = "bgm_id")
    private String bgmId;


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
