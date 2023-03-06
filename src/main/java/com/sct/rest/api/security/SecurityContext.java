package com.sct.rest.api.security;

import com.sct.rest.api.model.dto.security.CallContext;

public class SecurityContext {
    private static final ThreadLocal<CallContext> context = new ThreadLocal<>();

    public static void set(CallContext callContext) {
        context.set(callContext);
    }

    public static CallContext get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
