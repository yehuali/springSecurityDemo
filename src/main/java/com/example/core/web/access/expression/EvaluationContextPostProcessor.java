package com.example.core.web.access.expression;

import org.springframework.expression.EvaluationContext;

public interface EvaluationContextPostProcessor<I> {
    /**
     * 允许对{@link EvaluationContext}进行后期处理。
     * 实现可以返回{@link EvaluationContext}的新实例，也可以修改传入的* {@link EvaluationContext}
     * @param context
     * @param invocation
     * @return
     */
    EvaluationContext postProcess(EvaluationContext context, I invocation);
}
