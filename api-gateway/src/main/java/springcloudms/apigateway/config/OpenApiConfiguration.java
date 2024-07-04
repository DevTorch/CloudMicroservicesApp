package springcloudms.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Autowired
    private RouteDefinitionLocator locator;

    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();

        List<RouteDefinition> definitions = locator.getRouteDefinitions()
                .collectList()
                .block();

        definitions.stream().filter(routeDefinition -> routeDefinition.getId()
                .matches(".*_service_swagger")).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("_service_swagger", "");
            GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder().pathsToMatch("/api/" + name + "/**")
                    .group(name)
//                    .setGroup(name)
                    .build();
            groups.add(groupedOpenApi);
        });
        return groups;
    }
}
