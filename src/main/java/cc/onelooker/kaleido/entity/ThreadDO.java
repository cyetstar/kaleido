package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.ThreadDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 电影发布记录DO
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 * @see ThreadDTO
 */
@Data
@TableName("thread")
public class ThreadDO implements IdEntity<String> {
    private static final long serialVersionUID = 8141120704617576531L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 链接
     */
    @TableField(value = "url")
    private String url;

    /**
     * 站点
     */
    @TableField(value = "website")
    private String website;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * IMDb编号
     */
    @TableField(value = "imdb_id")
    private String imdbId;

    /**
     * 番组计划编号
     */
    @TableField(value = "bgm_id")
    private String bgmId;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
