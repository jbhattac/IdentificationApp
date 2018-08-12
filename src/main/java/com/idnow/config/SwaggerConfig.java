/**
 * 
 */
package com.idnow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author JBhattacharjee
 *
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.idnow"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(generateApiInfo());

    }
    
    private ApiInfo generateApiInfo()
    {
        return new ApiInfo("idnow api", "Identification idnow API", "Version 1.0",
            "urn:tos", "joydeep.bha@gmail.com", "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
}

}
