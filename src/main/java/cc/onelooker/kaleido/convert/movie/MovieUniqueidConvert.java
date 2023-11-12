package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieUniqueidDTO;
import cc.onelooker.kaleido.dto.movie.exp.MovieUniqueidExp;
import cc.onelooker.kaleido.dto.movie.req.MovieUniqueidCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieUniqueidPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieUniqueidUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieUniqueidCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieUniqueidPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieUniqueidViewResp;
import cc.onelooker.kaleido.entity.movie.MovieUniqueidDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影唯一标识Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieUniqueidConvert {

    MovieUniqueidConvert INSTANCE = Mappers.getMapper(MovieUniqueidConvert.class);

    MovieUniqueidDTO convert(MovieUniqueidDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieUniqueidDO convertToDO(MovieUniqueidDTO dto);

    MovieUniqueidDTO convertToDTO(MovieUniqueidPageReq req);

    MovieUniqueidDTO convertToDTO(MovieUniqueidCreateReq req);

    MovieUniqueidDTO convertToDTO(MovieUniqueidUpdateReq req);

    MovieUniqueidPageResp convertToPageResp(MovieUniqueidDTO dto);

    MovieUniqueidViewResp convertToViewResp(MovieUniqueidDTO dto);

    MovieUniqueidCreateResp convertToCreateResp(MovieUniqueidDTO dto);

    MovieUniqueidExp convertToExp(MovieUniqueidDTO dto);

}