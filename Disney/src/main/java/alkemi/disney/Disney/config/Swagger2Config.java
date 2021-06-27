package alkemi.disney.Disney.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket api() {
            ParameterBuilder parameterBuilder = new ParameterBuilder();
            parameterBuilder.name("Authorization")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .description("JWT token")
                    .required(false)
                    .defaultValue("Bearer ")
                    .build();
            List<Parameter> parameters = new ArrayList<>();
            parameters.add(parameterBuilder.build());
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("alkemi.disney"))
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(apiInfo())
                    .securitySchemes(Arrays.asList(apiKey()))
                    //.securityContexts(Lists.newArrayList(securityContext()));
                    //Setting globalOperationParameters ensures that authentication header is applied to all APIs
                    .globalOperationParameters(parameters);

    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Disney API",
                "Some custom description of API.",
                "1.0",
                "Terms of service",
                new Contact("Diaz Patricio", "www.myURL.com", "diaz.patriciodaniel@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }
    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    /*private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }*/

}
//http://localhost:8080/swagger-ui.html#/