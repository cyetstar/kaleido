package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.PTThreadDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

import java.util.Date;

/**
 * 电影发布记录DO
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 * @see PTThreadDTO
 */
@Data
@TableName("pt_thread")
public class PTThreadDO implements IdEntity<Long> {
    private static final long serialVersionUID = -7188938832103850359L;

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
