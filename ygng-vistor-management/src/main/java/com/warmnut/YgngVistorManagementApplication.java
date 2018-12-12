package com.warmnut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.warmnut.common.util.KindoObjectMapper;

/**
 * spring boot 启动的入口类
 * @author qy
 *
 */
@SpringBootApplication
@EnableCaching//开启缓存支持
public class YgngVistorManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(YgngVistorManagementApplication.class, args);
	}
	
	/**
	 * 自定义ObjectMapper,处理空值
	 * 
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		KindoObjectMapper mapper = new KindoObjectMapper();
		converter.setObjectMapper(mapper);
		return converter;		
	}
	
	
	
	

}
