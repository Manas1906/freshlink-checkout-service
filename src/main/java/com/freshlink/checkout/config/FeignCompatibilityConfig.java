package com.freshlink.checkout.config;

import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Configuration;

@Configuration
@Import(FeignAutoConfiguration.class)
public class FeignCompatibilityConfig {
}
