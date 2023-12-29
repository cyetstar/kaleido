package cc.onelooker.kaleido.third.douban;

import cc.onelooker.kaleido.utils.ConfigUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-12-03 17:20:00
 * @Description TODO
 */
@Component
public class DoubanApiService {

    private final static String URL = "https://api.douban.com";
    private final static String API_SEARCH_MOVIE = "/v2/movie/search";
    private final static String API_FIND_MOVIE_SUBJECT = "/v2/movie/subject/{doubanId}";
    private final static String API_FIND_MOVIE = "/v2/movie/{doubanId}";
    private final static String API_FIND_MOVIE_BY_IMDB = "/v2/movie/imdb/{imdbId}";
    private final static String API_LIST_MOVIE_WEEKLY = "/v2/movie/weekly";
    private String apikey = null;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(this.apikey)) {
            this.apikey = ConfigUtils.getSysConfig("doubanApikey");
        }
    }

    public List<Movie> searchMovie(String keywords) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", this.apikey);
        map.add("q", keywords);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(URL + API_SEARCH_MOVIE, request, JSONObject.class);
        JSONObject jsonObject = response.getBody();
        if (jsonObject != null) {
            return jsonObject.getJSONArray("subjects").toJavaList(Movie.class);
        }
        return Lists.newArrayList();
    }

    public Movie findMovieById(String doubanId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", this.apikey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Movie> response = restTemplate.postForEntity(URL + API_FIND_MOVIE_SUBJECT, request, Movie.class, doubanId);
        return response.getBody();
    }

    public Movie findMovieByImdbId(String imdbId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", this.apikey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(URL + API_FIND_MOVIE_BY_IMDB, request, JSONObject.class, imdbId);
        JSONObject jsonObject = response.getBody();
        String doubanId = StringUtils.substringAfterLast(jsonObject.getString("id"), Constants.SLASH);
        return findMovieById(doubanId);
    }

    public List<Subject> listMovieWeekly() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("apikey", this.apikey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(URL + API_LIST_MOVIE_WEEKLY, request, JSONObject.class);
//        String text = "{\n" +
//                "    \"subjects\": [\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 9.2,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 8.0,\n" +
//                "                        \"3\": 62.0,\n" +
//                "                        \"2\": 6.0,\n" +
//                "                        \"5\": 845.0,\n" +
//                "                        \"4\": 289.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"50\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"纪录片\",\n" +
//                "                    \"音乐\",\n" +
//                "                    \"歌舞\"\n" +
//                "                ],\n" +
//                "                \"title\": \"泰勒·斯威夫特：时代巡回演唱会\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/view/personage/raw/public/0d31d0e44f3cfaecb7445ecb19474ff9.jpg\",\n" +
//                "                            \"large\": \"https://img1.doubanio.com/view/personage/raw/public/0d31d0e44f3cfaecb7445ecb19474ff9.jpg\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/view/personage/raw/public/0d31d0e44f3cfaecb7445ecb19474ff9.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Taylor Swift\",\n" +
//                "                        \"name\": \"泰勒·斯威夫特\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1001373/\",\n" +
//                "                        \"id\": \"1001373\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Amanda Balen\",\n" +
//                "                        \"name\": \"阿曼达·巴伦\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1513193/\",\n" +
//                "                        \"id\": \"1513193\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Taylor Banks\",\n" +
//                "                        \"name\": \"泰勒·班克斯\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1513196/\",\n" +
//                "                        \"id\": \"1513196\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"170分钟\",\n" +
//                "                    \"181分钟(加长版)\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 7785,\n" +
//                "                \"mainland_pubdate\": \"2023-12-31\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"Taylor Swift: The Eras Tour\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/personage/raw/public/1f143910855d7e984d592ea1e8e1bc6a.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/personage/raw/public/1f143910855d7e984d592ea1e8e1bc6a.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/personage/raw/public/1f143910855d7e984d592ea1e8e1bc6a.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Sam Wrench\",\n" +
//                "                        \"name\": \"萨姆·伦奇\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1425199/\",\n" +
//                "                        \"id\": \"1425199\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-10-13(美国)\",\n" +
//                "                    \"2023-12-31(中国大陆)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901984940.jpg\",\n" +
//                "                    \"large\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901984940.jpg\",\n" +
//                "                    \"medium\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901984940.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/36538847/\",\n" +
//                "                \"id\": \"36538847\"\n" +
//                "            },\n" +
//                "            \"rank\": 1,\n" +
//                "            \"delta\": 10\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 7.9,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 30.0,\n" +
//                "                        \"3\": 814.0,\n" +
//                "                        \"2\": 174.0,\n" +
//                "                        \"5\": 1121.0,\n" +
//                "                        \"4\": 1584.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"40\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"剧情\"\n" +
//                "                ],\n" +
//                "                \"title\": \"完美的日子\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/personage/raw/public/e665f9516ac7422c877237accc525245.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/personage/raw/public/e665f9516ac7422c877237accc525245.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/personage/raw/public/e665f9516ac7422c877237accc525245.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Kôji Yakusho\",\n" +
//                "                        \"name\": \"役所广司\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1165478/\",\n" +
//                "                        \"id\": \"1165478\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1514644037.21.jpg\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1514644037.21.jpg\",\n" +
//                "                            \"medium\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1514644037.21.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Tokio Emoto\",\n" +
//                "                        \"name\": \"柄本时生\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1313539/\",\n" +
//                "                        \"id\": \"1313539\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img2.doubanio.com/view/personage/raw/public/d925a87faf237590ceb1d7f8cca04951.jpg\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/view/personage/raw/public/d925a87faf237590ceb1d7f8cca04951.jpg\",\n" +
//                "                            \"medium\": \"https://img2.doubanio.com/view/personage/raw/public/d925a87faf237590ceb1d7f8cca04951.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"中野有纱\",\n" +
//                "                        \"name\": \"中野有纱\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1491508/\",\n" +
//                "                        \"id\": \"1491508\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"123分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 5176,\n" +
//                "                \"mainland_pubdate\": \"\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"Perfect Days\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/img/author/raw/public/1511495449.53.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/img/author/raw/public/1511495449.53.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/img/author/raw/public/1511495449.53.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Wim Wenders\",\n" +
//                "                        \"name\": \"维姆·文德斯\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1054402/\",\n" +
//                "                        \"id\": \"1054402\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-05-25(戛纳电影节)\",\n" +
//                "                    \"2023-12-21(德国)\",\n" +
//                "                    \"2023-12-22(日本)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2898894527.jpg\",\n" +
//                "                    \"large\": \"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2898894527.jpg\",\n" +
//                "                    \"medium\": \"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2898894527.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/35902857/\",\n" +
//                "                \"id\": \"35902857\"\n" +
//                "            },\n" +
//                "            \"rank\": 2,\n" +
//                "            \"delta\": 0\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 7.9,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 8.0,\n" +
//                "                        \"3\": 190.0,\n" +
//                "                        \"2\": 32.0,\n" +
//                "                        \"5\": 214.0,\n" +
//                "                        \"4\": 441.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"40\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"喜剧\"\n" +
//                "                ],\n" +
//                "                \"title\": \"不要太期待世界末日\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img2.doubanio.com/view/personage/raw/public/ad188ad7293966095537d4be10e824ae.jpg\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/view/personage/raw/public/ad188ad7293966095537d4be10e824ae.jpg\",\n" +
//                "                            \"medium\": \"https://img2.doubanio.com/view/personage/raw/public/ad188ad7293966095537d4be10e824ae.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Ilinca Manolache\",\n" +
//                "                        \"name\": \"伊林卡·马诺拉切\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1515159/\",\n" +
//                "                        \"id\": \"1515159\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/personage/raw/public/65156920e65d56784ee245817900af6f.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/personage/raw/public/65156920e65d56784ee245817900af6f.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/personage/raw/public/65156920e65d56784ee245817900af6f.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Nina Hoss\",\n" +
//                "                        \"name\": \"尼娜·霍斯\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1009688/\",\n" +
//                "                        \"id\": \"1009688\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Ovidiu Pîrsan\",\n" +
//                "                        \"name\": \"奥维迪乌·皮尔桑\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1515153/\",\n" +
//                "                        \"id\": \"1515153\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"163分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 1286,\n" +
//                "                \"mainland_pubdate\": \"\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"Nu astepta prea mult de la sfârsitul lumii\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1423992351.25.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1423992351.25.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1423992351.25.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Radu Jude\",\n" +
//                "                        \"name\": \"拉杜·裘德\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1305166/\",\n" +
//                "                        \"id\": \"1305166\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-08-04(洛迦诺电影节)\",\n" +
//                "                    \"2023-10-27(罗马尼亚)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2901415646.jpg\",\n" +
//                "                    \"large\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2901415646.jpg\",\n" +
//                "                    \"medium\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2901415646.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/35772577/\",\n" +
//                "                \"id\": \"35772577\"\n" +
//                "            },\n" +
//                "            \"rank\": 3,\n" +
//                "            \"delta\": 8\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 7.8,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 21.0,\n" +
//                "                        \"3\": 1180.0,\n" +
//                "                        \"2\": 105.0,\n" +
//                "                        \"5\": 997.0,\n" +
//                "                        \"4\": 2499.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"40\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"剧情\",\n" +
//                "                    \"喜剧\"\n" +
//                "                ],\n" +
//                "                \"title\": \"留校联盟\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/view/celebrity/raw/public/p4309.jpg\",\n" +
//                "                            \"large\": \"https://img1.doubanio.com/view/celebrity/raw/public/p4309.jpg\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/view/celebrity/raw/public/p4309.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Paul Giamatti\",\n" +
//                "                        \"name\": \"保罗·吉亚玛提\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1017893/\",\n" +
//                "                        \"id\": \"1017893\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/personage/raw/public/a9afcf27c20588469ba4498a837255f6.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/personage/raw/public/a9afcf27c20588469ba4498a837255f6.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/personage/raw/public/a9afcf27c20588469ba4498a837255f6.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Da'Vine Joy Randolph\",\n" +
//                "                        \"name\": \"达明·乔伊·伦道夫\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1365682/\",\n" +
//                "                        \"id\": \"1365682\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/personage/raw/public/8615e869b100ecd65b2d3d3a498b0fc4.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/personage/raw/public/8615e869b100ecd65b2d3d3a498b0fc4.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/personage/raw/public/8615e869b100ecd65b2d3d3a498b0fc4.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Dominic Sessa\",\n" +
//                "                        \"name\": \"多米尼克·塞萨\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1501396/\",\n" +
//                "                        \"id\": \"1501396\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"133分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 6426,\n" +
//                "                \"mainland_pubdate\": \"\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"The Holdovers\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1392345017.24.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1392345017.24.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1392345017.24.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Alexander Payne\",\n" +
//                "                        \"name\": \"亚历山大·佩恩\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1004720/\",\n" +
//                "                        \"id\": \"1004720\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-08-31(特柳赖德电影节)\",\n" +
//                "                    \"2023-11-10(美国)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2898987215.jpg\",\n" +
//                "                    \"large\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2898987215.jpg\",\n" +
//                "                    \"medium\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2898987215.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/35496212/\",\n" +
//                "                \"id\": \"35496212\"\n" +
//                "            },\n" +
//                "            \"rank\": 4,\n" +
//                "            \"delta\": -3\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 7.5,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 13.0,\n" +
//                "                        \"3\": 163.0,\n" +
//                "                        \"2\": 30.0,\n" +
//                "                        \"5\": 120.0,\n" +
//                "                        \"4\": 247.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"40\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"剧情\"\n" +
//                "                ],\n" +
//                "                \"title\": \"绿色边境\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Jalal Altawil\",\n" +
//                "                        \"name\": \"\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1500236/\",\n" +
//                "                        \"id\": \"1500236\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1480387007.13.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1480387007.13.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1480387007.13.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Maja Ostaszewska\",\n" +
//                "                        \"name\": \"玛雅·奥丝塔泽斯卡\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1050883/\",\n" +
//                "                        \"id\": \"1050883\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/view/celebrity/raw/public/pTgVrnIM5Gescel_avatar_uploaded1535438338.69.jpg\",\n" +
//                "                            \"large\": \"https://img1.doubanio.com/view/celebrity/raw/public/pTgVrnIM5Gescel_avatar_uploaded1535438338.69.jpg\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/view/celebrity/raw/public/pTgVrnIM5Gescel_avatar_uploaded1535438338.69.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Behi Djanati Atai\",\n" +
//                "                        \"name\": \"贝希·贾纳蒂·阿泰\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1399992/\",\n" +
//                "                        \"id\": \"1399992\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"147分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 851,\n" +
//                "                \"mainland_pubdate\": \"\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"Zielona granica\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/celebrity/raw/public/p10505.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/celebrity/raw/public/p10505.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/celebrity/raw/public/p10505.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Agnieszka Holland\",\n" +
//                "                        \"name\": \"阿格涅丝卡·霍兰\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1036838/\",\n" +
//                "                        \"id\": \"1036838\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-09-05(威尼斯电影节)\",\n" +
//                "                    \"2023-09-22(波兰)\",\n" +
//                "                    \"2024-02-01(德国)\",\n" +
//                "                    \"2024-02-07(法国)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2897561169.jpg\",\n" +
//                "                    \"large\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2897561169.jpg\",\n" +
//                "                    \"medium\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2897561169.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/36217949/\",\n" +
//                "                \"id\": \"36217949\"\n" +
//                "            },\n" +
//                "            \"rank\": 5,\n" +
//                "            \"delta\": -2\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 7.5,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 4.0,\n" +
//                "                        \"3\": 222.0,\n" +
//                "                        \"2\": 25.0,\n" +
//                "                        \"5\": 96.0,\n" +
//                "                        \"4\": 342.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"40\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"剧情\",\n" +
//                "                    \"喜剧\"\n" +
//                "                ],\n" +
//                "                \"title\": \"扬妮克\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/view/personage/raw/public/580551c82b32399d55947f209fc800b8.jpg\",\n" +
//                "                            \"large\": \"https://img1.doubanio.com/view/personage/raw/public/580551c82b32399d55947f209fc800b8.jpg\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/view/personage/raw/public/580551c82b32399d55947f209fc800b8.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Raphaël Quenard\",\n" +
//                "                        \"name\": \"拉斐尔·奎纳德\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1454552/\",\n" +
//                "                        \"id\": \"1454552\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/celebrity/raw/public/p21213.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/celebrity/raw/public/p21213.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/celebrity/raw/public/p21213.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Pio Marmaï\",\n" +
//                "                        \"name\": \"皮奥·马麦\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1311921/\",\n" +
//                "                        \"id\": \"1311921\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1580295226.11.jpg\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1580295226.11.jpg\",\n" +
//                "                            \"medium\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1580295226.11.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Blanche Gardin\",\n" +
//                "                        \"name\": \"布朗什·加丁\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1392878/\",\n" +
//                "                        \"id\": \"1392878\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"65分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 1004,\n" +
//                "                \"mainland_pubdate\": \"\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"Yannick\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/personage/raw/public/5471ef0a58020070ef12f77e48433e66.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/personage/raw/public/5471ef0a58020070ef12f77e48433e66.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/personage/raw/public/5471ef0a58020070ef12f77e48433e66.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Quentin Dupieux\",\n" +
//                "                        \"name\": \"昆汀·杜皮约\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1315649/\",\n" +
//                "                        \"id\": \"1315649\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-08-02(法国)\",\n" +
//                "                    \"2023-08-03(洛迦诺电影节)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901915929.jpg\",\n" +
//                "                    \"large\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901915929.jpg\",\n" +
//                "                    \"medium\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901915929.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/36352491/\",\n" +
//                "                \"id\": \"36352491\"\n" +
//                "            },\n" +
//                "            \"rank\": 6,\n" +
//                "            \"delta\": 5\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 7.6,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 41.0,\n" +
//                "                        \"3\": 2397.0,\n" +
//                "                        \"2\": 250.0,\n" +
//                "                        \"5\": 1422.0,\n" +
//                "                        \"4\": 3423.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"40\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"喜剧\",\n" +
//                "                    \"歌舞\",\n" +
//                "                    \"奇幻\"\n" +
//                "                ],\n" +
//                "                \"title\": \"旺卡\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/personage/raw/public/111465f37abd05ebdac132a408ca6c24.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/personage/raw/public/111465f37abd05ebdac132a408ca6c24.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/personage/raw/public/111465f37abd05ebdac132a408ca6c24.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Timothée Chalamet\",\n" +
//                "                        \"name\": \"提莫西·查拉梅\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1325862/\",\n" +
//                "                        \"id\": \"1325862\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/view/personage/raw/public/1ee0864be9290f7eb16fc74436905879.jpg\",\n" +
//                "                            \"large\": \"https://img1.doubanio.com/view/personage/raw/public/1ee0864be9290f7eb16fc74436905879.jpg\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/view/personage/raw/public/1ee0864be9290f7eb16fc74436905879.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Olivia Colman\",\n" +
//                "                        \"name\": \"奥利维娅·科尔曼\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1004900/\",\n" +
//                "                        \"id\": \"1004900\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1871.jpg\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1871.jpg\",\n" +
//                "                            \"medium\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1871.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Hugh Grant\",\n" +
//                "                        \"name\": \"休·格兰特\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1003493/\",\n" +
//                "                        \"id\": \"1003493\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"116分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 60758,\n" +
//                "                \"mainland_pubdate\": \"2023-12-08\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"Wonka\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1425285993.52.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1425285993.52.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1425285993.52.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Paul King\",\n" +
//                "                        \"name\": \"保罗·金\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1313689/\",\n" +
//                "                        \"id\": \"1313689\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-12-06(中国台湾)\",\n" +
//                "                    \"2023-12-08(中国大陆)\",\n" +
//                "                    \"2023-12-15(美国)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901229830.jpg\",\n" +
//                "                    \"large\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901229830.jpg\",\n" +
//                "                    \"medium\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2901229830.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/26897888/\",\n" +
//                "                \"id\": \"26897888\"\n" +
//                "            },\n" +
//                "            \"rank\": 7,\n" +
//                "            \"delta\": 4\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 7.3,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 341.0,\n" +
//                "                        \"3\": 10825.0,\n" +
//                "                        \"2\": 1477.0,\n" +
//                "                        \"5\": 4338.0,\n" +
//                "                        \"4\": 12282.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"40\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"剧情\",\n" +
//                "                    \"悬疑\",\n" +
//                "                    \"惊悚\"\n" +
//                "                ],\n" +
//                "                \"title\": \"花月杀手\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/personage/raw/public/610e400f3fd1ecab407677f7300bc735.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/personage/raw/public/610e400f3fd1ecab407677f7300bc735.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/personage/raw/public/610e400f3fd1ecab407677f7300bc735.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Leonardo DiCaprio\",\n" +
//                "                        \"name\": \"莱昂纳多·迪卡普里奥\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1041029/\",\n" +
//                "                        \"id\": \"1041029\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img2.doubanio.com/view/celebrity/raw/public/p47221.jpg\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/view/celebrity/raw/public/p47221.jpg\",\n" +
//                "                            \"medium\": \"https://img2.doubanio.com/view/celebrity/raw/public/p47221.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Robert De Niro\",\n" +
//                "                        \"name\": \"罗伯特·德尼罗\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1054445/\",\n" +
//                "                        \"id\": \"1054445\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1477302573.96.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1477302573.96.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/celebrity/raw/public/p1477302573.96.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Jesse Plemons\",\n" +
//                "                        \"name\": \"杰西·普莱蒙\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1129549/\",\n" +
//                "                        \"id\": \"1129549\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"206分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 38148,\n" +
//                "                \"mainland_pubdate\": \"\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"Killers of the Flower Moon\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/personage/raw/public/43fb2ed0ca3e05305ca1743b809b3ae7.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/personage/raw/public/43fb2ed0ca3e05305ca1743b809b3ae7.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/personage/raw/public/43fb2ed0ca3e05305ca1743b809b3ae7.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Martin Scorsese\",\n" +
//                "                        \"name\": \"马丁·斯科塞斯\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1054425/\",\n" +
//                "                        \"id\": \"1054425\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-05-20(戛纳国际电影节)\",\n" +
//                "                    \"2023-10-20(美国)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2897460998.jpg\",\n" +
//                "                    \"large\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2897460998.jpg\",\n" +
//                "                    \"medium\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2897460998.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/26745332/\",\n" +
//                "                \"id\": \"26745332\"\n" +
//                "            },\n" +
//                "            \"rank\": 8,\n" +
//                "            \"delta\": -1\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 7.1,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 54.0,\n" +
//                "                        \"3\": 2805.0,\n" +
//                "                        \"2\": 334.0,\n" +
//                "                        \"5\": 644.0,\n" +
//                "                        \"4\": 2986.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"35\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"剧情\"\n" +
//                "                ],\n" +
//                "                \"title\": \"五月十二月\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/view/celebrity/raw/public/p19768.jpg\",\n" +
//                "                            \"large\": \"https://img1.doubanio.com/view/celebrity/raw/public/p19768.jpg\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/view/celebrity/raw/public/p19768.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Natalie Portman\",\n" +
//                "                        \"name\": \"娜塔莉·波特曼\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1054454/\",\n" +
//                "                        \"id\": \"1054454\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1425093980.43.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1425093980.43.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/celebrity/raw/public/p1425093980.43.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Julianne Moore\",\n" +
//                "                        \"name\": \"朱丽安·摩尔\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1054519/\",\n" +
//                "                        \"id\": \"1054519\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img9.doubanio.com/view/personage/raw/public/b4b0559629870300ba31d14728844635.jpg\",\n" +
//                "                            \"large\": \"https://img9.doubanio.com/view/personage/raw/public/b4b0559629870300ba31d14728844635.jpg\",\n" +
//                "                            \"medium\": \"https://img9.doubanio.com/view/personage/raw/public/b4b0559629870300ba31d14728844635.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Charles Melton\",\n" +
//                "                        \"name\": \"查尔斯·梅尔顿\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1389614/\",\n" +
//                "                        \"id\": \"1389614\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"113分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 9409,\n" +
//                "                \"mainland_pubdate\": \"\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"May December\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/celebrity/raw/public/p14297.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/celebrity/raw/public/p14297.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/celebrity/raw/public/p14297.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Todd Haynes\",\n" +
//                "                        \"name\": \"托德·海因斯\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1014069/\",\n" +
//                "                        \"id\": \"1014069\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-05-20(戛纳电影节)\",\n" +
//                "                    \"2023-11-17(美国)\",\n" +
//                "                    \"2023-12-01(美国网络)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2898667786.jpg\",\n" +
//                "                    \"large\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2898667786.jpg\",\n" +
//                "                    \"medium\": \"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2898667786.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/35295277/\",\n" +
//                "                \"id\": \"35295277\"\n" +
//                "            },\n" +
//                "            \"rank\": 9,\n" +
//                "            \"delta\": 2\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"subject\": {\n" +
//                "                \"rating\": {\n" +
//                "                    \"max\": 10,\n" +
//                "                    \"average\": 6.9,\n" +
//                "                    \"details\": {\n" +
//                "                        \"1\": 23.0,\n" +
//                "                        \"3\": 324.0,\n" +
//                "                        \"2\": 84.0,\n" +
//                "                        \"5\": 105.0,\n" +
//                "                        \"4\": 290.0\n" +
//                "                    },\n" +
//                "                    \"stars\": \"35\",\n" +
//                "                    \"min\": 0\n" +
//                "                },\n" +
//                "                \"genres\": [\n" +
//                "                    \"剧情\"\n" +
//                "                ],\n" +
//                "                \"title\": \"金色茧房\",\n" +
//                "                \"casts\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Le Phong Vu\",\n" +
//                "                        \"name\": \"黎风武\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1502724/\",\n" +
//                "                        \"id\": \"1502724\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Nguyen Thi Truc Quynh\",\n" +
//                "                        \"name\": \"阮氏竹琼\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1502723/\",\n" +
//                "                        \"id\": \"1502723\"\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
//                "                            \"large\": \"https://img2.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
//                "                            \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Nguyen Thinh\",\n" +
//                "                        \"name\": \"阮盛\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1502725/\",\n" +
//                "                        \"id\": \"1502725\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"durations\": [\n" +
//                "                    \"179分钟\"\n" +
//                "                ],\n" +
//                "                \"collect_count\": 1187,\n" +
//                "                \"mainland_pubdate\": \"\",\n" +
//                "                \"has_video\": false,\n" +
//                "                \"original_title\": \"Bên Trong Vỏ Kén Vàng\",\n" +
//                "                \"subtype\": \"movie\",\n" +
//                "                \"directors\": [\n" +
//                "                    {\n" +
//                "                        \"avatars\": {\n" +
//                "                            \"small\": \"https://img3.doubanio.com/view/personage/raw/public/2744496a4f924d482de730cb0fbf94e3.jpg\",\n" +
//                "                            \"large\": \"https://img3.doubanio.com/view/personage/raw/public/2744496a4f924d482de730cb0fbf94e3.jpg\",\n" +
//                "                            \"medium\": \"https://img3.doubanio.com/view/personage/raw/public/2744496a4f924d482de730cb0fbf94e3.jpg\"\n" +
//                "                        },\n" +
//                "                        \"name_en\": \"Thiên An Pham\",\n" +
//                "                        \"name\": \"范天安\",\n" +
//                "                        \"alt\": \"https://movie.douban.com/celebrity/1489628/\",\n" +
//                "                        \"id\": \"1489628\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"pubdates\": [\n" +
//                "                    \"2023-05-24(戛纳电影节)\",\n" +
//                "                    \"2023-09-20(法国)\",\n" +
//                "                    \"2023-10-15(平遥国际电影展)\"\n" +
//                "                ],\n" +
//                "                \"year\": \"2023\",\n" +
//                "                \"images\": {\n" +
//                "                    \"small\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2895250199.jpg\",\n" +
//                "                    \"large\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2895250199.jpg\",\n" +
//                "                    \"medium\": \"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2895250199.jpg\"\n" +
//                "                },\n" +
//                "                \"alt\": \"https://movie.douban.com/subject/36367140/\",\n" +
//                "                \"id\": \"36367140\"\n" +
//                "            },\n" +
//                "            \"rank\": 10,\n" +
//                "            \"delta\": 1\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"title\": \"豆瓣电影本周口碑榜\"\n" +
//                "}";
//        JSONObject jsonObject = JSONObject.parseObject(text);
        JSONObject jsonObject = response.getBody();
        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            return jsonArray.toJavaList(Subject.class);
        }
        return Lists.newArrayList();
    }
}
