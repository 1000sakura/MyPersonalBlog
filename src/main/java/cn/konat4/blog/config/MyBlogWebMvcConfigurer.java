package cn.konat4.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.konat4.blog.interceptor.AdminLoginInterceptor;

@Configuration
public class MyBlogWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        
        // 添加一个拦截器，拦截所有以/admin为前缀的url路径
        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login").excludePathPatterns("/admin/dist/**").excludePathPatterns("/admin/plugins/**");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 映射静态文件, 把目录映射到对应的URL
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }
}
