package cc.onelooker.kaleido.entity.music;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 艺术家DO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see cc.onelooker.kaleido.dto.music.MusicArtistDTO
 */
@Data
@TableName("music_artist")
public class MusicArtistDO implements IdEntity<Long> {
    private static final long serialVersionUID = 4989797156146020017L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * MusicBrainz编号
     */
    @TableField(value = "musicbrainz_id")
    private String musicbrainzId;

    /**
     * 网易云音乐编号
     */
    @TableField(value = "netease_id")
    private String neteaseId;

    /**
     * 国家地区
     */
    @TableField(value = "area")
    private String area;

    /**
     * 简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 排序名称
     */
    @TableField(value = "title_sort")
    private String titleSort;

    /**
     * 封面图
     */
    @TableField(value = "thumb")
    private String thumb;

    /**
     * 加入时间
     */
    @TableField(value = "added_at")
    private Long addedAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private Long updatedAt;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
