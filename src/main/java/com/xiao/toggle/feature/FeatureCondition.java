package com.xiao.toggle.feature;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeatureCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if (annotatedTypeMetadata.isAnnotated(FeatureToggle.class.getCanonicalName())) {
            Map<String, Object> annotationAttributes = annotatedTypeMetadata
                    .getAnnotationAttributes(FeatureToggle.class.getCanonicalName());
            String feature = (String) annotationAttributes.get("feature");
            boolean expectedToBeActive = Boolean
                    .parseBoolean(String.valueOf(annotationAttributes.get("beActive")));
            boolean isActive = Boolean.parseBoolean(conditionContext.getEnvironment().getProperty(feature));
            return expectedToBeActive == isActive;
        }
        return true;
    }
}
