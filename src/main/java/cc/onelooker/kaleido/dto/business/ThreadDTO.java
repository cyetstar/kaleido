package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.ThreadDO
 */
@Data
public class ThreadDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 8045538615905501399L;

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


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
