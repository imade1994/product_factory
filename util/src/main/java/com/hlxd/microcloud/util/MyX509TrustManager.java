package com.hlxd.microcloud.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
* @Description:    信任管理器
* @Author:         tsc
* @CreateDate:     2019/3/4 10:58
* @Version:        1.0
*/
public class MyX509TrustManager implements X509TrustManager {
	
	//检查客户端证书
	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub
		
	}
	
	//检查服务端证书
	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub
		
	}
	
	//返回受信任得X509证书数组
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
