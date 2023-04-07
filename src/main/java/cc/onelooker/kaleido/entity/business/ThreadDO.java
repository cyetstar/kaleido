package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * DO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.dto.business.ThreadDTO
 */
@Data
@TableName("thread")
public class ThreadDO implements IdEntity<Long> {
    private static final long serialVersionUID = 3103315925080977802L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * 
     */
    @TableField(value = "imdb")
    private String imdb;

    /**
     * 
     */
    @TableField(value = "links")
    private String links;

    /**
     * 
     */
    @TableField(value = "publish_date")
    private String publishDate;

    /**
     * 
     */
    @TableField(value = "rating")
    private Double rating;

    /**
     * 
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 
     */
    @TableField(value = "thanks")
    private Boolean thanks;

    /**
     * 
     */
    @TableField(value = "title")
    private String title;

    /**
     * 
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "updated_at")
    private Date updatedAt;

    /**
     * 
     */
    @TableField(value = "url")
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
