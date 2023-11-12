package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieSetDTO;
import cc.onelooker.kaleido.dto.movie.exp.MovieSetExp;
import cc.onelooker.kaleido.dto.movie.req.MovieSetCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieSetPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieSetUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieSetCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieSetPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieSetViewResp;
import cc.onelooker.kaleido.entity.movie.MovieSetDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影集合Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieSetConvert {

    MovieSetConvert INSTANCE = Mappers.getMapper(MovieSetConvert.class);

    MovieSetDTO convert(MovieSetDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieSetDO convertToDO(MovieSetDTO dto);

    MovieSetDTO convertToDTO(MovieSetPageReq req);

    MovieSetDTO convertToDTO(MovieSetCreateReq req);

    MovieSetDTO convertToDTO(MovieSetUpdateReq req);

    MovieSetPageResp convertToPageResp(MovieSetDTO dto);

    MovieSetViewResp convertToViewResp(MovieSetDTO dto);

    MovieSetCreateResp convertToCreateResp(MovieSetDTO dto);

    MovieSetExp convertToExp(MovieSetDTO dto);

}