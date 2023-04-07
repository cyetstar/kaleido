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
 * @see cc.onelooker.kaleido.dto.business.VideoDTO
 */
@Data
@TableName("video")
public class VideoDO implements IdEntity<Long> {
    private static final long serialVersionUID = 5804227046598522982L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "aspect")
    private String aspect;

    /**
     * 
     */
    @TableField(value = "codec")
    private String codec;

    /**
     * 
     */
    @TableField(value = "durationinseconds")
    private String durationinseconds;

    /**
     * 
     */
    @TableField(value = "height")
    private String height;

    /**
     * 
     */
    @TableField(value = "stereomode")
    private String stereomode;

    /**
     * 
     */
    @TableField(value = "width")
    private String width;

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
