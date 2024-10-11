package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MovieDoubanWeeklyDTO;
import cc.onelooker.kaleido.dto.req.MovieDoubanWeeklyCreateReq;
import cc.onelooker.kaleido.dto.req.MovieDoubanWeeklyPageReq;
import cc.onelooker.kaleido.dto.req.MovieDoubanWeeklyUpdateReq;
import cc.onelooker.kaleido.dto.resp.MovieDoubanWeeklyCreateResp;
import cc.onelooker.kaleido.dto.resp.MovieDoubanWeeklyPageResp;
import cc.onelooker.kaleido.dto.resp.MovieDoubanWeeklyViewResp;
import cc.onelooker.kaleido.entity.MovieDoubanWeeklyDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
* 豆瓣电影口碑榜Convert
*
* @author cyetstar
* @date 2023-12-29 16:15:43
*/
@Mapper
public interface MovieDoubanWeeklyConvert {

    MovieDoubanWeeklyConvert INSTANCE = Mappers.getMapper(MovieDoubanWeeklyConvert.class);

    MovieDoubanWeeklyDTO convert(MovieDoubanWeeklyDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieDoubanWeeklyDO convertToDO(MovieDoubanWeeklyDTO dto);

    MovieDoubanWeeklyDTO convertToDTO(MovieDoubanWeeklyPageReq req);

    MovieDoubanWeeklyDTO convertToDTO(MovieDoubanWeeklyCreateReq req);

    MovieDoubanWeeklyDTO convertToDTO(MovieDoubanWeeklyUpdateReq req);

    MovieDoubanWeeklyPageResp convertToPageResp(MovieDoubanWeeklyDTO dto);

    MovieDoubanWeeklyViewResp convertToViewResp(MovieDoubanWeeklyDTO dto);

    MovieDoubanWeeklyCreateResp convertToCreateResp(MovieDoubanWeeklyDTO dto);


}