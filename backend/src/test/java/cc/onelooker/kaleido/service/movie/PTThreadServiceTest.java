package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.PTThreadDTO;
import cc.onelooker.kaleido.service.PTThreadService;
import com.alibaba.fastjson2.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2024-02-09 23:29:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PTThreadServiceTest {

    @Autowired
    private PTThreadService ptThreadService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void saveList() {
        int page = 126;
        while (true) {
            JSONArray jsonArray = restTemplate.getForObject("http://127.0.0.1:6000/pt/list?page=" + page,
                    JSONArray.class);
            List<Map> list = jsonArray.toList(Map.class);
            if (list.isEmpty()) {
                break;
            }
            for (Map map : list) {
                Long id = Long.valueOf(MapUtils.getString(map, "id"));
                PTThreadDTO ptThreadDTO = ptThreadService.findById(id);
                if (ptThreadDTO != null) {
                    continue;
                }
                ptThreadDTO = new PTThreadDTO();
                ptThreadDTO.setId(id);
                ptThreadDTO.setType(MapUtils.getString(map, "catalog"));
                ptThreadDTO.setTitle(MapUtils.getString(map, "title"));
                ptThreadDTO.setPublishDate(MapUtils.getString(map, "publish_date"));
                ptThreadService.insert(ptThreadDTO);
            }
            page++;
        }
    }
}