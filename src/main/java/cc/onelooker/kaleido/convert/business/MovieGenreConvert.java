package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieGenreDTO;
import cc.onelooker.kaleido.dto.business.exp.MovieGenreExp;
import cc.onelooker.kaleido.dto.business.req.MovieGenreCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieGenrePageReq;
import cc.onelooker.kaleido.dto.business.req.MovieGenreUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieGenreCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MovieGenrePageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieGenreViewResp;
import cc.onelooker.kaleido.entity.business.MovieGenreDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 影片类型Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
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