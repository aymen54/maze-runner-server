package de.zalando.mazerunner.framework;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableSwagger
@Controller
// generated at build-time and contains all properties declared in the pom.xml
@PropertySource("classpath:build.properties")
public class SwaggerConfig {

    @Value("${swaggerui.version}")
    private String swaggerUiVersion;
    
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(final SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns("/mazes.*");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Maze runner server API", "API for maze-runner-server", "", "", "",
                "");

        return apiInfo;
    }

    /**
     * Provide a canonical url for the user and redirect to actual index.html in webjar.
     * Furthermore provide swagger-ui the baseurl which is used to.
     */
    @RequestMapping("/swagger-ui")
    public String swaggerUi() {
        return String.format("redirect:/webjars/swagger-ui/%s/index.html?url=/api-docs/", swaggerUiVersion);
    }
}
