package com.imooc.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class MockServer {
    public static void main(String[] args) throws IOException {
        //能够指定端口号和IP,连接wireMock(默认：8080)
//    	WireMock.configureFor(8080);
        //清空所有配置
        WireMock.removeAllMappings();
        //ALT+SHIFT+M 抽取出来当做一个方法 Refactor---->Extract
        mock("/order/1","01");
        mock("/order/2","02");
    }

    public static void mock(String url,String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/"+fileName+".txt");
        String content= StringUtils.join(FileUtils.readLines(resource.getFile(),"utf-8").toArray(),"\n");
        //伪造测试桩
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url)).willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }
}
