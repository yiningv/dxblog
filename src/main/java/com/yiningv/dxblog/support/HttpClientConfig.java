package com.yiningv.dxblog.support;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Value("${httpclient.max-total}")
    private Integer maxTotal;

    @Value("${httpclient.max-per-route}")
    private Integer maxPerRoute;

    @Value("${httpclient.connect-timeout}")
    private Integer connectTimeout;

    @Value("${httpclient.connection-request-timeout}")
    private Integer connectionRequestTimeout;

    @Value("${httpclient.socket-timeout}")
    private Integer socketTimeout;

    /**
     * bean: org.apache.http.client.config.RequestConfig
     * @return
     */
    @Bean
    public RequestConfig requestConfig() {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (connectTimeout != null) {
            requestConfigBuilder.setConnectTimeout(connectTimeout);
        }
        if (connectionRequestTimeout != null) {
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
        }
        if (socketTimeout != null) {
            requestConfigBuilder.setSocketTimeout(socketTimeout);
        }
        return requestConfigBuilder.build();
    }

    /**
     * bean: org.apache.http.client.HttpClient
     * @return
     */
    @Bean
    public HttpClient httpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        if (maxTotal != null) {
            httpClientBuilder.setMaxConnTotal(maxTotal);
        }
        if (maxPerRoute != null) {
            httpClientBuilder.setMaxConnPerRoute(maxPerRoute);
        }
        return httpClientBuilder.build();
    }

    /**
     * bean: org.apache.http.client.fluent.Executor
     * @param httpClient
     * @return
     */
    @Bean
    public Executor httpClientExecutor(HttpClient httpClient) {
        return Executor.newInstance(httpClient);
    }

}
