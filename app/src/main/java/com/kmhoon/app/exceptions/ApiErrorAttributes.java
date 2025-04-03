package com.kmhoon.app.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
@Getter
@Setter
public class ApiErrorAttributes extends DefaultErrorAttributes {

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private String message = ErrorCode.GENERIC_ERROR.getErrMsgKey();


    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        var attributes = super.getErrorAttributes(request, options);
        attributes.put("status", status);
        attributes.put("message", message);
        attributes.put("code", ErrorCode.GENERIC_ERROR.getErrCode());
        return attributes;
    }
}
