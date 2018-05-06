package barrenlandanalysis;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import javax.servlet.Servlet;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.core.Ordered;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class CustomWebMvcAutoConfig implements WebMvcConfigurer {
 
  // @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	String sharedReportsPath = "file:shared/build/reports/tests/test/";
	String serverReportsPath = "file:server/build/reports/tests/test/";
 
	registry.addResourceHandler("/reports/shared/**").addResourceLocations(sharedReportsPath);
	registry.addResourceHandler("/reports/server/**").addResourceLocations(serverReportsPath);
 
	// super.addResourceHandlers(registry);
  }
}