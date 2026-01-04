package org.mm97.pagilab.util;

import tools.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getDefaultObjectMapper() {
        return objectMapper;
    }
}
