package com.example.core.config.annotation;


import org.springframework.util.Assert;

import java.util.*;

public abstract class AbstractConfiguredSecurityBuilder<O, B extends SecurityBuilder<O>> extends AbstractSecurityBuilder<O> {

    private final LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers = new LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>>();

    private final Map<Class<? extends Object>, Object> sharedObjects = new HashMap<Class<? extends Object>, Object>();

    private ObjectPostProcessor<Object> objectPostProcessor;

    private final boolean allowConfigurersOfSameType;

    protected AbstractConfiguredSecurityBuilder(
            ObjectPostProcessor<Object> objectPostProcessor) {
        this(objectPostProcessor, false);
    }

    /**
     * 使用ObjectPostProcessor创建一个实例
     * @param objectPostProcessor
     * @param allowConfigurersOfSameType  如果为true，将不要覆盖其他SecurityConfigurer执行apply
     */
    protected AbstractConfiguredSecurityBuilder(
            ObjectPostProcessor<Object> objectPostProcessor,
            boolean allowConfigurersOfSameType) {
        Assert.notNull(objectPostProcessor, "objectPostProcessor cannot be null");
        this.objectPostProcessor = objectPostProcessor;
        this.allowConfigurersOfSameType = allowConfigurersOfSameType;
    }

    public <C extends SecurityConfigurer<O, B>> C getConfigurer(Class<C> clazz) {
        List<SecurityConfigurer<O, B>> configs = this.configurers.get(clazz);
        if (configs == null) {
            return null;
        }
        if (configs.size() != 1) {
            throw new IllegalStateException("Only one configurer expected for type "
                    + clazz + ", but got " + configs);
        }
        return (C) configs.get(0);
    }

    public <C extends SecurityConfigurerAdapter<O, B>> C apply(C configurer) throws Exception{
        configurer.addObjectPostProcessor(objectPostProcessor);
        configurer.setBuilder((B) this);
        add(configurer);
        return configurer;
    }

    public <C extends SecurityConfigurer<O, B>> C apply(C configurer) throws Exception {
        add(configurer);
        return configurer;
    }

    private <C extends SecurityConfigurer<O, B>> void add(C configurer) throws Exception {
        Assert.notNull(configurer, "configurer cannot be null");

        Class<? extends SecurityConfigurer<O, B>> clazz = (Class<? extends SecurityConfigurer<O, B>>) configurer
                .getClass();
        synchronized (configurers) {
            List<SecurityConfigurer<O, B>> configs = this.configurers.get(clazz);
            if (configs == null) {
                configs = new ArrayList<SecurityConfigurer<O, B>>(1);
            }
            configs.add(configurer);
            this.configurers.put(clazz, configs);
        }
    }

    private BuildState buildState = BuildState.UNBUILT;

    public <C> C getSharedObject(Class<C> sharedType) {
        return (C) this.sharedObjects.get(sharedType);
    }

    public <C> void setSharedObject(Class<C> sharedType, C object) {
        this.sharedObjects.put(sharedType, object);
    }

    /**
     * 定义构建的模版
     * @return
     * @throws Exception
     */
    @Override
    protected O doBuild() throws Exception {
        synchronized (configurers) {
            buildState = BuildState.INITIALIZING;

            beforeInit();
            init();

            buildState = BuildState.CONFIGURING;
            configure();

            buildState = BuildState.BUILDING;
            O result = performBuild();


            return result;
        }


    }

    protected void beforeInit() throws Exception {
    }

    private void init() throws Exception {
        Collection<SecurityConfigurer<O, B>> configurers = getConfigurers();

        for (SecurityConfigurer<O, B> configurer : configurers) {
            configurer.init((B) this);
        }

//        for (SecurityConfigurer<O, B> configurer : configurersAddedInInitializing) {
//            configurer.init((B) this);
//        }
    }

    private void configure() throws Exception {
        Collection<SecurityConfigurer<O, B>> configurers = getConfigurers();

        for (SecurityConfigurer<O, B> configurer : configurers) {
            configurer.configure((B) this);
        }
    }

    protected abstract O performBuild() throws Exception;

    private Collection<SecurityConfigurer<O, B>> getConfigurers() {
        List<SecurityConfigurer<O, B>> result = new ArrayList<SecurityConfigurer<O, B>>();
        for (List<SecurityConfigurer<O, B>> configs : this.configurers.values()) {
            result.addAll(configs);
        }
        return result;
    }

    /**
     * 构造状态枚举
     */
    private static enum BuildState{
        UNBUILT(0),

        INITIALIZING(1),

        CONFIGURING(2),

        BUILDING(3),

        BUILT(4);

        private final int order;

        BuildState(int order){
            this.order = order;
        }

        public boolean isInitializing() {
            return INITIALIZING.order == order;
        }

        public boolean isConfigured() {
            return order >= CONFIGURING.order;
        }

    }
}
