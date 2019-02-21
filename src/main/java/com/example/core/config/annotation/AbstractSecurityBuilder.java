package com.example.core.config.annotation;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 确保对象只创建一次
 * @param <O>
 */
public abstract class AbstractSecurityBuilder<O> implements SecurityBuilder<O> {
    private AtomicBoolean building = new AtomicBoolean();
    private O object;

    public final O build() throws Exception {
        if (this.building.compareAndSet(false, true)) {
            this.object = doBuild();
            return this.object;
        }
        throw new Exception("This object has already been built");
    }

    protected abstract O doBuild() throws Exception;
}
