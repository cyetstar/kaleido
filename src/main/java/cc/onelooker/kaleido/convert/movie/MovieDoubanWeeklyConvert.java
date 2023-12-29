package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieDoubanWeeklyDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieDoubanWeeklyCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieDoubanWeeklyPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieDoubanWeeklyUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieDoubanWeeklyCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieDoubanWeeklyPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieDoubanWeeklyViewResp;
import cc.onelooker.kaleido.entity.movie.MovieDoubanWeeklyDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 豆瓣电影口碑榜Convert
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */
@Mapper
public interface MovieDoubanWeeklyConvert {

    MovieDoubanWeeklyConvert INSTANCE = Mappers.getMapper(MovieDoubanWeeklyConvert.class);

    MovieDoubanWeeklyDTO convert(MovieDoubanWeeklyDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieDoubanWeeklyDO convertToDO(MovieDoubanWeeklyDTO dto);

    MovieDoubanWeeklyDTO convertToDTO(MovieDoubanWeeklyPageReq req);

    MovieDoubanWeeklyDTO convertToDTO(MovieDoubanWeeklyCreateReq req);

    MovieDoubanWeeklyDTO convertToDTO(MovieDoubanWeeklyUpdateReq req);

    MovieDoubanWeeklyPageResp convertToPageResp(MovieDoubanWeeklyDTO dto);

    MovieDoubanWeeklyViewResp convertToViewResp(MovieDoubanWeeklyDTO dto);

    MovieDoubanWeeklyCreateResp convertToCreateResp(MovieDoubanWeeklyDTO dto);

}