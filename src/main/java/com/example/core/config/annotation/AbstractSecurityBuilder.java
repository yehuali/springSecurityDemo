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

    /**
     * 获取已构建的对象。如果它尚未构建，则会出现异常
     * @return
     */
    public final O getObject() {
        if (!this.building.get()) {
            throw new IllegalStateException("This object has not been built");
        }
        return this.object;
    }

    protected abstract O doBuild() throws Exception;
}
