package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieProducerDTO;
import cc.onelooker.kaleido.entity.business.MovieProducerDO;
import cc.onelooker.kaleido.dto.business.req.MovieProducerPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieProducerCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieProducerUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieProducerPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieProducerViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieProducerCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieProducerExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieProducerConvert {

    MovieProducerConvert INSTANCE = Mappers.getMapper(MovieProducerConvert.class);

    MovieProducerDTO convert(MovieProducerDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieProducerDO convertToDO(MovieProducerDTO dto);

    MovieProducerDTO convertToDTO(MovieProducerPageReq req);

    MovieProducerDTO convertToDTO(MovieProducerCreateReq req);

    MovieProducerDTO convertToDTO(MovieProducerUpdateReq req);

    MovieProducerPageResp convertToPageResp(MovieProducerDTO dto);

    MovieProducerViewResp convertToViewResp(MovieProducerDTO dto);

    MovieProducerCreateResp convertToCreateResp(MovieProducerDTO dto);

    MovieProducerExp convertToExp(MovieProducerDTO dto);

}