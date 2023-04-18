package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieUniqueidDTO;
import cc.onelooker.kaleido.dto.business.exp.MovieUniqueidExp;
import cc.onelooker.kaleido.dto.business.req.MovieUniqueidCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieUniqueidPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieUniqueidUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieUniqueidCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MovieUniqueidPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieUniqueidViewResp;
import cc.onelooker.kaleido.entity.business.MovieUniqueidDO;
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