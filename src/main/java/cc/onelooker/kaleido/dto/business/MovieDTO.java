package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 电影DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.business.MovieDO
 */
@Data
public class MovieDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -4454324070494604321L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影名称
     */
    private String dymc;

    /**
     * 原片名
     */
    private String ypm;

    /**
     * 用户评分
     */
    private Integer yhpf;

    /**
     * 电影简介
     */
    private String dyjj;

    /**
     * 电影标语
     */
    private String dyby;

    /**
     * 影片时长
     */
    private Integer ypsc;

    /**
     * 电影等级
     */
    private String dydj;

    /**
     * 上映日期
     */
    private String syrq;

    /**
     * 官网地址
     */
    private String gwdz;

    /**
     * 是否观看
     */
    private String gkbz;

    /**
     * 观看时间
     */
    private String gksj;

    /**
     * 是否收藏
     */
    private String scbz;

    /**
     * 收藏时间
     */
    private String scsj;

    /**
     * plex编号
     */
    private String plexId;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
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
