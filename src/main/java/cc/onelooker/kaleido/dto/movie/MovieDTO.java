package cc.onelooker.kaleido.dto.movie;

import cc.onelooker.kaleido.dto.ISystem;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
 * 电影DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.movie.MovieDO
 */
@Data
public class MovieDTO implements ISystem<Long> {
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
     * 原电影名称
     */
    private String ydymc;

    /**
     * 用户评分
     */
    private Integer yhpf;

    /**
     * 电影类型
     */
    private String dylx;

    /**
     * 国家地区
     */
    private String gjdq;

    /**
     * 电影语言
     */
    private String dyyy;

    /**
     * 电影简介
     */
    private String dyjj;

    /**
     * 电影标语
     */
    private String dyby;

    /**
     * 电影时长
     */
    private Integer dysc;

    /**
     * 电影等级
     */
    private String dydj;

    /**
     * 上映日期
     */
    private String syrq;

    /**
     * 上映年份
     */
    private String synf;

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
     * 评分
     */
    private String pf;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
    private String xgsj;

    private List<MovieSetDTO> movieSetDTOList;

    private List<MovieRatingDTO> movieRatingDTOList;

    private List<MovieUniqueidDTO> movieUniqueidDTOList;

    private List<MovieAkaDTO> movieAkaDTOList;

    private List<MovieTagDTO> movieTagDTOList;

    private List<MovieActorDTO> actorList;

    private List<MovieActorDTO> writerList;

    private List<MovieActorDTO> directorList;

    private MovieFileDTO movieFileDTO;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
