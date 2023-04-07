package cc.onelooker.kaleido.service.system;

import com.zjjcnt.common.core.service.IBaseService;
import cc.onelooker.kaleido.dto.system.FileInfoDTO;

import java.util.List;

/**
 * 文件信息Service
 *
 * @author xiadawei
 * @date 2022-05-31 00:27:23
 */
public interface FileInfoService extends IBaseService<FileInfoDTO> {

    List<String> listFileIdByBizIdAndBizTable(String bizId, String bizTable);

    List<FileInfoDTO> listByBizIdAndBizTable(String bizId, String bizTable);

    FileInfoDTO findByBizIdAndBizTable(String bizId, String bizTable);

    void deleteByFileId(String fileId);

    void bindBizInfo(String fileId, String bizId, String bizTable);

    void bindBizInfo(String fileId, String bizId, String bizTable, String bizType);
}