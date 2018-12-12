package com.warmnut.listener;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.warmnut.repository.S_DictRepository;
import com.warmnut.support.DictMap;


/**
 * 容器启动事件
 * @author qinyao
 *
 */
@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent>{

    protected Logger log = LoggerFactory.getLogger(StartApplicationListener.class);
    private volatile AtomicBoolean isInit=new AtomicBoolean(false);
    
    @Autowired
	private  S_DictRepository dicDao;
    
  
    @Override  
    //监听此事件做so库的加载工作
    public void onApplicationEvent(ContextRefreshedEvent event) {  
    	  //防止重复触发
    	 if(!isInit.compareAndSet(false,true)) {
             return;
         }
    	System.out.println("================================================ application start");    	
    	DictMap.queryDic(dicDao);
    	
//		if (event.getApplicationContext().getParent() == null) {// root application context 没有parent，他就是老大.
//			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
//		}
        
    }  
}
