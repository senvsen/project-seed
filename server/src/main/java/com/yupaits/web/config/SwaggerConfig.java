package com.yupaits.web.config;

import com.yupaits.web.prop.ApiProps;
import com.yupaits.web.prop.model.GroupApiInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger接口文档配置
 * @author yupaits
 * @date 2018/10/16
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ApiProps apiProps;

    @Autowired
    public SwaggerConfig(ApiProps apiProps) {
        this.apiProps = apiProps;
    }

    @Bean
    public Docket authApi() {
        return genDocket("auth");
    }

    @Bean
    public Docket sysApi() {
        return genDocket("sys");
    }

    @Bean
    public Docket msgApi() {
        return genDocket("msg");
    }

    @Bean
    public Docket scheduleApi() {
        return genDocket("schedule");
    }

    /**
     * 根据分组名称生成Docket
     * @param groupName 分组名
     */
    private Docket genDocket(String groupName) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(apiInfo(groupName))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupaits." + groupName))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 获取ApiInfo信息
     * @param groupName 分组名
     */
    private ApiInfo apiInfo(String groupName) {
        GroupApiInfo groupApiInfo = apiProps.getGroups().stream()
                .filter(info -> StringUtils.equals(groupName, info.getName()))
                .findFirst().orElse(new GroupApiInfo());
        return new ApiInfoBuilder()
                .title(groupApiInfo.getTitle())
                .description(groupApiInfo.getDescription())
                .version(apiProps.getVersion())
                .contact(apiProps.getContact())
                .license(apiProps.getLicense())
                .licenseUrl(apiProps.getLicenseUrl())
                .termsOfServiceUrl(apiProps.getTermsOfServiceUrl())
                .build();
    }
}
