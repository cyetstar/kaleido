package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysDictDTO;
import cc.onelooker.kaleido.dto.req.SysDictCreateReq;
import cc.onelooker.kaleido.dto.req.SysDictPageReq;
import cc.onelooker.kaleido.dto.req.SysDictUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysDictCreateResp;
import cc.onelooker.kaleido.dto.resp.SysDictPageResp;
import cc.onelooker.kaleido.dto.resp.SysDictViewResp;
import cc.onelooker.kaleido.entity.SysDictDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 字典表Convert
 *
 * @author cyetstar
 * @date 2022-11-19 23:17:28
 */
@Mapper
public interface SysDictConvert {
    SysDictConvert INSTANCE = Mappers.getMapper(SysDictConvert.class);

    SysDictDTO convert(SysDictDO sysDictDO);

    List<SysDictDTO> convert(List<SysDictDO> sysDictDOList);

    @InheritInverseConfiguration(name = "convert")
    SysDictDO convertToDO(SysDictDTO sysDictDTO);

    SysDictDO convertToDO(SysDictCreateReq req);

    SysDictDO convertToDO(SysDictUpdateReq req);

    SysDictDTO convertToDTO(SysDictPageReq req);

    SysDictDTO convertToDTO(SysDictCreateReq req);

    SysDictDTO convertToDTO(SysDictUpdateReq req);

    SysDictPageResp convertToPageResp(SysDictDTO dto);

    List<SysDictPageResp> convertToPageResp(List<SysDictDO> dtoList);

    SysDictViewResp convertToViewResp(SysDictDTO dto);

    SysDictCreateResp convertToCreateResp(SysDictDTO dto);

}