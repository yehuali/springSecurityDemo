package com.example.core.config.annotation;

public abstract class SecurityConfigurerAdapter<O, B extends SecurityBuilder<O>>
        implements SecurityConfigurer<O, B>{

    private B securityBuilder;

    public void init(B builder) throws Exception {
    }

    public void configure(B builder) throws Exception {
    }

    public B and() {
        return getBuilder();
    }

    public void setBuilder(B builder) {
        this.securityBuilder = builder;
    }

    protected final B getBuilder() {
        if (securityBuilder == null) {
            throw new IllegalStateException("securityBuilder cannot be null");
        }
        return securityBuilder;
    }
}
