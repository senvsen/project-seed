package com.yupaits.web.prop;

import com.google.common.collect.Lists;
import com.yupaits.web.prop.model.GroupApiInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Contact;

import java.util.List;

/**
 * @author yupaits
 * @date 2018/10/17
 */
@Data
@Configuration
@ConfigurationProperties(value = "api", ignoreInvalidFields = true)
public class ApiProps {
    private String version = "v1.0.0";
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private List<GroupApiInfo> groups = Lists.newArrayList();

    public Contact getContact() {
        return new Contact(contactName, contactUrl, contactEmail);
    }
}
