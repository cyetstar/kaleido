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
 * 剧集演职员DO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.dto.tvshow.TvshowActorDTO
 */
@Data
@TableName("tvshow_actor")
public class TvshowActorDO implements IdEntity<Long> {
    private static final long serialVersionUID = -7537395807809857706L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 原名
     */
    @TableField(value = "original_name")
    private String originalName;

    /**
     * 中文名
     */
    @TableField(value = "cn_name")
    private String cnName;

    /**
     * 缩略图
     */
    @TableField(value = "thumb")
    private String thumb;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
