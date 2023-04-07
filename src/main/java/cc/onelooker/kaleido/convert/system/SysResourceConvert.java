package cc.onelooker.kaleido.convert.system;

import cc.onelooker.kaleido.dto.system.SysResourceDTO;
import cc.onelooker.kaleido.dto.system.exp.SysResourceExp;
import cc.onelooker.kaleido.dto.system.req.SysResourceCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysResourcePageReq;
import cc.onelooker.kaleido.dto.system.req.SysResourceUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysResourceCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysResourceListTypeResp;
import cc.onelooker.kaleido.dto.system.resp.SysResourcePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysResourceViewResp;
import cc.onelooker.kaleido.entity.system.SysResourceDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 资源表Convert
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */
@Mapper
public interface SysResourceConvert {
    SysResourceConvert INSTANCE = Mappers.getMapper(SysResourceConvert.class);

    SysResourceDTO convert(SysResourceDO sysResourceDO);

    @InheritInverseConfiguration(name="convert")
    SysResourceDO convertToDO(SysResourceDTO sysResourceDTO);

    SysResourceDTO convertToDTO(SysResourcePageReq req);

    SysResourceDTO convertToDTO(SysResourceCreateReq req);

    SysResourceDTO convertToDTO(SysResourceUpdateReq req);

    SysResourcePageResp convertToPageResp(SysResourceDTO dto);

    SysResourceViewResp convertToViewResp(SysResourceDTO dto);

    SysResourceCreateResp convertToCreateResp(SysResourceDTO dto);

    SysResourceListTypeResp convertToListTypeResp(SysResourceDTO dto);

    SysResourceExp convertToExp(SysResourceDTO dto);
}