package cn.haiwai.newsnotification.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import cn.haiwai.newsnotification.manage.response.Response;


/**
 * 全局异常拦截处理
 * @author lh
 * @date 2017年11月6日
 * @version 1.0
 */
@ControllerAdvice
@ResponseBody
@Controller
public class ExceptionAdvice {
	
	private static final Logger logger=LoggerFactory.getLogger(ExceptionAdvice.class);
	
	  /** 
     * 400 - Bad Request 
     */  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler(HttpMessageNotReadableException.class)  
    public Response Response(HttpMessageNotReadableException e) {  
        logger.error("参数解析失败400", e);  
        return Response.failure("参数解析失败400 could_not_read_json");  
    }  
  
    /** 
     * 405 - Method Not Allowed 
     */  
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)  
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {  
        logger.error("不支持当前请求方法405", e);  
        return Response.failure("不支持当前请求方法405 request_method_not_supported");  
    }  
  
    /** 
     * 415 - Unsupported Media Type 
     */  
   @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)  
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)  
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {  
        logger.error("不支持当前媒体类型415", e);  
        return Response.failure("不支持当前媒体类型415 content_type_not_supported");  
    }  
  
    /** 
     * 500 - Internal Server Error 
     */  
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
    @ExceptionHandler(Exception.class)  
    public Response handleException(Exception e) {  
        logger.error("服务运行异常500", e);  
        String message=e.getMessage();
        message="出现未知异常！"+message;
        return Response.failure(message);  
    }  

}

