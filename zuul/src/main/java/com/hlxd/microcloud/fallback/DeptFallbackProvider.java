package com.hlxd.microcloud.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;


@SuppressWarnings("deprecation")
@Component
public class DeptFallbackProvider implements ZuulFallbackProvider {

	@Override
	public String getRoute() {
		return "*";	// 设置好处理的失败路由
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse() {

			@Override
			public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("The service is unopened or disconnected".getBytes());
			}

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders() ;
				headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
				return headers;
			}

			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.BAD_REQUEST;
			}

			@Override
			public int getRawStatusCode() throws IOException {
				return HttpStatus.BAD_REQUEST.value();
			}

			@Override
			public String getStatusText() throws IOException {
				return HttpStatus.BAD_REQUEST.getReasonPhrase();
			}
			@Override
			public void close() {
				
			}};
	}

}
