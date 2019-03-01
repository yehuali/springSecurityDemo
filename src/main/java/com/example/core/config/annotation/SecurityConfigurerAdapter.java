package com.example.core.config.annotation;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SecurityConfigurerAdapter<O, B extends SecurityBuilder<O>>
        implements SecurityConfigurer<O, B>{

    private B securityBuilder;

    private CompositeObjectPostProcessor objectPostProcessor = new CompositeObjectPostProcessor();

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

    /**
     * 执行对象的后处理,默认委托  { @link ObjectPostProcessor }
     * @param object
     * @param <T>
     * @return 可能修改的要使用的对象
     */
    protected <T> T postProcess(T object) {
        return (T) this.objectPostProcessor.postProcess(object);
    }

    public void addObjectPostProcessor(ObjectPostProcessor<?> objectPostProcessor) {
        this.objectPostProcessor.addObjectPostProcessor(objectPostProcessor);
    }

    private static final class CompositeObjectPostProcessor implements
            ObjectPostProcessor<Object> {

        private List<ObjectPostProcessor<? extends Object>> postProcessors = new ArrayList<ObjectPostProcessor<?>>();

        public Object postProcess(Object object) {
            for (ObjectPostProcessor opp : postProcessors) {
                Class<?> oppClass = opp.getClass();
                Class<?> oppType = GenericTypeResolver.resolveTypeArgument(oppClass,
                        ObjectPostProcessor.class);
                if (oppType == null || oppType.isAssignableFrom(object.getClass())) {
                    object = opp.postProcess(object);
                }
            }
            return object;
        }

        private boolean addObjectPostProcessor(
                ObjectPostProcessor<? extends Object> objectPostProcessor) {
            boolean result = this.postProcessors.add(objectPostProcessor);
            Collections.sort(postProcessors, AnnotationAwareOrderComparator.INSTANCE);
            return result;
        }



    }
}
