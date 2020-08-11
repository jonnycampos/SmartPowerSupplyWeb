package com.sps.services;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController implements ApplicationContextAware {
    
    private ApplicationContext context;
    
    @RequestMapping(value="/shutdown", method=RequestMethod.GET)
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }
 
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
        
    }
}