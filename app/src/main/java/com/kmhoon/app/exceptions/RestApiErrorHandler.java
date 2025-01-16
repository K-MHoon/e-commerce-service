package com.kmhoon.app.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Locale;

import static com.kmhoon.app.exceptions.ErrorCode.*;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class RestApiErrorHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleException(HttpServletRequest request, Exception e, Locale locale) {
        return Error.builder()
                .message(GENERIC_ERROR.getErrMsgKey())
                .errorCode(GENERIC_ERROR.getErrCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                          HttpMediaTypeNotSupportedException ex,
                                                          Locale locale) {
        return Error.builder()
                .message(HTTP_MEDIATYPE_NOT_SUPPORTED.getErrMsgKey())
                .errorCode(HTTP_MEDIATYPE_NOT_SUPPORTED.getErrCode())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleHttpMessageNotWritableException(HttpServletRequest request,
                                                       HttpMessageNotWritableException ex,
                                                       Locale locale) {
        return Error.builder()
                .message(HTTP_MESSAGE_NOT_WRITABLE.getErrMsgKey())
                .errorCode(HTTP_MESSAGE_NOT_WRITABLE.getErrCode())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleHttpMediaTypeNotAcceptableException(HttpServletRequest request,
                                                           HttpMediaTypeNotAcceptableException ex,
                                                           Locale locale) {

        return Error.builder()
                .message(HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrMsgKey())
                .errorCode(HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrCode())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleHttpMessageNotReadableException(HttpServletRequest request,
                                                       HttpMessageNotReadableException ex,
                                                       Locale locale) {
        return Error.builder()
                .message(HTTP_MESSAGE_NOT_READABLE.getErrMsgKey())
                .errorCode(HTTP_MESSAGE_NOT_READABLE.getErrCode())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleJsonParseException(HttpServletRequest request,
                                          JsonParseException ex,
                                          Locale locale) {
        return Error.builder()
                .message(JSON_PARSE_ERROR.getErrMsgKey())
                .errorCode(JSON_PARSE_ERROR.getErrCode())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Error handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                              HttpRequestMethodNotSupportedException ex,
                                                              Locale locale) {
        return Error.builder()
                .message(HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrMsgKey())
                .errorCode(HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrCode())
                .status(HttpStatus.NOT_IMPLEMENTED.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleHIllegalArgumentException(HttpServletRequest request,
                                                 IllegalArgumentException ex,
                                                 Locale locale) {
        return Error.builder()
                .message(String.format("%s %s", ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getErrMsgKey(), ex.getMessage()))
                .errorCode(ILLEGAL_ARGUMENT_EXCEPTION.getErrCode())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleResourceNotFoundException(HttpServletRequest request,
                                                 ResourceNotFoundException ex, Locale locale) {
        return Error.builder()
                .message(String.format("%s %s", ErrorCode.RESOURCE_NOT_FOUND.getErrMsgKey(), ex.getMessage()))
                .errorCode(ex.getErrorCode())
                .status(HttpStatus.NOT_FOUND.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleCustomerNotFoundException(HttpServletRequest request,
                                                 CustomerNotFoundException ex, Locale locale) {
        return Error.builder()
                .message(String.format("%s %s", ErrorCode.CUSTOMER_NOT_FOUND.getErrMsgKey(), ex.getMessage()))
                .errorCode(ex.getErrorCode())
                .status(HttpStatus.NOT_FOUND.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleItemNotFoundException(HttpServletRequest request,
                                             ItemNotFoundException ex,
                                             Locale locale) {

        return Error.builder()
                .message(String.format("%s %s", ErrorCode.ITEM_NOT_FOUND.getErrMsgKey(), ex.getMessage()))
                .errorCode(ex.getErrorCode())
                .status(HttpStatus.NOT_FOUND.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }

    @ExceptionHandler(GenericAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Error handleGenericAlreadyExistsException(HttpServletRequest request,
                                                     GenericAlreadyExistsException ex, Locale locale) {
        return Error.builder()
                .message(String.format("%s %s", ErrorCode.GENERIC_ALREADY_EXISTS.getErrMsgKey(), ex.getMessage()))
                .errorCode(ex.getErrorCode())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .timeStamp(Instant.now())
                .build();
    }
}
