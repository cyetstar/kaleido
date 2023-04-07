package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieWriterDTO;
import cc.onelooker.kaleido.entity.business.MovieWriterDO;
import cc.onelooker.kaleido.dto.business.req.MovieWriterPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieWriterCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieWriterUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieWriterPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieWriterViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieWriterCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieWriterExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieWriterConvert {

    MovieWriterConvert INSTANCE = Mappers.getMapper(MovieWriterConvert.class);

    MovieWriterDTO convert(MovieWriterDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieWriterDO convertToDO(MovieWriterDTO dto);

    MovieWriterDTO convertToDTO(MovieWriterPageReq req);

    MovieWriterDTO convertToDTO(MovieWriterCreateReq req);

    MovieWriterDTO convertToDTO(MovieWriterUpdateReq req);

    MovieWriterPageResp convertToPageResp(MovieWriterDTO dto);

    MovieWriterViewResp convertToViewResp(MovieWriterDTO dto);

    MovieWriterCreateResp convertToCreateResp(MovieWriterDTO dto);

    MovieWriterExp convertToExp(MovieWriterDTO dto);

}