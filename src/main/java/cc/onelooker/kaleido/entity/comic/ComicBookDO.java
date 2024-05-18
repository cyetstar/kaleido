package cc.onelooker.kaleido.entity.comic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 漫画书籍DO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see cc.onelooker.kaleido.dto.comic.ComicBookDTO
 */
@Data
@TableName("comic_book")
public class ComicBookDO implements IdEntity<String> {
    private static final long serialVersionUID = -309016882329185925L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 系列id
     */
    @TableField(value = "series_id")
    private String seriesId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 卷号
     */
    @TableField(value = "book_number")
    private Integer bookNumber;

    /**
     * 排序号
     */
    @TableField(value = "sort_number")
    private Integer sortNumber;

    /**
     * 页数
     */
    @TableField(value = "page_count")
    private Integer pageCount;

    /**
     * 路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private Long fileSize;

    /**
     * 番组计划编号
     */
    @TableField(value = "bgm_id")
    private String bgmId;

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
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
