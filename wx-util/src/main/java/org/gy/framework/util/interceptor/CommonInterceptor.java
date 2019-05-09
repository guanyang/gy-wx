package org.gy.framework.util.interceptor;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.gy.framework.util.interceptor.support.CodeType;
import org.gy.framework.util.interceptor.support.CommonException;
import org.gy.framework.util.interceptor.support.MessageUtil;
import org.gy.framework.util.interceptor.support.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：异常处理的切面
 * 
 */
public class CommonInterceptor {

    private static Logger    logger    = LoggerFactory.getLogger(CommonInterceptor.class);

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            // 参数校验
            Object[] args = proceedingJoinPoint.getArgs();
            if (args != null && args.length > 0) {
                for (Object obj : args) {
                    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
                    if (constraintViolations != null && constraintViolations.size() > 0) {
                        // 返回任意一个错误信息
                        ConstraintViolation<Object> violation = constraintViolations.iterator().next();
                        ResponseDTO tmp = new ResponseDTO();
                        MessageUtil.wrapResponse(tmp, CodeType.E999001, violation.getMessage());
                        return tmp;
                    }
                }
            }
            // 具体业务逻辑处理
            Object result = proceedingJoinPoint.proceed();
            return (ResponseDTO) result;
        } catch (Exception e) {
            // 异常处理
            ResponseDTO tmp = new ResponseDTO();
            if (e instanceof CommonException) {
                MessageUtil.wrapResponse(tmp, (CommonException) e);
            } else {
                MessageUtil.wrapResponse(tmp, CodeType.E999999, e.getMessage());
            }
            logger.error("CommonInterceptor异常：" + tmp.getMessage(), e);
            return tmp;
        }
    }

}
