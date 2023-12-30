package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateFormat;

/**
 * 豆瓣电影口碑榜DTO
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 * @see cc.onelooker.kaleido.entity.movie.MovieDoubanWeeklyDO
 */
@Data
public class MovieDoubanWeeklyDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 7307402215120090989L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影名
     */
    private String title;

    /**
     * 原片名
     */
    private String originalTitle;

    /**
     * 首映年份
     */
    private String year;

    /**
     * 海报
     */
    private String thumb;

    /**
     * 上榜日期
     */
    private String listingDate;

    /**
     * 下榜日期
     */
    private String delistingDate;

    /**
     * 最高名次
     */
    private Integer top;

    /**
     * 备注
     */
    private String memo;

    // ------ 非数据库表字段 -------
    /**
    * 大于等于上榜日期
    */
    private String listingDateStart;

    /**
    * 小于等于上榜日期
    */
    private String listingDateEnd;

    /**
    * 大于等于下榜日期
    */
    private String delistingDateStart;

    /**
    * 小于等于下榜日期
    */
    private String delistingDateEnd;



    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
