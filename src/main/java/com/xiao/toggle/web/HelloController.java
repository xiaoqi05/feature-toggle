package com.xiao.toggle.web;

import com.xiao.toggle.feature.FeatureToggle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloController {
  @RequestMapping("")
  @FeatureToggle(feature = "feature.hello")
  public Map hello() {
    return Collections.singletonMap("message", "hello world!");
  }
}
