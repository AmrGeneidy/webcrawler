package com.webcrawler.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.webcrawler.repository")
public class DynamoDBAsyncClientConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String dynamoDbEndpoint;

    @Value("${amazon.dynamodb.region}")
    private String dynamoDbRegion;

    @Value("${amazon.aws.accessKey}")
    private String awsAccessKey;

    @Value("${amazon.aws.secretKey}")
    private String awsSecretKey;

    @Bean(destroyMethod = "shutdown")
    public AmazonDynamoDBAsync amazonDynamoDBAsync() {
        return AmazonDynamoDBAsyncClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDbEndpoint, dynamoDbRegion))
                .build();
    }

    @Bean(destroyMethod = "shutdown")
    public DynamoDB dynamoDB(final AmazonDynamoDBAsync amazonDynamoDBAsync) {
        return new DynamoDB(amazonDynamoDBAsync);
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(final AmazonDynamoDBAsync amazonDynamoDBAsync) {
        return new DynamoDBMapper(amazonDynamoDBAsync);
    }
}