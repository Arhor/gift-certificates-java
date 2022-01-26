package com.epam.esm.gift.web.context;

import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = SCOPE_REQUEST, proxyMode = INTERFACES)
public class CurrentRequestContextImpl implements CurrentRequestContext {

    private final UUID traceId = UUID.randomUUID();

    @Override
    public UUID getTraceId() {
        return traceId;
    }
}
