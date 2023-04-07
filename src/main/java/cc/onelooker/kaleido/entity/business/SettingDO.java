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
 * @see cc.onelooker.kaleido.dto.business.SettingDTO
 */
@Data
@TableName("setting")
public class SettingDO implements IdEntity<Long> {
    private static final long serialVersionUID = 6961225211628166179L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "value")
    private String value;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
