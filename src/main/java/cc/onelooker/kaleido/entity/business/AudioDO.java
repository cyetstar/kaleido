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
 * @see cc.onelooker.kaleido.dto.business.AudioDTO
 */
@Data
@TableName("audio")
public class AudioDO implements IdEntity<Long> {
    private static final long serialVersionUID = 104046611822105719L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "channels")
    private String channels;

    /**
     * 
     */
    @TableField(value = "codec")
    private String codec;

    /**
     * 
     */
    @TableField(value = "language")
    private String language;

    /**
     * 
     */
    @TableField(value = "fileinfo_id")
    private Long fileinfoId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
