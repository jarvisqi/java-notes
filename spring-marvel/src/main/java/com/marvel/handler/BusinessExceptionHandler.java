package com.marvel.handler;

import cn.hutool.http.HttpStatus;
import com.framework.common.AjaxObject;
import com.framework.common.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


/**
 * 异常处理器
 * RestControllerAdvice 启用后, 被 @ExceptionHandler、@InitBinder、@ModelAttribute 注解的方法，
 * 作用于@RequestMapping标注的方法上。
 *
 * @author : Jarvis
 * @date : 2018/6/7
 */
@RestControllerAdvice
public class BusinessExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }


    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Jarvis");
    }

    /**
     * 全局异常捕捉处理
     *
     * @param e Exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception e) {
        AjaxObject object = new AjaxObject();
        if (e instanceof BusinessException) {
            object.put("code", ((BusinessException) e).getCode());
            object.put("message", ((BusinessException) e).getMsg());
        } else {
            logger.error("系统异常", e);
            object.put("code", HttpStatus.HTTP_INTERNAL_ERROR);
            object.put("message", "系统异常，请联系管理员");
        }
        object.data(null);
        return object;
    }
}
