package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysResourceDTO;
import cc.onelooker.kaleido.dto.req.SysResourceCreateReq;
import cc.onelooker.kaleido.dto.req.SysResourcePageReq;
import cc.onelooker.kaleido.dto.req.SysResourceUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysResourceCreateResp;
import cc.onelooker.kaleido.dto.resp.SysResourceListTypeResp;
import cc.onelooker.kaleido.dto.resp.SysResourcePageResp;
import cc.onelooker.kaleido.dto.resp.SysResourceViewResp;
import cc.onelooker.kaleido.entity.SysResourceDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 资源表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysResourceConvert {
    SysResourceConvert INSTANCE = Mappers.getMapper(SysResourceConvert.class);

    SysResourceDTO convert(SysResourceDO sysResourceDO);

    @InheritInverseConfiguration(name = "convert")
    SysResourceDO convertToDO(SysResourceDTO sysResourceDTO);

    SysResourceDTO convertToDTO(SysResourcePageReq req);

    SysResourceDTO convertToDTO(SysResourceCreateReq req);

    SysResourceDTO convertToDTO(SysResourceUpdateReq req);

    SysResourcePageResp convertToPageResp(SysResourceDTO dto);

    SysResourceViewResp convertToViewResp(SysResourceDTO dto);

    SysResourceCreateResp convertToCreateResp(SysResourceDTO dto);

    SysResourceListTypeResp convertToListTypeResp(SysResourceDTO dto);

}