package com.angiii.learnplatform.exception;

import com.angiii.learnplatform.domain.dto.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据，操作失败!");
        }
        return RespBean.error("数据库异常，操作失败!");
    }

    @ExceptionHandler(BadLoginException.class)
    public RespBean loginException(BadLoginException e) {
        log.error("用户名或者密码错误", e);
        return RespBean.error(e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public RespBean deniedException(AccessDeniedException e) {
        log.error("没有权限访问", e);
        return RespBean.error(403, "你没有权限访问");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public RespBean illegalArgumentException(IllegalArgumentException e) {
        log.error("操作异常", e);
        return RespBean.error("操作异常!");
    }

    @ExceptionHandler(Exception.class)
    public RespBean exception(Exception e) {
        log.error("发生了未知异常", e);
        return RespBean.error("系统出现错误, 请联系网站管理员!");
    }
}
