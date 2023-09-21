package hello.springmvc.login;

import hello.springmvc.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.springmvc.login.web.filter.LogFilter;
import hello.springmvc.login.web.filter.LoginCheckFilter;
import hello.springmvc.login.web.intercepter.LogIntercepter;
import hello.springmvc.login.web.intercepter.LoginCheckIntercepter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogIntercepter())
                .order(1)
                .addPathPatterns("/**") // 서블릿과 달리 **이라고 해줘야함 - 하위는 전부다라는 뜻
                .excludePathPatterns("/css/**", "*.ico", "/error");

        registry.addInterceptor(new LoginCheckIntercepter())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login",
                        "/logout", "/css/**", "/*.ico", "/error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    //@Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
