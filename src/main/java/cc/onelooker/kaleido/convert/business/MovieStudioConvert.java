package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieStudioDTO;
import cc.onelooker.kaleido.entity.business.MovieStudioDO;
import cc.onelooker.kaleido.dto.business.req.MovieStudioPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieStudioCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieStudioUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieStudioPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieStudioViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieStudioCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieStudioExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieStudioConvert {

    MovieStudioConvert INSTANCE = Mappers.getMapper(MovieStudioConvert.class);

    MovieStudioDTO convert(MovieStudioDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieStudioDO convertToDO(MovieStudioDTO dto);

    MovieStudioDTO convertToDTO(MovieStudioPageReq req);

    MovieStudioDTO convertToDTO(MovieStudioCreateReq req);

    MovieStudioDTO convertToDTO(MovieStudioUpdateReq req);

    MovieStudioPageResp convertToPageResp(MovieStudioDTO dto);

    MovieStudioViewResp convertToViewResp(MovieStudioDTO dto);

    MovieStudioCreateResp convertToCreateResp(MovieStudioDTO dto);

    MovieStudioExp convertToExp(MovieStudioDTO dto);

}