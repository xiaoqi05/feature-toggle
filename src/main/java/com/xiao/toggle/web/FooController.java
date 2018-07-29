package com.xiao.toggle.web;

import com.xiao.toggle.feature.FeatureToggle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping ("/foo")
@FeatureToggle (feature = "feature.foo", beActive = false)
public class FooController {

    @GetMapping
    public Map hello() {
        return Collections.singletonMap("message", "hello foo!");
    }
}
