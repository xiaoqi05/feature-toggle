package com.xiao.toggle.feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FeatureRepository {
    private static final String FEATURE_PREFIX = "feature.";
    private final Environment env;

    @Autowired
    public FeatureRepository(Environment env) {
        this.env = env;
    }

    public Set<String> featureKeys() {
        Map<String, Object> map = new HashMap<>();
        for (PropertySource<?> propertySource : ((AbstractEnvironment) env).getPropertySources()) {
            if (propertySource instanceof CompositePropertySource) {
                Optional result = ((LinkedHashSet) ((CompositePropertySource) propertySource).getPropertySources())
                        .stream().findFirst();
                result.ifPresent(s -> ((CompositePropertySource) s).getPropertySources()
                        .forEach(source -> map.putAll(((MapPropertySource) source).getSource())
                        ));
            }

            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
        return map.keySet().stream()
                .filter(k -> k.startsWith(FEATURE_PREFIX))
                .collect(Collectors.toSet());
    }

    public Boolean isActive(String key) {
        return allFeatures().get(key);
    }

    public Map<String, Boolean> allFeatures() {
        return featureKeys().stream().collect(Collectors.toMap(k -> k, k -> Boolean.parseBoolean(env.getProperty(k))));
    }
}
