package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.MovieThreadDTO;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.zjjcnt.common.util.IdentityUtils;
import com.zjjcnt.common.util.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @Author xiadawei
 * @Date 2023-12-21 09:28:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieThreadServiceTest {

    @Autowired
    private MovieThreadService movieThreadService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void downloadTorrent() {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        MovieThreadDTO param = new MovieThreadDTO();
        param.setStatus(ThreadStatus.todo.ordinal());
        List<MovieThreadDTO> movieThreadDTOList = movieThreadService.list(param);
        for (MovieThreadDTO movieThreadDTO : movieThreadDTOList) {
            if (StringUtils.isBlank(movieThreadDTO.getLinks())) {
                continue;
            }
            for (String link : StringUtils.split(movieThreadDTO.getLinks(), Constants.COMMA)) {
                if (StringUtils.contains(link, "pt.eastgame.org")) {
                    downloadTorrent(link, movieThreadDTO);
                }
            }
        }
    }

    private void downloadTorrent(String url, MovieThreadDTO movieThreadDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.COOKIE, "c_secure_uid=Mjk2OTQ%3D; c_secure_pass=158ee141f59b0c48b3311bcc198f7f55; c_secure_ssl=eWVhaA%3D%3D; c_secure_tracker_ssl=eWVhaA%3D%3D; c_secure_login=bm9wZQ%3D%3D; __utmz=160627708.1696349189.6.2.utmcsr=192.168.3.100:1329|utmccn=(referral)|utmcmd=referral|utmcct=/; __utma=160627708.7976078.1693316768.1696349189.1701010689.7; OUTFOX_SEARCH_USER_ID_NCOO=147191347.17357275");
            RequestEntity<byte[]> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
            HttpHeaders responseEntityHeaders = responseEntity.getHeaders();
            String contentDisposition = responseEntityHeaders.getFirst("Content-Disposition");
            //attachment; filename=%5BTLF%5D.%E7%BD%97%E5%AF%86%E6%AC%A7%E4%B8%8E%E6%9C%B1%E4%B8%BD%E5%8F%B6%E5%90%8E%E7%8E%B0%E4%BB%A3%E6%BF%80%E6%83%85%E7%89%88.Romeo%2BJuliet.1996.BD.2Audio.MiniSD-TLF.mkv.torrent
            String decodeContentDisposition = URLUtil.decode(contentDisposition);
            String fileName = StringUtils.substringAfter(decodeContentDisposition, "filename=");
            FileUtils.writeByteArrayToFile(Paths.get("/Users/cyetstar/Downloads/tlf", fileName).toFile(), responseEntity.getBody());
        } catch (Exception e) {
            log.error("【{}】下载出错，{}", movieThreadDTO.getTitle(), url);
        } finally {
            ThreadUtil.sleep(RandomUtil.randomInt(500));
        }
    }

}