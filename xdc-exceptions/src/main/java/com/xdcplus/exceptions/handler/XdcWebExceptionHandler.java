package com.xdcplus.exceptions.handler;

import com.xdcplus.exceptions.Response;
import com.xdcplus.exceptions.XdcWebException;
import com.xdcplus.exceptions.enums.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.crypto.BadPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.LinkedList;
import java.util.List;

/**
 *  异常控制处理器
 * @author Rong.Jia
 * @date 2019/4/3
 */
@Slf4j
@SuppressWarnings("ALL")
@RestControllerAdvice
public class XdcWebExceptionHandler {

    /**
     *  捕获自定义异常，并返回异常数据
     * @author Rong.Jia
     * @date 2019/4/3 8:46
     */
    @ExceptionHandler(value = XdcWebException.class)
    public Response xdcWebExceptionHandler(XdcWebException e){

        log.error("xdcWebExceptionHandler  {}", e.getMessage());

        return Response.error(e.getCode(), e.getMessage());

    }

    /**
     * 捕获缺失参数异常，并返回异常数据
     *
     * @param e e 异常
     * @date 2021/06/22 11:46
     * @author Rong.Jia
     * @return {@link Response}
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Response missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {

        log.error("missingServletRequestParameterException {}", e.getMessage());

        return Response.error(ExceptionEnum.LACK_OF_PARAMETER);

    }


    /**
     *  捕获不合法的参数异常，并返回异常数据
     * @author Rong.Jia
     * @date 2019/4/3 8:46
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Response illegalArgumentExceptionHandler(IllegalArgumentException e){

        log.error("illegalArgumentExceptionHandler  {}", e.getMessage());

        return Response.error(ExceptionEnum.PARAMETER_ERROR.getCode(), e.getMessage());
    }

    /**
     *  捕捉404异常
     * @param e 404 异常
     * @date 2019/04/17 09:46:22
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Response noHandlerFoundHandle(NoHandlerFoundException e) {

        log.error("noHandlerFoundHandle {}", e.getMessage());

        return new Response(ExceptionEnum.NOT_FOUND);

    }

    /**
     *  字段验证异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response handle(MethodArgumentNotValidException e){
        List<String> errorList = new LinkedList<>();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (ObjectError error : errors ) {

            log.error ("MethodArgumentNotValidException : {} - {}",error.getObjectName(),error.getDefaultMessage());
            errorList.add(error.getDefaultMessage());

        }

        log.error("MethodArgumentNotValidExceptionHandle :{}",e.getMessage());

        return Response.error(ExceptionEnum.PARAMETER_ERROR.getCode(),errorList.get(0));

    }

    /**
     *  字段验证异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Response handle(BindException e){
        List<String> errorList = new LinkedList<>();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (ObjectError error : errors ) {

            log.error ("BindException : {} - {}",error.getObjectName(),error.getDefaultMessage());
            errorList.add(error.getDefaultMessage());

        }

        log.error("BindException :{}",e.getMessage());

        return Response.error(ExceptionEnum.PARAMETER_ERROR.getCode(),errorList.get(0));

    }

    /**
     *  参数类型不正确异常
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2020/03/25 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Response methodArgumentTypeMismatchExceptionException(Exception e) {

        log.error("methodArgumentTypeMismatchExceptionException : {}",e.getMessage());

        return Response.error(ExceptionEnum.THE_PARAMETER_TYPE_IS_INCORRECT);

    }

    /**
     *  不支持当前请求方法
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        log.error("HttpRequestMethodNotSupportedException : {}",e.getMessage());

        return Response.error(ExceptionEnum.REQUEST_MODE_ERROR);

    }

    /**
     *  不支持当前媒体类型
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {

        log.error("HttpMediaTypeNotSupportedException : {}",e.getMessage());

        return Response.error(ExceptionEnum.MEDIA_TYPE_NOT_SUPPORTED);

    }

    /**
     *  默认异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response defaultErrorHandler( Exception e) {

        if (e.getCause() instanceof XdcWebException) {
            xdcWebExceptionHandler((XdcWebException)e);
        }

        if (e.getCause() instanceof IllegalArgumentException) {
            illegalArgumentExceptionHandler((IllegalArgumentException)e.getCause());
        }

        if (e.getCause() instanceof MethodArgumentTypeMismatchException) {
            methodArgumentTypeMismatchExceptionException((MethodArgumentTypeMismatchException)e.getCause());
        }

        if (e.getCause() instanceof MissingServletRequestParameterException) {
            missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException)e.getCause());
        }

        log.error("defaultErrorHandler : {}", e.getMessage());

        return Response.error(ExceptionEnum.SYSTEM_ERROR);
    }

    /**
     *  默认运行异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Response runtimeErrorHandler(RuntimeException e) {

        if (e.getCause() instanceof XdcWebException) {
            xdcWebExceptionHandler((XdcWebException)e);
        }

        if(e.getCause() instanceof HttpMessageNotReadableException){
            httpMesssageHandler((HttpMessageNotReadableException)e.getCause());
        }

        if (e.getCause() instanceof MultipartException) {
            multipartExceptionHandler((MultipartException)e.getCause());
        }

        if (e.getCause() instanceof IllegalArgumentException) {
            illegalArgumentExceptionHandler((IllegalArgumentException)e.getCause());
        }

        if (e.getCause() instanceof MethodArgumentTypeMismatchException) {
            methodArgumentTypeMismatchExceptionException((MethodArgumentTypeMismatchException)e.getCause());
        }

        log.error("runtimeErrorHandler : {}", e.getMessage());

        return Response.error(ExceptionEnum.SYSTEM_ERROR);
    }

    /**
     *  文件上传异常处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public Response maxUploadHandler(MaxUploadSizeExceededException e) {

        log.error("MaxUploadSizeExceededException : {}", e.getMessage());

        return Response.error(ExceptionEnum.FILE_LIMIT_EXCEEDED);
    }

    /**
     *  使用反射或者代理造成的异常需要根据异常类型单独处理
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(value = UndeclaredThrowableException.class)
    @ResponseBody
    public Response undeclaredThrowableException(HttpServletRequest req, UndeclaredThrowableException e) {

        //密文解密失败异常
        if (e.getCause() instanceof BadPaddingException) {

            log.error("BadPaddingException : {}",e.getMessage());
            return Response.error(ExceptionEnum.SYSTEM_ERROR);
        }

        log.error("UndeclaredThrowableException : {}",e.getMessage());
        return Response.error(ExceptionEnum.SYSTEM_ERROR);
    }

    /**
     *  http 请求参数格式错误
     * @param e  异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public Response  httpMesssageHandler(HttpMessageNotReadableException e){

        log.error("HttpMessageNotReadableException : {}",e.getMessage());

        return Response.error(ExceptionEnum.REQUEST_PARAMETER_FORMAT_IS_INCORRECT);
    }

    /**
     *  超出最大限制捕获异常
     * @param e 异常信息
     * @author Rong.Jia
     * @date 2018/8/4 20:02
     * @return Response 返回异常信息
     */
    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public Response  multipartExceptionHandler(MultipartException e){

        log.error("multipartExceptionHandler : {}",e.getMessage());

        return Response.error(ExceptionEnum.FILE_LIMIT_EXCEEDED);

    }





}
