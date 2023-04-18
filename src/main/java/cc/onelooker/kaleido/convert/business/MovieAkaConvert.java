package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieAkaDTO;
import cc.onelooker.kaleido.dto.business.exp.MovieAkaExp;
import cc.onelooker.kaleido.dto.business.req.MovieAkaCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieAkaPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieAkaUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieAkaCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MovieAkaPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieAkaViewResp;
import cc.onelooker.kaleido.entity.business.MovieAkaDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 别名Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieAkaConvert {

    MovieAkaConvert INSTANCE = Mappers.getMapper(MovieAkaConvert.class);

    MovieAkaDTO convert(MovieAkaDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieAkaDO convertToDO(MovieAkaDTO dto);

    MovieAkaDTO convertToDTO(MovieAkaPageReq req);

    MovieAkaDTO convertToDTO(MovieAkaCreateReq req);

    MovieAkaDTO convertToDTO(MovieAkaUpdateReq req);

    MovieAkaPageResp convertToPageResp(MovieAkaDTO dto);

    MovieAkaViewResp convertToViewResp(MovieAkaDTO dto);

    MovieAkaCreateResp convertToCreateResp(MovieAkaDTO dto);

    MovieAkaExp convertToExp(MovieAkaDTO dto);

}