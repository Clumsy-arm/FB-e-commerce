package org.project.ecommerce.config; // This package name must match yours

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This handler is for your product images
        registry.addResourceHandler("/productImages/**")
                .addResourceLocations("classpath:/static/productImages/");

        // This handler is for your CSS files
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        // This handler is for your JavaScript files
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
}