package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author xiadawei
 * @Date 2023-12-21 14:42:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieCollectionServiceTest {

    @Autowired
    private MovieCollectionService movieCollectionService;

    @Autowired
    private PlexApiService plexApiService;

    @Test
    void deleteAll() {
        List<MovieCollectionDTO> movieCollectionDTOList = movieCollectionService.list(null);
        log.info("总共【{}】条记录待删除", movieCollectionDTOList.size());
        for (MovieCollectionDTO movieCollectionDTO : movieCollectionDTOList) {
            try {
                plexApiService.deleteCollection(movieCollectionDTO.getId());
                movieCollectionService.deleteById(movieCollectionDTO.getId());
                log.info("【{}】删除成功", movieCollectionDTO.getTitle());
            } catch (Exception e) {
                log.error("【{}】删除发生错误:{}", movieCollectionDTO.getTitle(), ExceptionUtil.getMessage(e));
            }
        }
    }

}