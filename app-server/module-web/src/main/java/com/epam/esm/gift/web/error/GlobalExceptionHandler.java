package com.epam.esm.gift.web.error;

import static com.epam.esm.gift.localization.config.MessageSourceConfig.ERROR_MESSAGES_BEAN;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.epam.esm.gift.localization.error.EntityDuplicateException;
import com.epam.esm.gift.localization.error.EntityNotFoundException;
import com.epam.esm.gift.localization.error.ErrorLabel;
import com.epam.esm.gift.localization.error.LocalizableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messages;

    public GlobalExceptionHandler(@Qualifier(ERROR_MESSAGES_BEAN) final MessageSource messages) {
        this.messages = messages;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleDefault(final Exception ex, final Locale locale) {
        return new ApiError(
            messages.getMessage(
                ErrorLabel.ERROR_SERVER_INTERNAL.getCode(),
                null,
                locale
            )
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiError handleEntityNotFoundException(final EntityNotFoundException ex, final Locale locale) {
        return new ApiError(
            findTranslationForException(ex, locale),
            ErrorCode.NOT_FOUND
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityDuplicateException.class)
    public ApiError handleEntityDuplicateException(final EntityDuplicateException ex, final Locale locale) {
        return new ApiError(
            findTranslationForException(ex, locale),
            ErrorCode.DUPLICATE
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex, final Locale locale) {
        return new ApiError(
            messages.getMessage(
                ErrorLabel.ERROR_VALUE_TYPE_MISMATCH.getCode(),
                new Object[]{ex.getName(), ex.getValue(), ex.getRequiredType()},
                locale
            ),
            ErrorCode.METHOD_ARG_TYPE_MISMATCH
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiError handleNoHandlerFoundException(final NoHandlerFoundException ex, final Locale locale) {
        return new ApiError(
            messages.getMessage(
                ErrorLabel.ERROR_SERVER_HANDLER_NOT_FOUND.getCode(),
                null,
                locale
            ),
            ErrorCode.HANDLER_NOT_FOUND
        );
    }

    private String findTranslationForException(final LocalizableException exception, final Locale locale) {
        return messages.getMessage(
            exception.getLabel().getCode(),
            exception.getParams(),
            locale
        );
    }
}