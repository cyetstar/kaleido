package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.ActorDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 演职员DO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see ActorDTO
 */
@Data
@TableName("actor")
public class ActorDO implements IdEntity<String> {
    private static final long serialVersionUID = 5953030545604783665L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

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

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
