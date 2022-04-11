package com.example.club_project.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class AwsConfigure {

    private String region;

    private String url;

    private String bucket;

    private String accessKey;

    private String secretKey;
}
