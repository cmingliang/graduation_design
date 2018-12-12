package com.warmnut.sms;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.warmnut.security_core.validate.code.sms.SmsCodeSender;

@Component
public class YgngSmsCodeSender implements SmsCodeSender{
	private static final String url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	private static final String account = "cf_z123456";
	
	private static final String password = "4810e39fd4a43d6a598e75d8d8997c97";

	@Override
	public void send(String mobile, String code) {
		
		 	RestTemplate client = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        HttpMethod method = HttpMethod.POST;
	        // 以表单的方式提交
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        params.add("account", account);
	        params.add("password", password);
	        params.add("mobile", mobile);
	        params.add("content",  "您的验证码是：" + code + "。请不要把验证码泄露给其他人。");

	        //将请求头部和参数合成一个请求
	        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
	        //执行HTTP请求，将返回的结构使用ResultVO类格式化
	        
	        ResponseEntity response = client.exchange(url, method, requestEntity,String.class);

	        System.out.println(response.toString());
	 
	}
	
//	public static void main(String[] args) {
//		YgngSmsCodeSender sender = new YgngSmsCodeSender();
//		sender.send("18513342211", "3214");
//	}

}
