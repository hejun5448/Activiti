package org.activiti.app.ui;

import org.activiti.app.conf.ApplicationConfiguration;
import org.activiti.app.servlet.ApiDispatcherServletConfiguration;
import org.activiti.app.servlet.AppDispatcherServletConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@Import({ApplicationConfiguration.class})
public class ActivitiUIApplication extends SpringBootServletInitializer{

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ActivitiUIApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ActivitiUIApplication.class);
	}
	
	@Bean	
	public ServletRegistrationBean apiDispatcher() {
		DispatcherServlet api = new DispatcherServlet();
		api.setContextClass(AnnotationConfigWebApplicationContext.class);
		api.setContextConfigLocation(ApiDispatcherServletConfiguration.class.getName());
		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.setServlet(api);
		registrationBean.addUrlMappings("/api/*");
		registrationBean.setLoadOnStartup(1);
		registrationBean.setAsyncSupported(true);
		registrationBean.setName("api");
		
		return registrationBean;
	}
	
	@Bean	
	public ServletRegistrationBean appDispatcher() {
		DispatcherServlet app = new DispatcherServlet();
		app.setContextClass(AnnotationConfigWebApplicationContext.class);
		app.setContextConfigLocation(AppDispatcherServletConfiguration.class.getName());
		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.setServlet(app);
		registrationBean.addUrlMappings("/app/*");
		registrationBean.setLoadOnStartup(1);
		registrationBean.setAsyncSupported(true);
		registrationBean.setName("app");
		
		return registrationBean;
	}
}
