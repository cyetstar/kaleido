package cc.onelooker.kaleido.dto.business.exp;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * 导出对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
public class MovieExp{

    @ExcelProperty("")
    private Long id;

    @ExcelProperty("")
    private String actor;

    @ExcelProperty("")
    private String aired;

    @ExcelProperty("")
    private String category;

    @ExcelProperty("")
    private String certification;

    @ExcelProperty("")
    private String code;

    @ExcelProperty("")
    private String country;

    @ExcelProperty("")
    private Date createdAt;

    @ExcelProperty("")
    private String dateadded;

    @ExcelProperty("")
    private String doubanId;

    @ExcelProperty("")
    private Double doubanRating;

    @ExcelProperty("")
    private String doubanResult;

    @ExcelProperty("")
    private Integer doubanTop250;

    @ExcelProperty("")
    private Date doubanUpdatedAt;

    @ExcelProperty("")
    private Integer doubanVotes;

    @ExcelProperty("")
    private String edition;

    @ExcelProperty("")
    private String epbookmark;

    @ExcelProperty("")
    private String filePath;

    @ExcelProperty("")
    private Boolean hasFanart;

    @ExcelProperty("")
    private Boolean hasFile;

    @ExcelProperty("")
    private Boolean hasNfo;

    @ExcelProperty("")
    private Boolean hasPoster;

    @ExcelProperty("")
    private Boolean hasSubtitle;

    @ExcelProperty("")
    private String imdb;

    @ExcelProperty("")
    private Double imdbRating;

    @ExcelProperty("")
    private Integer imdbVotes;

    @ExcelProperty("")
    private String kodiid;

    @ExcelProperty("")
    private String lastplayed;

    @ExcelProperty("")
    private String mpaa;

    @ExcelProperty("")
    private String nfoPath;

    @ExcelProperty("")
    private String number;

    @ExcelProperty("")
    private Double numberRating;

    @ExcelProperty("")
    private Integer numberVotes;

    @ExcelProperty("")
    private String originalFilename;

    @ExcelProperty("")
    private String originaltitle;

    @ExcelProperty("")
    private String outline;

    @ExcelProperty("")
    private Integer playcount;

    @ExcelProperty("")
    private String plot;

    @ExcelProperty("")
    private String premiered;

    @ExcelProperty("")
    private Integer runtime;

    @ExcelProperty("")
    private String showlink;

    @ExcelProperty("")
    private String sorttitle;

    @ExcelProperty("")
    private String source;

    @ExcelProperty("")
    private String status;

    @ExcelProperty("")
    private String tagline;

    @ExcelProperty("")
    private String title;

    @ExcelProperty("")
    private String tmdbCollectionId;

    @ExcelProperty("")
    private String tmdbid;

    @ExcelProperty("")
    private Integer top250;

    @ExcelProperty("")
    private String trailer;

    @ExcelProperty("")
    private Date updatedAt;

    @ExcelProperty("")
    private String userNote;

    @ExcelProperty("")
    private Double userrating;

    @ExcelProperty("")
    private Boolean validatedPath;

    @ExcelProperty("")
    private Boolean watched;

    @ExcelProperty("")
    private String website;

    @ExcelProperty("")
    private String year;
}
