package org.example.Config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.Controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(securityScheme()));
    }

    @Bean
    public static BeanPostProcessor swaggerFix() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider) {
                    customize((WebMvcRequestHandlerProvider) bean);
                }
                return bean;
            }

            private void customize(WebMvcRequestHandlerProvider provider) {
                try {
                    Field field = ReflectionUtils.findField(WebMvcRequestHandlerProvider.class, "handlerMappings");
                    if (field != null) {
                        field.setAccessible(true);
                        @SuppressWarnings("unchecked")
                        List<RequestMappingInfoHandlerMapping> mappings = (List<RequestMappingInfoHandlerMapping>) field.get(provider);
                        List<RequestMappingInfoHandlerMapping> filtered = mappings.stream()
                                .filter(m -> m.getPatternParser() == null)
                                .collect(Collectors.toList());
                        field.set(provider, filtered);
                    }
                } catch (Exception ignored) {
                }
            }
        };
    }

    private SecurityScheme securityScheme() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(
                        new SecurityReference("Authorization", new AuthorizationScope[0])
                ))
                .operationSelector(operationContext -> true)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电影购票系统 API 文档")
                .description("提供电影、影院、订单、用户、排片等接口文档")
                .version("1.0")
                .build();
    }
}
