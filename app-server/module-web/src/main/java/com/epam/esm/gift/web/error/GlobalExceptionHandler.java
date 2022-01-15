package com.epam.esm.gift.web.error;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.epam.esm.gift.error.EntityDuplicateException;
import com.epam.esm.gift.error.EntityNotFoundException;
import com.epam.esm.gift.error.PropertyConditionException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ERROR_UNCATEGORIZED = "error.uncategorized";
    private static final String ERROR_REST_RESOURCE_NOT_FOUND = "error.rest.resource.not.found";
    private static final String ERROR_REST_RESOURCE_DUPLICATE = "error.rest.resource.duplicate";

    private final MessageSource messages;

    public GlobalExceptionHandler(final MessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleDefault(final Exception ex, final Locale locale) {
        log.error(ex.getMessage(), ex);
        return new ApiError(
            getMessage(
                ERROR_UNCATEGORIZED,
                locale
            )
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityNotFoundException(final EntityNotFoundException ex, final Locale locale) {
        log.error("Entity by certain condition is not found", ex);
        return new ApiError(
            messages.getMessage(
                ERROR_REST_RESOURCE_NOT_FOUND,
                convertToArgs(ex),
                locale
            ),
            ErrorCode.NOT_FOUND
        );
    }

    @ExceptionHandler(EntityDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEntityDuplicateException(final EntityDuplicateException ex, final Locale locale) {
        log.error("Entity by certain condition already exists", ex);
        return new ApiError(
            messages.getMessage(
                ERROR_REST_RESOURCE_DUPLICATE,
                convertToArgs(ex),
                locale
            ),
            ErrorCode.DUPLICATE
        );
    }

    private String getMessage(final String code, final Locale locale) {
        return messages.getMessage(code, null, locale);
    }

    private Object[] convertToArgs(final PropertyConditionException ex) {
        return new Object[]{ex.getName(), ex.getCondition()};
    }
}