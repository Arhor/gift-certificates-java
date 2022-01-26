package com.epam.esm.gift.web.listener;

import static com.epam.esm.gift.web.listener.AppInitialStateVerifier.VerificationResult.Failure;
import static com.epam.esm.gift.web.listener.AppInitialStateVerifier.VerificationResult.Success;
import static java.util.stream.Collectors.groupingBy;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.epam.esm.gift.web.error.ErrorCode;

@Component
public class AppInitialStateVerifier {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @EventListener(ContextRefreshedEvent.class)
    public void verifyAppState() {
        var verificationResults = new ArrayList<VerificationResult>();

        verificationResults.add(verifyErrorCodes());

        boolean failed = false;

        for (var verificationResult : verificationResults) {
            if (verificationResult instanceof VerificationResult.Success) {
                log.info("[SUCCESS]: {}", verificationResult.message);
            } else if (verificationResult instanceof VerificationResult.Failure) {
                log.error("[FAILURE]: {}", verificationResult.message);
                failed = true;
            }
        }

        if (failed) {
            throw new IllegalStateException("Application failed to start");
        }
    }

    private VerificationResult verifyErrorCodes() {
        var duplicates = new EnumMap<ErrorCode.Type, HashMap<Number, List<ErrorCode>>>(ErrorCode.Type.class);

        var errorCodesGroupedByType = Arrays.stream(ErrorCode.values())
            .collect(groupingBy(ErrorCode::getType));

        for (var errorCodesByTypeEntry : errorCodesGroupedByType.entrySet()) {
            var errorCodesType = errorCodesByTypeEntry.getKey();
            var errorCodesByType = errorCodesByTypeEntry.getValue();

            var errorCodesGroupedByNumericValue = errorCodesByType.stream()
                .collect(groupingBy(ErrorCode::getNumericValue));

            for (var errorCodesByNumericValueEntry : errorCodesGroupedByNumericValue.entrySet()) {
                var errorCodeNumericValue = errorCodesByNumericValueEntry.getKey();
                var errorCodesByNumericValue = errorCodesByNumericValueEntry.getValue();

                if (errorCodesByNumericValue.size() > 1) {
                    duplicates
                        .computeIfAbsent(errorCodesType, type -> new HashMap<>())
                        .computeIfAbsent(errorCodeNumericValue, number -> new ArrayList<>())
                        .addAll(errorCodesByNumericValue);
                }
            }
        }

        return duplicates.isEmpty()
            ? new Success("Error codes verified")
            : new Failure("Duplicate numeric values found for the same error-code type: " + duplicates);
    }

    abstract static class VerificationResult {

        final String message;

        protected VerificationResult(final String message) {
            this.message = message;
        }

        static final class Success extends VerificationResult {
            private Success(final String value) {
                super(value);
            }
        }

        static final class Failure extends VerificationResult {
            private Failure(final String value) {
                super(value);
            }
        }
    }
}
