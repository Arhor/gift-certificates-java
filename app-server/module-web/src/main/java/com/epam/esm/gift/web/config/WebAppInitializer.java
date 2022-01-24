package com.epam.esm.gift.web.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SuppressWarnings("NullableProblems")
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    public FrameworkServlet createDispatcherServlet(final WebApplicationContext servletAppContext) {
        var frameworkServlet = super.createDispatcherServlet(servletAppContext);

        if (frameworkServlet instanceof DispatcherServlet) {
            ((DispatcherServlet) frameworkServlet).setThrowExceptionIfNoHandlerFound(true);
        }
        return frameworkServlet;
    }
}