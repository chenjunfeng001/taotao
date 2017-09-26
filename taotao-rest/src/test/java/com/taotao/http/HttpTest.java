package com.taotao.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpTest {

    @Test
    public void toget() throws Exception {
        // 获取http
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 获取get
        HttpGet get = new HttpGet("http://www.baidu.com");
        // 获取相应
        CloseableHttpResponse response = httpClient.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        // 获取响应回来的信息实体
        HttpEntity entity = response.getEntity();
        // 运用api解析
        String string = EntityUtils.toString(entity);
        System.out.println(string);
        response.close();
        httpClient.close();

    }
}
