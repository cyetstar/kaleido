package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.AuthorDTO;
import cc.onelooker.kaleido.dto.req.AuthorCreateReq;
import cc.onelooker.kaleido.dto.req.AuthorPageReq;
import cc.onelooker.kaleido.dto.req.AuthorUpdateReq;
import cc.onelooker.kaleido.dto.resp.AuthorCreateResp;
import cc.onelooker.kaleido.dto.resp.AuthorPageResp;
import cc.onelooker.kaleido.dto.resp.AuthorViewResp;
import cc.onelooker.kaleido.entity.AuthorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 作者Convert
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorConvert {

    AuthorConvert INSTANCE = Mappers.getMapper(AuthorConvert.class);

    AuthorDTO convert(AuthorDO entity);

    @InheritInverseConfiguration(name = "convert")
    AuthorDO convertToDO(AuthorDTO dto);

    AuthorDTO convertToDTO(AuthorPageReq req);

    AuthorDTO convertToDTO(AuthorCreateReq req);

    AuthorDTO convertToDTO(AuthorUpdateReq req);

    AuthorPageResp convertToPageResp(AuthorDTO dto);

    AuthorViewResp convertToViewResp(AuthorDTO dto);

    AuthorCreateResp convertToCreateResp(AuthorDTO dto);

}