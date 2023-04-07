package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieGenreDTO;
import cc.onelooker.kaleido.entity.business.MovieGenreDO;
import cc.onelooker.kaleido.dto.business.req.MovieGenrePageReq;
import cc.onelooker.kaleido.dto.business.req.MovieGenreCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieGenreUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieGenrePageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieGenreViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieGenreCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieGenreExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieGenreConvert {

    MovieGenreConvert INSTANCE = Mappers.getMapper(MovieGenreConvert.class);

    MovieGenreDTO convert(MovieGenreDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieGenreDO convertToDO(MovieGenreDTO dto);

    MovieGenreDTO convertToDTO(MovieGenrePageReq req);

    MovieGenreDTO convertToDTO(MovieGenreCreateReq req);

    MovieGenreDTO convertToDTO(MovieGenreUpdateReq req);

    MovieGenrePageResp convertToPageResp(MovieGenreDTO dto);

    MovieGenreViewResp convertToViewResp(MovieGenreDTO dto);

    MovieGenreCreateResp convertToCreateResp(MovieGenreDTO dto);

    MovieGenreExp convertToExp(MovieGenreDTO dto);

}