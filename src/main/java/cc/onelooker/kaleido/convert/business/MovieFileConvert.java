package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieFileDTO;
import cc.onelooker.kaleido.entity.business.MovieFileDO;
import cc.onelooker.kaleido.dto.business.req.MovieFilePageReq;
import cc.onelooker.kaleido.dto.business.req.MovieFileCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieFileUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieFilePageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieFileViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieFileCreateResp;
import cc.onelooker.kaleido.exp.business.MovieFileExp;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
/**
* 电影文件Convert
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/
@Mapper
public interface MovieFileConvert {

    MovieFileConvert INSTANCE = Mappers.getMapper(MovieFileConvert.class);

    MovieFileDTO convert(MovieFileDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieFileDO convertToDO(MovieFileDTO dto);

    MovieFileDTO convertToDTO(MovieFilePageReq req);

    MovieFileDTO convertToDTO(MovieFileCreateReq req);

    MovieFileDTO convertToDTO(MovieFileUpdateReq req);

    MovieFilePageResp convertToPageResp(MovieFileDTO dto);

    MovieFileViewResp convertToViewResp(MovieFileDTO dto);

    MovieFileCreateResp convertToCreateResp(MovieFileDTO dto);

    MovieFileExp convertToExp(MovieFileDTO dto);

}