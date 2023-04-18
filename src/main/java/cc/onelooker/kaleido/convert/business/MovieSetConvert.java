package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieSetDTO;
import cc.onelooker.kaleido.dto.business.exp.MovieSetExp;
import cc.onelooker.kaleido.dto.business.req.MovieSetCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieSetPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieSetUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieSetCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MovieSetPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieSetViewResp;
import cc.onelooker.kaleido.entity.business.MovieSetDO;
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