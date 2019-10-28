package com.company.listener;

import java.awt.EventQueue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.company.app.App;

public class AppServletContextListener implements ServletContextListener {
	@Override
    public void contextInitialized(ServletContextEvent arg0) { 
		EventQueue.invokeLater(new App());
    }
	
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    	
    }
}
