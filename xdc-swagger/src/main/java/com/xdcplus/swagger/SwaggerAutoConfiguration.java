package com.xdcplus.swagger;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationWebMvcConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * swagger配置
 *
 * @author Rong.Jia
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({BeanValidatorPluginsConfiguration.class,
        Swagger2DocumentationWebMvcConfiguration.class})
public class SwaggerAutoConfiguration {

    private static final String DEFAULT_BASE_PATH = "/**";
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");

    /**
     * 引入Knife4j扩展类
     */
    private final OpenApiExtensionResolver openApiExtensionResolver;

    private final SwaggerProperties swaggerProperties;

    private final Environment environment;

    @Bean
    @ConditionalOnMissingBean
    public Docket api() {

        ApiSelectorBuilder apis = new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo(swaggerProperties))
                .ignoredParameterTypes(getIgnoredParameterTypes(swaggerProperties.getIgnoredParameterTypes())).select()
				.apis(SwaggerUtil.basePackages(swaggerProperties.getBasePackages()));

        getBasePath().forEach(p -> apis.paths(PathSelectors.ant(p)));
        getExcludePath().forEach(p -> apis.paths(PathSelectors.ant(p).negate()));

        return apis.build()
				.securityContexts(securityContexts(swaggerProperties))
				.securitySchemes(securitySchemas(swaggerProperties))
                .extensions(openApiExtensionResolver
						.buildExtensions(getName()));
    }

	private String getName() {
		String name = environment.getProperty("spring.application.name");
		return StrUtil.isBlank(name) ? "default" : name;
	}

    private List<String> getBasePath() {
        List<String> basePath = swaggerProperties.getBasePath();
        if (CollectionUtil.isEmpty(basePath)) {
            basePath.add(DEFAULT_BASE_PATH);
        }
        return basePath;
    }

    private List<String> getExcludePath() {
        List<String> excludePath = swaggerProperties.getExcludePath();
        if (CollectionUtil.isEmpty(excludePath)) {
            excludePath.addAll(DEFAULT_EXCLUDE_PATH);
        }
        return excludePath;
    }

    private Class<?>[] getIgnoredParameterTypes(List<Class<?>> ignoredParameterTypes) {
        Class<?>[] array = new Class[ignoredParameterTypes.size()];
        return ignoredParameterTypes.toArray(array);
    }

    /**
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     */
    private List<SecurityContext> securityContexts(SwaggerProperties swaggerProperties) {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(defaultAuth(swaggerProperties))
                .forPaths(PathSelectors.regex(swaggerProperties.getAuthorization().getAuthRegex()))
                .build());
    }

    /**
     * 默认的全局鉴权策略
     */
    private List<SecurityReference> defaultAuth(SwaggerProperties swaggerProperties) {
        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        List<SwaggerProperties.AuthorizationScope> swaggerScopeList = swaggerProperties.getAuthorization().getAuthorizationScopeList();
        swaggerScopeList.forEach(authorizationScope -> authorizationScopeList.add(new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription())));
        if (authorizationScopeList.size() == 0) {
            authorizationScopeList.add(new AuthorizationScope("global", "accessEverywhere"));
        }
        AuthorizationScope[] authorizationScopes = authorizationScopeList.toArray(new AuthorizationScope[0]);
        swaggerScopeList.forEach(authorizationScope -> securityReferenceList.add(new SecurityReference(authorizationScope.getName(), authorizationScopes)));
        if (securityReferenceList.size() == 0) {
            securityReferenceList.add(new SecurityReference(SwaggerUtil.apiKey().getName(), authorizationScopes));
        }
        return securityReferenceList;
    }

    /**
     * 配置安全策略
     */
    private List<SecurityScheme> securitySchemas(SwaggerProperties swaggerProperties) {
        List<SwaggerProperties.AuthorizationApiKey> swaggerApiKeyList = swaggerProperties.getAuthorization().getAuthorizationApiKeyList();
        if (swaggerApiKeyList.size() == 0) {
            return CollectionUtil.newArrayList(SwaggerUtil.apiKey());
        } else {
            List<SecurityScheme> securitySchemeList = new ArrayList<>();
            swaggerApiKeyList.forEach(authorizationApiKey
                    -> securitySchemeList.add(SwaggerUtil.apiKey(authorizationApiKey.getName(),
                    authorizationApiKey.getKeyName(), authorizationApiKey.getPassAs())));
            return securitySchemeList;
        }
    }

    /**
     * 配置基本信息
     */
    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }



}
