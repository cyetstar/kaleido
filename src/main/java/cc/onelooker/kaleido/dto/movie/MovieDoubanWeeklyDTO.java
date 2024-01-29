package cc.onelooker.kaleido.dto.movie;

import com.google.common.collect.Maps;
import com.zjjcnt.common.core.dto.BaseDTO;
import com.zjjcnt.common.util.JsonUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

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
     * 豆瓣编号
     */
    private String doubanId;

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
     * 最高名次
     */
    private Integer top;

    /**
     * 上榜情况
     */
    private String listingDetail;

    /**
     * 在榜状态
     */
    private String status;

    /**
     * 备注
     */
    private String memo;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void addListingDetail(String listingDate, Integer rank) {
        Map<String, Integer> listDetailMap = getListDetailMap();
        listDetailMap.put(listingDate, rank);
        this.listingDetail = JsonUtils.toJsonString(listDetailMap);
    }

    public Map<String, Integer> getListDetailMap() {
        if (StringUtils.isNotEmpty(listingDetail)) {
            return JsonUtils.parseMapType(listingDetail, Integer.class);
        }
        return Maps.newTreeMap();
    }

    public Integer getBestTop() {
        return getListDetailMap().values().stream().min(Integer::compareTo).orElse(10);
    }

}
