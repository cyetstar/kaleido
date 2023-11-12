package cc.onelooker.kaleido.dto.movie.resp;

import com.google.common.collect.Lists;
import com.zjjcnt.common.util.constant.Constants;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

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

    @ApiModelProperty("原电影名称")
    private String ydymc;

    @ApiModelProperty("用户评分")
    private Integer yhpf;

    @ApiModelProperty("电影简介")
    private String dyjj;

    @ApiModelProperty("电影标语")
    private String dyby;

    @ApiModelProperty("电影时长")
    private Integer dysc;

    @ApiModelProperty("电影等级")
    private String dydj;

    @StringDateFormat
    @ApiModelProperty("上映日期")
    private String syrq;

    @ApiModelProperty("上映年份")
    private String synf;

    @ApiModelProperty("官网地址")
    private String gwdz;

    @Dict("sfbz")
    @ApiModelProperty("是否观看")
    private String gkbz;

    @StringDateTimeFormat
    @ApiModelProperty("观看时间")
    private String gksj;

    @Dict("sfbz")
    @ApiModelProperty("是否收藏")
    private String scbz;

    @StringDateTimeFormat
    @ApiModelProperty("收藏时间")
    private String scsj;

    @ApiModelProperty("plex编号")
    private String plexId;

    private String doubanId;

    private String imdbId;

    private MovieViewRatingResp doubanRating;

    private MovieViewRatingResp imdbRating;

    private List<String> dylxList;

    private List<String> gjdqList;

    private List<String> dyyyList;

    private List<String> tagList;

    private List<String> akaList;

    private List<MovieViewSetResp> setList;

    private List<MovieViewActorResp> directorList;

    private List<MovieViewActorResp> writerList;

    private List<MovieViewActorResp> actorList;

    private MovieViewFileResp file;

    public List<String> getDyjjList() {
        return StringUtils.isNotEmpty(dyjj) ? Lists.newArrayList(StringUtils.split(dyjj, Constants.LINE_END_TAG)) : Lists.newArrayList();
    }

    @Data
    public static class MovieViewRatingResp {
        private BigDecimal pjf;

        private Integer tps;

        private String bslx;
    }

    @Data
    public static class MovieViewActorResp {

        private Long id;

        private String xm;
    }

    @Data
    public static class MovieViewSetResp {

        private Long id;

        private String mc;
    }

    @Data
    public static class MovieViewFileResp {

        private Long id;
        /**
         * 文件名
         */
        private String wjm;

        /**
         * 文件路径
         */
        private String wjlj;

        /**
         * 文件大小
         */
        private Integer wjdx;

        public MovieViewVideoResp video;

        public List<MovieViewAudioResp> audioList;

        public List<MovieViewSubtitleResp> subtitleList;
    }

    @Data
    public static class MovieViewVideoResp {

        private Long id;

        private String codec;

        private String aspect;

        private Integer width;

        private Integer height;

        private Integer durationinseconds;

        private String stereomode;
    }

    @Data
    public static class MovieViewAudioResp {

        private Long id;

        private String codec;

        private String language;

        private String channels;
    }

    @Data
    public static class MovieViewSubtitleResp {

        private Long id;

        private String language;
    }
}
