package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysDictTypeDTO;
import cc.onelooker.kaleido.dto.req.SysDictTypeCreateReq;
import cc.onelooker.kaleido.dto.req.SysDictTypePageReq;
import cc.onelooker.kaleido.dto.req.SysDictTypeUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysDictTypeCreateResp;
import cc.onelooker.kaleido.dto.resp.SysDictTypePageResp;
import cc.onelooker.kaleido.dto.resp.SysDictTypeViewResp;
import cc.onelooker.kaleido.entity.SysDictTypeDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典表类型表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Mapper
public interface SysDictTypeConvert {
    SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

    SysDictTypeDTO convert(SysDictTypeDO sysDictTypeDO);

    @InheritInverseConfiguration(name = "convert")
    SysDictTypeDO convertToDO(SysDictTypeDTO sysDictTypeDTO);

    SysDictTypeDTO convertToDTO(SysDictTypePageReq req);

    SysDictTypeDTO convertToDTO(SysDictTypeCreateReq req);

    SysDictTypeDTO convertToDTO(SysDictTypeUpdateReq req);

    SysDictTypePageResp convertToPageResp(SysDictTypeDTO dto);

    SysDictTypeViewResp convertToViewResp(SysDictTypeDTO dto);

    SysDictTypeCreateResp convertToCreateResp(SysDictTypeDTO dto);

}