package com.xiao.toggle.intereceptor;

import com.xiao.toggle.feature.FeatureRepository;
import com.xiao.toggle.feature.FeatureToggle;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeatureInterceptor implements HandlerInterceptor {
    private final FeatureRepository featureRepository;

    public FeatureInterceptor(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object handler) {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            FeatureToggle methodAnnotation = handlerMethod.getMethodAnnotation(FeatureToggle.class);
            if (methodAnnotation == null) {
                return true;
            }

            if (featureRepository.isActive(methodAnnotation.feature()) == null) {
                return true;
            }

            if (methodAnnotation.beActive() == featureRepository.isActive(methodAnnotation.feature())) {
                return true;
            }

            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {

    }
}
