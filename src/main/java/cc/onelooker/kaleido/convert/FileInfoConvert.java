package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.FileInfoDTO;
import cc.onelooker.kaleido.entity.FileInfoDO;
import com.zjjcnt.common.file.FileInfo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 文件信息Convert
 *
 * @author xiadawei
 * @date 2022-05-31 00:27:23
 */
@Mapper
public interface FileInfoConvert {
    FileInfoConvert INSTANCE = Mappers.getMapper(FileInfoConvert.class);

    FileInfoDTO convert(FileInfoDO fileInfoDO);

    @InheritInverseConfiguration(name = "convert")
    FileInfoDO convertToDO(FileInfoDTO fileInfoDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "id", target = "fileId")
    })
    FileInfoDTO convert(FileInfo fileInfo);
}