package com.epam.esm.gift.web.error;

import static com.epam.esm.gift.localization.config.LocalizationConfig.ERROR_MESSAGES_BEAN;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.epam.esm.gift.localization.error.EntityDuplicateException;
import com.epam.esm.gift.localization.error.EntityNotFoundException;
import com.epam.esm.gift.localization.error.ErrorLabel;
import com.epam.esm.gift.localization.error.LocalizableException;
import com.epam.esm.gift.web.context.CurrentRequestContext;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final MessageSource messages;
    private final CurrentRequestContext currentRequestContext;

    public GlobalExceptionHandler(
        @Qualifier(ERROR_MESSAGES_BEAN) final MessageSource messages,
        final CurrentRequestContext currentRequestContext
    ) {
        this.messages = messages;
        this.currentRequestContext = currentRequestContext;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleDefault(
        final Exception exception,
        final Locale locale
    ) {
        logExceptionWithTraceId(exception, "Unhandled exception. Consider appropriate exception handler method");

        return new ApiError(
            currentRequestContext.getTraceId(),
            findTranslation(
                locale,
                ErrorLabel.ERROR_SERVER_INTERNAL
            )
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiError handleEntityNotFoundException(
        final EntityNotFoundException exception,
        final Locale locale
    ) {
        logExceptionWithTraceId(exception);

        return new ApiError(
            currentRequestContext.getTraceId(),
            findTranslationForException(locale, exception),
            ErrorCode.NOT_FOUND
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityDuplicateException.class)
    public ApiError handleEntityDuplicateException(
        final EntityDuplicateException exception,
        final Locale locale
    ) {
        logExceptionWithTraceId(exception);

        return new ApiError(
            currentRequestContext.getTraceId(),
            findTranslationForException(locale, exception),
            ErrorCode.DUPLICATE
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError handleMethodArgumentTypeMismatchException(
        final MethodArgumentTypeMismatchException exception,
        final Locale locale
    ) {
        logExceptionWithTraceId(exception);

        return new ApiError(
            currentRequestContext.getTraceId(),
            findTranslation(
                locale,
                ErrorLabel.ERROR_VALUE_TYPE_MISMATCH,
                exception.getName(),
                exception.getValue(),
                exception.getRequiredType()
            ),
            ErrorCode.METHOD_ARG_TYPE_MISMATCH
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiError handleNoHandlerFoundException(
        final NoHandlerFoundException exception,
        final Locale locale
    ) {
        logExceptionWithTraceId(exception);

        return new ApiError(
            currentRequestContext.getTraceId(),
            findTranslation(
                locale,
                ErrorLabel.ERROR_SERVER_HANDLER_NOT_FOUND,
                exception.getHttpMethod(),
                exception.getRequestURL()
            ),
            ErrorCode.HANDLER_NOT_FOUND
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleMethodArgumentNotValidException(
        final MethodArgumentNotValidException exception,
        final Locale locale
    ) {
        logExceptionWithTraceId(exception);

        var bindingResult = exception.getBindingResult();
        var errors = ListUtils.union(
            handleObjectErrors(
                bindingResult.getFieldErrors(),
                FieldError::getField,
                FieldError::getDefaultMessage
            ),
            handleObjectErrors(
                bindingResult.getGlobalErrors(),
                ObjectError::getObjectName,
                ObjectError::getDefaultMessage
            )
        );

        return new ApiError(
            currentRequestContext.getTraceId(),
            findTranslation(
                locale,
                ErrorLabel.ERROR_ENTITY_VALIDATION_FAILED
            ),
            ErrorCode.VALIDATION_FAILED,
            errors
        );
    }

    private void logExceptionWithTraceId(final Exception exception) {
        log.error("Trace-ID: {}", currentRequestContext.getTraceId(), exception);
    }

    private void logExceptionWithTraceId(final Exception exception, final String message) {
        log.error("{}, Trace-ID: {}", message, currentRequestContext.getTraceId(), exception);
    }

    private String findTranslation(final Locale locale, final ErrorLabel label, final Object... params) {
        return messages.getMessage(
            label.getCode(),
            params,
            locale
        );
    }

    private String findTranslationForException(final Locale locale, final LocalizableException exception) {
        return findTranslation(
            locale,
            exception.getLabel(),
            exception.getParams()
        );
    }

    private <T extends ObjectError> List<String> handleObjectErrors(
        final List<? extends T> errors,
        final Function<T, String> nameProvider,
        final Function<T, String> messageProvider
    ) {
        var result = new ArrayList<String>(errors.size());
        for (var error : errors) {
            var name = nameProvider.apply(error);
            var message = messageProvider.apply(error);

            result.add(name + ": " + message);
        }
        return result;
    }
}
