package com.steveq.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer{

    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext dispatcherServlet =
                new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(AppConfig.class);

        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/geonoteapp/*");
    }

}
