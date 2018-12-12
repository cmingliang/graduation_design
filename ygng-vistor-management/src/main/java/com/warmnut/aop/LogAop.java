package com.warmnut.aop;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.warmnut.common.util.HttpKit;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.common.vo.YgngError;
import com.warmnut.log.BussinessLog;
import com.warmnut.log.LogManager;
import com.warmnut.log.LogObjectHolder;
import com.warmnut.log.LogSucceed;
import com.warmnut.log.LogTaskFactory;
import com.warmnut.bean.S_User;

/**
 * 日志记录
 *
 * @author qy
 * @date 2018年9月5日14:35:05
 */
@Aspect
@Component
public class LogAop {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "@annotation(com.warmnut.log.BussinessLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        //先执行业务
        Object result = point.proceed();
        try {
        	SimpleResponse response = (SimpleResponse)result;
        	LogSucceed flag ;
        	if(YgngError.SUCCESS.value() == response.getErrorCode()) {
        		flag = LogSucceed.SUCCESS;
        	}else {        	
        		flag = LogSucceed.FAIL;
        	}
            handle(point,flag);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }
        return result;
    }

    
  
    /**
     * @param point 
     * @param flag  是否成功标志
     * @throws Exception
     */
    private void handle(ProceedingJoinPoint point,LogSucceed flag) throws Exception {

        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        //如果当前用户未登录，不做日志
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
        S_User user;
        if(principal instanceof S_User) {
        	user = (S_User)principal;
        }else {
        	return;
        }

        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
//        Class dictClass = annotation.dict();

        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }

        //如果涉及到修改,比对变化
        String msg = "";   
        if (bussinessName.indexOf("修改") != -1 || bussinessName.indexOf("编辑") != -1) {
            Object obj1 = LogObjectHolder.me().get();
            Map<String, String> obj2 = HttpKit.getRequestParameters();
            msg = "old:"+obj1+";new:"+obj2;
        } else {
            Map<String, String> parameters = HttpKit.getRequestParameters();
            msg = "new:"+parameters.toString();
        }

        LogManager.me().executeLog(LogTaskFactory.bussinessLog(user.getId(),user.getName(), bussinessName, className, methodName, msg,flag,HttpKit.getIp()));
    }
}