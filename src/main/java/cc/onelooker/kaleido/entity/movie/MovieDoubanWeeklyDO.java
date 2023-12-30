package cc.onelooker.kaleido.entity.movie;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.annotation.Crypto;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateFormat;

/**
 * 豆瓣电影口碑榜DO
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 * @see cc.onelooker.kaleido.dto.movie.MovieDoubanWeeklyDTO
 */
@Data
@TableName("movie_douban_weekly")
public class MovieDoubanWeeklyDO implements IdEntity<Long> {
    private static final long serialVersionUID = -6247935346600730459L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 电影名
     */
    @TableField(value = "title")
    private String title;

    /**
     * 原片名
     */
    @TableField(value = "original_title")
    private String originalTitle;

    /**
     * 首映年份
     */
    @TableField(value = "year")
    private String year;

    /**
     * 海报
     */
    @TableField(value = "thumb")
    private String thumb;

    /**
     * 上榜日期
     */
    @TableField(value = "listing_date")
    private String listingDate;

    /**
     * 下榜日期
     */
    @TableField(value = "delisting_date")
    private String delistingDate;

    /**
     * 最高名次
     */
    @TableField(value = "top")
    private Integer top;

    /**
     * 备注
     */
    @TableField(value = "memo")
    private String memo;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
