package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 电影发布记录DTO
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 * @see cc.onelooker.kaleido.entity.movie.PTThreadDO
 */
@Data
public class PTThreadDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 1665172171093787891L;

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Date createdAt;

    /**
     *
     */
    private String doubanId;

    /**
     *
     */
    private String imdb;

    /**
     *
     */
    private String links;

    /**
     *
     */
    private String publishDate;

    /**
     *
     */
    private Double rating;

    /**
     *
     */
    private Integer status;

    /**
     *
     */
    private Boolean thanks;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String type;

    /**
     *
     */
    private Date updatedAt;

    /**
     *
     */
    private String url;

    // ------ 非数据库表字段 -------

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title;
    }
}
