package com.risingtest.wanted;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;

@Component
@Order
public class SpringfoxSwaggerHostResolver implements WebMvcOpenApiTransformationFilter {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public boolean supports(DocumentationType delimiter) {
        return delimiter == DocumentationType.OAS_30;
    }

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI swagger = context.getSpecification();

        Server server = swagger.getServers().get(0);
        if (server.getUrl().contains(":80")) {
            server.setUrl(server.getUrl().replace(":80",":443").replace("http://","https://"));
        }
        else{
            server.setUrl("https://dev.odoong.shop:443");
        }

        return swagger;
    }
}