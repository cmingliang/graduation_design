package com.warmnut.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.common.vo.YgngError;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 全局的异常处理类
 * @author qy
 *
 */
@ApiIgnore
@RestControllerAdvice
public class ExceptionHandlerController {
	
	private Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);
	
	 /**
     * 无权访问该资源异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public SimpleResponse credentials(AccessDeniedException e) {
       return new SimpleResponse(HttpStatus.UNAUTHORIZED.value(),"权限不足");
    }
    
    /**
     * 记录已存在,主键冲突
     * @param e
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SimpleResponse duplicateEntry(DataIntegrityViolationException e) {
    	 return new SimpleResponse(YgngError.DUPLICATE_ENTRY);
    }
    
   
	
    @ExceptionHandler({Exception.class})//可以根据不同的异常单独做处理    
    public SimpleResponse TestExceptionHandlerExceptionResolver(HttpServletRequest request,HttpServletResponse res,Exception ex){	 
    	logger.error("程序发生异常",ex);
    	SimpleResponse response;    
    	response = new SimpleResponse(YgngError.UNKNOWN_ERROR);    		        	
		return response;		
    }

}
