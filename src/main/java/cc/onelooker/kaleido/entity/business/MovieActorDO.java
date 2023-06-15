package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;

import java.lang.Long;

import com.zjjcnt.common.core.annotation.Crypto;

import java.lang.String;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 演职员DO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.dto.business.MovieActorDTO
 */
@Crypto
@Data
@TableName("movie_actor")
public class MovieActorDO implements IdEntity<Long> {
    private static final long serialVersionUID = -8431537076357256147L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 姓名
     */
    @Crypto
    @TableField(value = "xm")
    private String xm;

    /**
     * 本名
     */
    @TableField(value = "bm")
    private String bm;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj")
    private String cjsj;

    /**
     * 修改时间
     */
    @TableField(value = "xgsj")
    private String xgsj;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
