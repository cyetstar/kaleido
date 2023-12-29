package cc.onelooker.kaleido.dto.tvshow;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 单季DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.entity.tvshow.TvshowSeasonDO
 */
@Data
public class TvshowSeasonDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 5019751674641049715L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 剧集id
     */
    private Long showId;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 季号
     */
    private Integer seasonIndex;

    /**
     * 海报
     */
    private String thumb;

    /**
     * 艺术图
     */
    private String art;

    /**
     * 加入时间
     */
    private Long addedAt;

    /**
     * 更新时间
     */
    private Long updatedAt;

    // ------ 非数据库表字段 -------

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
