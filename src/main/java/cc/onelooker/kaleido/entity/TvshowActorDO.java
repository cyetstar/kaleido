package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.TvshowActorDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 剧集演职员DO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowActorDTO
 */
@Data
@TableName("tvshow_actor")
public class TvshowActorDO implements IdEntity<String> {
    private static final long serialVersionUID = -7537395807809857706L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

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
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
