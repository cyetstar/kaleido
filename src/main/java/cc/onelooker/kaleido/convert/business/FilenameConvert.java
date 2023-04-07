package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.FilenameDTO;
import cc.onelooker.kaleido.entity.business.FilenameDO;
import cc.onelooker.kaleido.dto.business.req.FilenamePageReq;
import cc.onelooker.kaleido.dto.business.req.FilenameCreateReq;
import cc.onelooker.kaleido.dto.business.req.FilenameUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.FilenamePageResp;
import cc.onelooker.kaleido.dto.business.resp.FilenameViewResp;
import cc.onelooker.kaleido.dto.business.resp.FilenameCreateResp;
import cc.onelooker.kaleido.dto.business.exp.FilenameExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface FilenameConvert {

    FilenameConvert INSTANCE = Mappers.getMapper(FilenameConvert.class);

    FilenameDTO convert(FilenameDO entity);

    @InheritInverseConfiguration(name="convert")
    FilenameDO convertToDO(FilenameDTO dto);

    FilenameDTO convertToDTO(FilenamePageReq req);

    FilenameDTO convertToDTO(FilenameCreateReq req);

    FilenameDTO convertToDTO(FilenameUpdateReq req);

    FilenamePageResp convertToPageResp(FilenameDTO dto);

    FilenameViewResp convertToViewResp(FilenameDTO dto);

    FilenameCreateResp convertToCreateResp(FilenameDTO dto);

    FilenameExp convertToExp(FilenameDTO dto);

}