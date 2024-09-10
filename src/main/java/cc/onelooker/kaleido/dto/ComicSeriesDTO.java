package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.ComicSeriesDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 漫画系列DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see ComicSeriesDO
 */
@Data
public class ComicSeriesDTO implements BaseDTO<String> {
    private static final long serialVersionUID = -2027321145543094030L;

    /**
     * 主键
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 原标题
     */
    private String originalTitle;

    /**
     * 年份
     */
    private String year;

    /**
     * 简介
     */
    private String summary;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 卷数
     */
    private Integer bookCount;

    /**
     * 评分
     */
    private Float rating;

    /**
     * 状态
     */
    private String status;

    /**
     * 路径
     */
    private String path;

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
    private String keyword;

    private String writerName;

    private String pencillerName;

    private List<String> tagList;

    private List<String> alternateTitleList;

    private List<AuthorDTO> writerList;

    private List<AuthorDTO> pencillerList;

    private List<ComicBookDTO> bookList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public ComicBookDTO getBook(Integer volumeNumber) {
        if (CollectionUtils.isEmpty(bookList)) {
            return null;
        }
        return bookList.stream().filter(s -> volumeNumber.equals(s.getBookNumber())).findFirst().orElse(null);
    }
}
