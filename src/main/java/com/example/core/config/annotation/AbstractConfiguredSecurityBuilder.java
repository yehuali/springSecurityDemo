package com.example.core.config.annotation;


import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class AbstractConfiguredSecurityBuilder<O, B extends SecurityBuilder<O>> extends AbstractSecurityBuilder<O> {

    private final LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers = new LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>>();


    public <C extends SecurityConfigurer<O, B>> C apply(C configurer) throws Exception{
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

    @Override
    protected O doBuild() throws Exception {
        synchronized (configurers) {
            buildState = BuildState.INITIALIZING;

            beforeInit();
            init();

            return null;
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
