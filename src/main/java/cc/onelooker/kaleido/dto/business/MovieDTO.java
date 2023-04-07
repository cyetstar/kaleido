package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.MovieDO
 */
@Data
public class MovieDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -4433222994530132500L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String actor;

    /**
     * 
     */
    private String aired;

    /**
     * 
     */
    private String category;

    /**
     * 
     */
    private String certification;

    /**
     * 
     */
    private String code;

    /**
     * 
     */
    private String country;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private String dateadded;

    /**
     * 
     */
    private String doubanId;

    /**
     * 
     */
    private Double doubanRating;

    /**
     * 
     */
    private String doubanResult;

    /**
     * 
     */
    private Integer doubanTop250;

    /**
     * 
     */
    private Date doubanUpdatedAt;

    /**
     * 
     */
    private Integer doubanVotes;

    /**
     * 
     */
    private String edition;

    /**
     * 
     */
    private String epbookmark;

    /**
     * 
     */
    private String filePath;

    /**
     * 
     */
    private Boolean hasFanart;

    /**
     * 
     */
    private Boolean hasFile;

    /**
     * 
     */
    private Boolean hasNfo;

    /**
     * 
     */
    private Boolean hasPoster;

    /**
     * 
     */
    private Boolean hasSubtitle;

    /**
     * 
     */
    private String imdb;

    /**
     * 
     */
    private Double imdbRating;

    /**
     * 
     */
    private Integer imdbVotes;

    /**
     * 
     */
    private String kodiid;

    /**
     * 
     */
    private String lastplayed;

    /**
     * 
     */
    private String mpaa;

    /**
     * 
     */
    private String nfoPath;

    /**
     * 
     */
    private String number;

    /**
     * 
     */
    private Double numberRating;

    /**
     * 
     */
    private Integer numberVotes;

    /**
     * 
     */
    private String originalFilename;

    /**
     * 
     */
    private String originaltitle;

    /**
     * 
     */
    private String outline;

    /**
     * 
     */
    private Integer playcount;

    /**
     * 
     */
    private String plot;

    /**
     * 
     */
    private String premiered;

    /**
     * 
     */
    private Integer runtime;

    /**
     * 
     */
    private String showlink;

    /**
     * 
     */
    private String sorttitle;

    /**
     * 
     */
    private String source;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String tagline;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String tmdbCollectionId;

    /**
     * 
     */
    private String tmdbid;

    /**
     * 
     */
    private Integer top250;

    /**
     * 
     */
    private String trailer;

    /**
     * 
     */
    private Date updatedAt;

    /**
     * 
     */
    private String userNote;

    /**
     * 
     */
    private Double userrating;

    /**
     * 
     */
    private Boolean validatedPath;

    /**
     * 
     */
    private Boolean watched;

    /**
     * 
     */
    private String website;

    /**
     * 
     */
    private String year;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
