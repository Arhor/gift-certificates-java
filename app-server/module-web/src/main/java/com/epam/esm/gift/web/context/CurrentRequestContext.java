package com.epam.esm.gift.web.context;

import java.util.UUID;

public interface CurrentRequestContext {

    UUID getTraceId();
}
