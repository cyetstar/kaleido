package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.MovieThreadDTO;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-12-03 01:31:00
 * @Description TODO
 */
@Slf4j
@Component
public class AsyncTaskManager {

    @Autowired
    private MovieThreadService movieThreadService;

    @Autowired
    private MovieManager movieManager;

    @Async
    public void moveFile(List<String> sourcePathList, String targetPath) throws IOException {
        for (String path : sourcePathList) {
            if (Files.isDirectory(Paths.get(path))) {
                NioFileUtils.moveDir(Paths.get(path), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(Paths.get(path), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    @Async
    public void checkThreadStatus() {
        Instant start = Instant.now();
        try {
            log.info("检测发布信息状态启动。");
            MovieThreadDTO param = new MovieThreadDTO();
            param.setStatus(ThreadStatus.todo.ordinal());
            int pageNumber = 1;
            while (true) {
                PageResult<MovieThreadDTO> page = movieThreadService.page(param, Page.of(pageNumber, 1000));
                if (page.isEmpty()) {
                    break;
                }
                List<MovieThreadDTO> records = page.getRecords();
                for (MovieThreadDTO movieThreadDTO : records) {
                    try {
                        movieManager.checkThreadStatus(movieThreadDTO);
                        log.debug("【{}】检测完成。", movieThreadDTO.getTitle());
                    } catch (Exception e) {
                        log.error("【{}】检测发生错误。{}", movieThreadDTO.getTitle(), ExceptionUtil.getMessage(e));
                    }
                }
                pageNumber++;
            }
        } finally {
            Instant end = Instant.now();
            log.info("检测发布信息状态完毕，耗时{}分钟。", Duration.between(start, end).toMinutes());
        }
    }

}
