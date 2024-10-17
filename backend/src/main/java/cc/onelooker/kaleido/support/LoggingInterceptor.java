package cc.onelooker.kaleido.support;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2024-10-17 13:52:00
 * @Description TODO
 */
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // 打印请求信息
        logRequest(request, body);

        // 执行请求
        ClientHttpResponse response = execution.execute(request, body);

        // 打印响应信息
        logResponse(response);

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        System.out.println("Request URI: " + request.getURI());
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request Headers: " + request.getHeaders());
        System.out.println("Request Body: " + new String(body, StandardCharsets.UTF_8));
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
        String responseBody = bufferedReader.lines().collect(Collectors.joining("\n"));
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Headers: " + response.getHeaders());
        System.out.println("Response Body: " + responseBody);
    }
}
