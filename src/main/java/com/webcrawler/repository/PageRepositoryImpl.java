package com.webcrawler.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.webcrawler.model.Page;
import com.webcrawler.util.CompletablePromise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Repository
public class PageRepositoryImpl implements PageRepository {
//    @Autowired
//    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDBAsync amazonDynamoDBAsync;

    @PostConstruct
    private void initializeDatabase() {
//        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBAsync);
        CreateTableRequest createTableRequest = buildPagesTableCreateRequest();
        TableUtils.createTableIfNotExists(amazonDynamoDBAsync, createTableRequest);
    }

    @Override
    public CompletablePromise<PutItemResult> savePage(Page page) {
        PutItemRequest putItemRequest = new PutItemRequest();

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("url", new AttributeValue().withS(page.getUrl()));
        item.put("body", new AttributeValue().withS(page.getBody()));

        putItemRequest.withTableName("pages");
        putItemRequest.withItem(item);

        return new CompletablePromise<>(amazonDynamoDBAsync.putItemAsync(putItemRequest));
    }

    @Override
    public CompletableFuture<String> getBody(String url) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("url", new AttributeValue().withS(url));

        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.withTableName("pages");
        getItemRequest.withKey(key);
        return new CompletablePromise<>(amazonDynamoDBAsync.getItemAsync(getItemRequest))
                .thenApply(GetItemResult::getItem).thenApply(map -> map.get("body").getS());
    }

    private CreateTableRequest buildPagesTableCreateRequest() {
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("url")
                .withAttributeType("S"));

        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
        tableKeySchema.add(new KeySchemaElement()
                .withAttributeName("url")
                .withKeyType(KeyType.HASH));

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName("pages")
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 5)
                        .withWriteCapacityUnits((long) 1))
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(tableKeySchema);

        return createTableRequest;
    }
}
