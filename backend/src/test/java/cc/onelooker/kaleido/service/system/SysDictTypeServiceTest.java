package cc.onelooker.kaleido.service.system;

import cc.onelooker.kaleido.service.SysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @Author xiadawei
 * @Date 2023-12-29 14:58:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SysDictTypeServiceTest {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Test
    public void syncPlex() {
        sysDictTypeService.syncPlex();
    }

}