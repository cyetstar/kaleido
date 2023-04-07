package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;

/**
 * DO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.dto.business.ActorDTO
 */
@Data
@TableName("actor")
public class ActorDO implements IdEntity<Long> {
    private static final long serialVersionUID = 4617432770209705623L;

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
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "orders")
    private String orders;

    /**
     * 
     */
    @TableField(value = "originalname")
    private String originalname;

    /**
     * 
     */
    @TableField(value = "profile")
    private String profile;

    /**
     * 
     */
    @TableField(value = "role")
    private String role;

    /**
     * 
     */
    @TableField(value = "thumb")
    private String thumb;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
