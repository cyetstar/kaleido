package cc.onelooker.kaleido.dto.business.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * 请求对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
@ApiModel("请求对象")
public class MovieCreateReq{

    @ApiModelProperty("")
    private String actor;

    @ApiModelProperty("")
    private String aired;

    @ApiModelProperty("")
    private String category;

    @ApiModelProperty("")
    private String certification;

    @ApiModelProperty("")
    private String code;

    @ApiModelProperty("")
    private String country;

    @ApiModelProperty("")
    private Date createdAt;

    @ApiModelProperty("")
    private String dateadded;

    @ApiModelProperty("")
    private String doubanId;

    @ApiModelProperty("")
    private Double doubanRating;

    @ApiModelProperty("")
    private String doubanResult;

    @ApiModelProperty("")
    private Integer doubanTop250;

    @ApiModelProperty("")
    private Date doubanUpdatedAt;

    @ApiModelProperty("")
    private Integer doubanVotes;

    @ApiModelProperty("")
    private String edition;

    @ApiModelProperty("")
    private String epbookmark;

    @ApiModelProperty("")
    private String filePath;

    @ApiModelProperty("")
    private Boolean hasFanart;

    @ApiModelProperty("")
    private Boolean hasFile;

    @ApiModelProperty("")
    private Boolean hasNfo;

    @ApiModelProperty("")
    private Boolean hasPoster;

    @ApiModelProperty("")
    private Boolean hasSubtitle;

    @ApiModelProperty("")
    private String imdb;

    @ApiModelProperty("")
    private Double imdbRating;

    @ApiModelProperty("")
    private Integer imdbVotes;

    @ApiModelProperty("")
    private String kodiid;

    @ApiModelProperty("")
    private String lastplayed;

    @ApiModelProperty("")
    private String mpaa;

    @ApiModelProperty("")
    private String nfoPath;

    @ApiModelProperty("")
    private String number;

    @ApiModelProperty("")
    private Double numberRating;

    @ApiModelProperty("")
    private Integer numberVotes;

    @ApiModelProperty("")
    private String originalFilename;

    @ApiModelProperty("")
    private String originaltitle;

    @ApiModelProperty("")
    private String outline;

    @ApiModelProperty("")
    private Integer playcount;

    @ApiModelProperty("")
    private String plot;

    @ApiModelProperty("")
    private String premiered;

    @ApiModelProperty("")
    private Integer runtime;

    @ApiModelProperty("")
    private String showlink;

    @ApiModelProperty("")
    private String sorttitle;

    @ApiModelProperty("")
    private String source;

    @ApiModelProperty("")
    private String status;

    @ApiModelProperty("")
    private String tagline;

    @ApiModelProperty("")
    private String title;

    @ApiModelProperty("")
    private String tmdbCollectionId;

    @ApiModelProperty("")
    private String tmdbid;

    @ApiModelProperty("")
    private Integer top250;

    @ApiModelProperty("")
    private String trailer;

    @ApiModelProperty("")
    private Date updatedAt;

    @ApiModelProperty("")
    private String userNote;

    @ApiModelProperty("")
    private Double userrating;

    @ApiModelProperty("")
    private Boolean validatedPath;

    @ApiModelProperty("")
    private Boolean watched;

    @ApiModelProperty("")
    private String website;

    @ApiModelProperty("")
    private String year;
}
