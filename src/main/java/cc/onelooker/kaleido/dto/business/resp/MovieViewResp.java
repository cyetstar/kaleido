package cc.onelooker.kaleido.dto.business.resp;

import cc.onelooker.kaleido.dto.business.*;
import cc.onelooker.kaleido.enums.ActorRole;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.math.BigDecimal;
import java.util.List;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 电影响应对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Data
@ApiModel("电影响应对象")
public class MovieViewResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影名称")
    private String dymc;

    @ApiModelProperty("原片名")
    private String ypm;

    @ApiModelProperty("用户评分")
    private Integer yhpf;

    @ApiModelProperty("电影简介")
    private String dyjj;

    @ApiModelProperty("电影标语")
    private String dyby;

    @ApiModelProperty("影片时长")
    private Integer ypsc;

    @ApiModelProperty("电影等级")
    private String dydj;

    @StringDateFormat
    @ApiModelProperty("上映日期")
    private String syrq;

    @ApiModelProperty("上映年份")
    private String synf;

    @ApiModelProperty("官网地址")
    private String gwdz;

    @Dict("gkbz")
    @ApiModelProperty("是否观看")
    private String gkbz;

    @StringDateTimeFormat
    @ApiModelProperty("观看时间")
    private String gksj;

    @Dict("scbz")
    @ApiModelProperty("是否收藏")
    private String scbz;

    @StringDateTimeFormat
    @ApiModelProperty("收藏时间")
    private String scsj;

    @ApiModelProperty("plex编号")
    private String plexId;

    private String doubanId;

    private String imdbId;

    private List<MovieViewRatingResp> ratingList;

    private List<MovieViewGenreResp> genreList;

    private List<MovieViewLanguageResp> languageList;

    private List<MovieViewCountryResp> countryList;

    private List<MovieViewTagResp> tagList;

    private List<MovieViewSetResp> setList;

    private List<MovieViewAkaResp> akaList;

    private List<MovieViewActorResp> directorList;

    private List<MovieViewActorResp> writerList;

    private List<MovieViewActorResp> actorList;

    @Data
    public static class MovieViewRatingResp {
        private BigDecimal pjf;

        private String pflx;
    }

    @Data
    public static class MovieViewActorResp {

        private Long id;

        private String xm;
    }

    @Data
    public static class MovieViewGenreResp {

        private Long id;

        private String mc;
    }

    @Data
    public static class MovieViewLanguageResp {

        private Long id;

        private String mc;
    }

    @Data
    public static class MovieViewCountryResp {

        private Long id;

        private String mc;
    }

    @Data
    public static class MovieViewSetResp {

        private Long id;

        private String mc;
    }

    @Data
    public static class MovieViewAkaResp {

        private Long id;

        private String dymc;
    }

    @Data
    public static class MovieViewTagResp {

        private Long id;

        private String value;
    }
}
