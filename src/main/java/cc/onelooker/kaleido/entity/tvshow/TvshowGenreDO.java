package cc.onelooker.kaleido.entity.tvshow;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.annotation.Crypto;

import java.lang.Long;
import java.lang.String;

/**
 * 剧集类型DO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.dto.tvshow.TvshowGenreDTO
 */
@Data
@TableName("tvshow_genre")
public class TvshowGenreDO implements IdEntity<Long> {
    private static final long serialVersionUID = -6444756879637274311L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标识
     */
    @TableField(value = "tag")
    private String tag;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
