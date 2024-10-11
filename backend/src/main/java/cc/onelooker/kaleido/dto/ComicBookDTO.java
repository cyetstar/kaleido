package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.ComicBookDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 漫画书籍DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see ComicBookDO
 */
@Data
public class ComicBookDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 6453652703730033483L;

    /**
     * 主键
     */
    private String id;

    /**
     * 系列id
     */
    private String seriesId;

    /**
     * 标题
     */
    private String title;

    /**
     * 原标题
     */
    private String originalTitle;

    /**
     * 卷号
     */
    private Integer bookNumber;

    /**
     * 排序号
     */
    private Integer sortNumber;

    /**
     * 页数
     */
    private Integer pageCount;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 番组计划编号
     */
    private String bgmId;

    /**
     * 封面页码
     */
    private Integer coverPageNumber;

    /**
     * 封面裁切数据
     */
    private String coverBoxData;

    /**
     * 加入时间
     */
    private Long addedAt;

    /**
     * 更新时间
     */
    private Long updatedAt;

    // ------ 非数据库表字段 -------

    private String web;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
