package com.epam.esm.gift.web.context;

import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "request", proxyMode = INTERFACES)
public class CurrentRequestContextImpl implements CurrentRequestContext {

    private final UUID traceId = UUID.randomUUID();

    @Override
    public UUID getTraceId() {
        return traceId;
    }
}
