package ar.edu.unq.inscripcionunq.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "ar.edu.unq.inscripcionunq.spring.controller" })
public class WebConfig extends WebMvcConfigurerAdapter {

}
