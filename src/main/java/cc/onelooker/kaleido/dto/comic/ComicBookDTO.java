package cc.onelooker.kaleido.dto.comic;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 漫画书籍DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see cc.onelooker.kaleido.entity.comic.ComicBookDO
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
     * 简介
     */
    private String summary;

    /**
     * 卷号
     */
    private Integer bookNumber;

    /**
     * 页数
     */
    private Integer pageCount;

    /**
     * 路径
     */
    private String path;

    /**
     * 封面
     */
    private String cover;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 番组计划编号
     */
    private String bgmId;

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
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
