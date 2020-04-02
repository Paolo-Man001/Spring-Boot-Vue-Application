package com.example.springbootvue;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootVueApplication {

   public static void main(String[] args) {
      SpringApplication.run(SpringBootVueApplication.class, args);
   }


   // bootstrap TEST-DATA into the H2 - in memory Db :
	@Bean
   ApplicationRunner init(TodoRepository repository) {
      return args -> {
         Stream.of("Study ASP.net", "Meditate", "Binge-watch some Netflix or Derren Brown", "Finish Spring-Boot + React project")
                 .forEach(name -> {
                    Todo todo = new Todo();
                    todo.setTitle(name);
                    repository.save(todo);
                 });
         repository.findAll().forEach(System.out::println);
      };
   }

	// Fixing the CORS Errors :
	@Bean
	public FilterRegistrationBean simpleCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);

		// *** Setup URL to MATCH the Vue client URL + PORT ***
		config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));	// Vue's PORT
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		// *** REGISTER these configurations and APPLY to Bean :
		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source)); // This was the original. Causes error when CorsFilter() has Args. 
		FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter());			// This compiles when no args inside CorsFilter()
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return bean;
	}

}
