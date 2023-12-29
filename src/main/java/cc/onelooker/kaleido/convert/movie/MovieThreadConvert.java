package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieThreadDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadViewResp;
import cc.onelooker.kaleido.entity.movie.MovieThreadDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 电影发布记录Convert
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Mapper
public interface MovieThreadConvert {

    MovieThreadConvert INSTANCE = Mappers.getMapper(MovieThreadConvert.class);

    MovieThreadDTO convert(MovieThreadDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieThreadDO convertToDO(MovieThreadDTO dto);

    MovieThreadDTO convertToDTO(MovieThreadPageReq req);

    MovieThreadDTO convertToDTO(MovieThreadCreateReq req);

    MovieThreadDTO convertToDTO(MovieThreadUpdateReq req);

    MovieThreadPageResp convertToPageResp(MovieThreadDTO dto);

    MovieThreadViewResp convertToViewResp(MovieThreadDTO dto);

    MovieThreadCreateResp convertToCreateResp(MovieThreadDTO dto);

}