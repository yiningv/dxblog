package com.yiningv.dxblog.support;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpFetch {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private RequestConfig requestConfig;

    public String doGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);

        httpGet.setConfig(requestConfig);

        HttpResponse response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }
}
