package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.VideoDO
 */
@Data
public class VideoDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 2633489682779633956L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String aspect;

    /**
     * 
     */
    private String codec;

    /**
     * 
     */
    private String durationinseconds;

    /**
     * 
     */
    private String height;

    /**
     * 
     */
    private String stereomode;

    /**
     * 
     */
    private String width;

    /**
     * 
     */
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
