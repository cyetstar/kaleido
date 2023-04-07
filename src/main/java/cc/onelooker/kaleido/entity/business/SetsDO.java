package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * DO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.dto.business.SetsDTO
 */
@Data
@TableName("sets")
public class SetsDO implements IdEntity<Long> {
    private static final long serialVersionUID = 3577179055024699118L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "category")
    private String category;

    /**
     * 
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * 
     */
    @TableField(value = "douban_result")
    private String doubanResult;

    /**
     * 
     */
    @TableField(value = "movie_size")
    private Integer movieSize;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "overview")
    private String overview;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
