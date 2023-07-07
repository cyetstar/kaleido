package cc.onelooker.kaleido.mexc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-07-06 00:44:00
 * @Description TODO
 */
@Component
public class MexcApiService {

    private final static String API_DEFAULT_SYMBOLS = "/api/v3/defaultSymbols";
    private final static String API_EXCHANGE_INFO = "/api/v3/exchangeInfo?symbol={symbol}";

    private String url = "https://api.mexc.com";

    @Autowired
    private RestTemplate restTemplate;

    private String getAPIUrl(String api) {
        return url + api;
    }

    public List<String> listDefaultSymbols() {
        ResponeResult<List> responeResult = restTemplate.getForObject(getAPIUrl(API_DEFAULT_SYMBOLS), ResponeResult.class);
        List<String> symbols = responeResult.getData();
        return symbols;
    }

    public ExchangeInfo getExchangeInfo(String symbol) {
        return restTemplate.getForObject(getAPIUrl(API_EXCHANGE_INFO), ExchangeInfo.class, symbol);
    }
}
