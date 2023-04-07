package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieDirectorDTO;
import cc.onelooker.kaleido.entity.business.MovieDirectorDO;
import cc.onelooker.kaleido.dto.business.req.MovieDirectorPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieDirectorCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieDirectorUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieDirectorPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieDirectorViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieDirectorCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieDirectorExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieDirectorConvert {

    MovieDirectorConvert INSTANCE = Mappers.getMapper(MovieDirectorConvert.class);

    MovieDirectorDTO convert(MovieDirectorDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieDirectorDO convertToDO(MovieDirectorDTO dto);

    MovieDirectorDTO convertToDTO(MovieDirectorPageReq req);

    MovieDirectorDTO convertToDTO(MovieDirectorCreateReq req);

    MovieDirectorDTO convertToDTO(MovieDirectorUpdateReq req);

    MovieDirectorPageResp convertToPageResp(MovieDirectorDTO dto);

    MovieDirectorViewResp convertToViewResp(MovieDirectorDTO dto);

    MovieDirectorCreateResp convertToCreateResp(MovieDirectorDTO dto);

    MovieDirectorExp convertToExp(MovieDirectorDTO dto);

}