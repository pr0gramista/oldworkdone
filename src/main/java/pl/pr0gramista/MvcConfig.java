package pl.pr0gramista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.pr0gramista.user.UserMethodArgumentResolver;

import java.util.List;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    UserMethodArgumentResolver userMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userMethodArgumentResolver());
    }

    public UserMethodArgumentResolver userMethodArgumentResolver() {
        return userMethodArgumentResolver;
    }
}