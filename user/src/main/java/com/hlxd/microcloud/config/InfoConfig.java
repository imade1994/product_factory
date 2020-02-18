package com.hlxd.microcloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@Component
@RefreshScope
public class InfoConfig {

	@Value("${info.app.name}")
	private String appName;
	@Value("${info.company.name}")
	private String comanyName;
	@Value("${info.app.version}")
	private String appVersion;
	
	public String getAppName() {
		return appName;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public String getComanyName() {
		return comanyName;
	}
}
