package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysDeptDTO;
import cc.onelooker.kaleido.dto.req.SysDeptCreateReq;
import cc.onelooker.kaleido.dto.req.SysDeptPageReq;
import cc.onelooker.kaleido.dto.req.SysDeptUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysDeptCreateResp;
import cc.onelooker.kaleido.dto.resp.SysDeptPageResp;
import cc.onelooker.kaleido.dto.resp.SysDeptViewResp;
import cc.onelooker.kaleido.entity.SysDeptDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 部门表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDeptConvert {
    SysDeptConvert INSTANCE = Mappers.getMapper(SysDeptConvert.class);

    SysDeptDTO convert(SysDeptDO sysDeptDO);

    @InheritInverseConfiguration(name = "convert")
    SysDeptDO convertToDO(SysDeptDTO sysDeptDTO);

    SysDeptDTO convertToDTO(SysDeptPageReq req);

    SysDeptDTO convertToDTO(SysDeptCreateReq req);

    SysDeptDTO convertToDTO(SysDeptUpdateReq req);

    SysDeptPageResp convertToPageResp(SysDeptDTO dto);

    SysDeptViewResp convertToViewResp(SysDeptDTO dto);

    SysDeptCreateResp convertToCreateResp(SysDeptDTO dto);

}