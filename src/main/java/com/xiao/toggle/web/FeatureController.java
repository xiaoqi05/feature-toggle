package com.xiao.toggle.web;

import com.xiao.toggle.feature.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/features")
public class FeatureController {

    @Autowired
    FeatureRepository featureRepository;

    @RequestMapping ("")
    public List<Map> features() {
            return featureRepository.allFeatures()
                .entrySet()
                .stream()
                .map(entry -> new HashMap<String, Object>() {
                    {
                        put("key", entry.getKey());
                        put("active", entry.getValue());
                    }
                })
                .collect(Collectors.toList());
    }
}