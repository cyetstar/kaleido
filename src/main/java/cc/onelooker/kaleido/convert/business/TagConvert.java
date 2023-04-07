package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.TagDTO;
import cc.onelooker.kaleido.entity.business.TagDO;
import cc.onelooker.kaleido.dto.business.req.TagPageReq;
import cc.onelooker.kaleido.dto.business.req.TagCreateReq;
import cc.onelooker.kaleido.dto.business.req.TagUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.TagPageResp;
import cc.onelooker.kaleido.dto.business.resp.TagViewResp;
import cc.onelooker.kaleido.dto.business.resp.TagCreateResp;
import cc.onelooker.kaleido.dto.business.exp.TagExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface TagConvert {

    TagConvert INSTANCE = Mappers.getMapper(TagConvert.class);

    TagDTO convert(TagDO entity);

    @InheritInverseConfiguration(name="convert")
    TagDO convertToDO(TagDTO dto);

    TagDTO convertToDTO(TagPageReq req);

    TagDTO convertToDTO(TagCreateReq req);

    TagDTO convertToDTO(TagUpdateReq req);

    TagPageResp convertToPageResp(TagDTO dto);

    TagViewResp convertToViewResp(TagDTO dto);

    TagCreateResp convertToCreateResp(TagDTO dto);

    TagExp convertToExp(TagDTO dto);

}