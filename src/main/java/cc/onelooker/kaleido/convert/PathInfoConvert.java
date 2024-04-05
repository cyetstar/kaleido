package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.PathInfoDTO;
import cc.onelooker.kaleido.dto.req.PathInfoCreateReq;
import cc.onelooker.kaleido.dto.req.PathInfoPageReq;
import cc.onelooker.kaleido.dto.req.PathInfoUpdateReq;
import cc.onelooker.kaleido.dto.resp.PathInfoCreateResp;
import cc.onelooker.kaleido.dto.resp.PathInfoPageResp;
import cc.onelooker.kaleido.dto.resp.PathInfoViewResp;
import cc.onelooker.kaleido.entity.PathInfoDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 目录信息Convert
 *
 * @author cyetstar
 * @date 2024-03-23 23:08:41
 */
@Mapper
public interface PathInfoConvert {

    PathInfoConvert INSTANCE = Mappers.getMapper(PathInfoConvert.class);

    PathInfoDTO convert(PathInfoDO entity);

    @InheritInverseConfiguration(name = "convert")
    PathInfoDO convertToDO(PathInfoDTO dto);

    PathInfoDTO convertToDTO(PathInfoPageReq req);

    PathInfoDTO convertToDTO(PathInfoCreateReq req);

    PathInfoDTO convertToDTO(PathInfoUpdateReq req);

    PathInfoPageResp convertToPageResp(PathInfoDTO dto);

    PathInfoViewResp convertToViewResp(PathInfoDTO dto);

    PathInfoCreateResp convertToCreateResp(PathInfoDTO dto);

}