package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.math.BigDecimal;
import java.util.List;

import com.zjjcnt.common.core.annotation.StringDateFormat;

/**
 * 电影请求对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Data
@ApiModel("电影请求对象")
public class MovieUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    private String doubanId;

    private String imdbId;

    @ApiModelProperty("电影名称")
    private String dymc;

    @ApiModelProperty("原电影名称")
    private String ydymc;

    @ApiModelProperty("电影简介")
    private String dyjj;

    @ApiModelProperty("电影标语")
    private String dyby;

    @ApiModelProperty("影片时长")
    private Integer dysc;

    @ApiModelProperty("电影等级")
    private String dydj;

    @StringDateFormat
    @ApiModelProperty("上映日期")
    private String syrq;

    @ApiModelProperty("官网地址")
    private String gwdz;

    private MovieUpdateRatingReq doubanRating;

    private MovieUpdateRatingReq imdbRating;

    private List<String> dylxList;

    private List<String> dyyyList;

    private List<String> gjdqList;

    private List<String> tagList;

    private List<String> akaList;

    private List<MovieUpdateActorReq> directorList;

    private List<MovieUpdateActorReq> writerList;

    private List<MovieUpdateActorReq> actorList;

    @Data
    public static class MovieUpdateRatingReq {
        private String pjf;

        private Integer tps;

    }

    @Data
    public static class MovieUpdateActorReq {

        private Long id;

        private String xm;
    }

}
