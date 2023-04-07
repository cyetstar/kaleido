package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * DO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.dto.business.MovieDTO
 */
@Data
@TableName("movie")
public class MovieDO implements IdEntity<Long> {
    private static final long serialVersionUID = 924821197024857122L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "actor")
    private String actor;

    /**
     * 
     */
    @TableField(value = "aired")
    private String aired;

    /**
     * 
     */
    @TableField(value = "category")
    private String category;

    /**
     * 
     */
    @TableField(value = "certification")
    private String certification;

    /**
     * 
     */
    @TableField(value = "code")
    private String code;

    /**
     * 
     */
    @TableField(value = "country")
    private String country;

    /**
     * 
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 
     */
    @TableField(value = "dateadded")
    private String dateadded;

    /**
     * 
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * 
     */
    @TableField(value = "douban_rating")
    private Double doubanRating;

    /**
     * 
     */
    @TableField(value = "douban_result")
    private String doubanResult;

    /**
     * 
     */
    @TableField(value = "douban_top250")
    private Integer doubanTop250;

    /**
     * 
     */
    @TableField(value = "douban_updated_at")
    private Date doubanUpdatedAt;

    /**
     * 
     */
    @TableField(value = "douban_votes")
    private Integer doubanVotes;

    /**
     * 
     */
    @TableField(value = "edition")
    private String edition;

    /**
     * 
     */
    @TableField(value = "epbookmark")
    private String epbookmark;

    /**
     * 
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 
     */
    @TableField(value = "has_fanart")
    private Boolean hasFanart;

    /**
     * 
     */
    @TableField(value = "has_file")
    private Boolean hasFile;

    /**
     * 
     */
    @TableField(value = "has_nfo")
    private Boolean hasNfo;

    /**
     * 
     */
    @TableField(value = "has_poster")
    private Boolean hasPoster;

    /**
     * 
     */
    @TableField(value = "has_subtitle")
    private Boolean hasSubtitle;

    /**
     * 
     */
    @TableField(value = "imdb")
    private String imdb;

    /**
     * 
     */
    @TableField(value = "imdb_rating")
    private Double imdbRating;

    /**
     * 
     */
    @TableField(value = "imdb_votes")
    private Integer imdbVotes;

    /**
     * 
     */
    @TableField(value = "kodiid")
    private String kodiid;

    /**
     * 
     */
    @TableField(value = "lastplayed")
    private String lastplayed;

    /**
     * 
     */
    @TableField(value = "mpaa")
    private String mpaa;

    /**
     * 
     */
    @TableField(value = "nfo_path")
    private String nfoPath;

    /**
     * 
     */
    @TableField(value = "number")
    private String number;

    /**
     * 
     */
    @TableField(value = "number_rating")
    private Double numberRating;

    /**
     * 
     */
    @TableField(value = "number_votes")
    private Integer numberVotes;

    /**
     * 
     */
    @TableField(value = "original_filename")
    private String originalFilename;

    /**
     * 
     */
    @TableField(value = "originaltitle")
    private String originaltitle;

    /**
     * 
     */
    @TableField(value = "outline")
    private String outline;

    /**
     * 
     */
    @TableField(value = "playcount")
    private Integer playcount;

    /**
     * 
     */
    @TableField(value = "plot")
    private String plot;

    /**
     * 
     */
    @TableField(value = "premiered")
    private String premiered;

    /**
     * 
     */
    @TableField(value = "runtime")
    private Integer runtime;

    /**
     * 
     */
    @TableField(value = "showlink")
    private String showlink;

    /**
     * 
     */
    @TableField(value = "sorttitle")
    private String sorttitle;

    /**
     * 
     */
    @TableField(value = "source")
    private String source;

    /**
     * 
     */
    @TableField(value = "status")
    private String status;

    /**
     * 
     */
    @TableField(value = "tagline")
    private String tagline;

    /**
     * 
     */
    @TableField(value = "title")
    private String title;

    /**
     * 
     */
    @TableField(value = "tmdb_collection_id")
    private String tmdbCollectionId;

    /**
     * 
     */
    @TableField(value = "tmdbid")
    private String tmdbid;

    /**
     * 
     */
    @TableField(value = "top250")
    private Integer top250;

    /**
     * 
     */
    @TableField(value = "trailer")
    private String trailer;

    /**
     * 
     */
    @TableField(value = "updated_at")
    private Date updatedAt;

    /**
     * 
     */
    @TableField(value = "user_note")
    private String userNote;

    /**
     * 
     */
    @TableField(value = "userrating")
    private Double userrating;

    /**
     * 
     */
    @TableField(value = "validated_path")
    private Boolean validatedPath;

    /**
     * 
     */
    @TableField(value = "watched")
    private Boolean watched;

    /**
     * 
     */
    @TableField(value = "website")
    private String website;

    /**
     * 
     */
    @TableField(value = "year")
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
