package com.example.demo.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * @author zyw
 * @date 2020/7/3 21:51
 */
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setLoadOnStartup(0);
        registration.setRunAsRole("manager");
        registration.setMultipartConfig(new MultipartConfigElement("/tmp/uploads", 200000, 200000, 0));
    }
}
