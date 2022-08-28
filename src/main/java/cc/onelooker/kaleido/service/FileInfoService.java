package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.FileInfoDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 文件信息Service
 *
 * @author xiadawei
 * @date 2022-05-31 00:27:23
 */
public interface FileInfoService extends IBaseService<FileInfoDTO> {

    String BIZTYPE_SMS = "01"; //说明书
    String BIZTYPE_AZRJ = "02"; //安装软件

    List<FileInfoDTO> listByBizIdAndBizTable(Long bizId, String bizTable);

    FileInfoDTO findByBizIdAndBizTable(Long bizId, String bizTable);
}