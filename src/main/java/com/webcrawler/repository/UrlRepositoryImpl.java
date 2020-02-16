package com.webcrawler.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.webcrawler.util.CompletablePromise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Repository
public class UrlRepositoryImpl implements UrlRepository {
    
    private static final String TABLE_NAME = "urls";
    private static final String URL = "url";
            
    @Autowired
    private AmazonDynamoDBAsync amazonDynamoDBAsync;

    @PostConstruct
    private void initializeDatabase() {
        CreateTableRequest createTableRequest = buildPagesTableCreateRequest();
        TableUtils.deleteTableIfExists(amazonDynamoDBAsync, buildDeleteTableRequest());
        TableUtils.createTableIfNotExists(amazonDynamoDBAsync, createTableRequest);
    }

    @Override
    public CompletableFuture<PutItemResult> insert(String url) {
        PutItemRequest putItemRequest = new PutItemRequest();

        Map<String, AttributeValue> item = new HashMap<>();
        item.put(URL, new AttributeValue().withS(url));

        putItemRequest.withTableName(TABLE_NAME);
        putItemRequest.withItem(item);

        return new CompletablePromise<>(amazonDynamoDBAsync.putItemAsync(putItemRequest));
    }

    @Override
    public CompletableFuture<Boolean> contains(String url) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(URL, new AttributeValue().withS(url));

        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.withTableName(TABLE_NAME);
        getItemRequest.withKey(key);
        return new CompletablePromise<>(amazonDynamoDBAsync.getItemAsync(getItemRequest))
                .thenApply(GetItemResult::getItem).thenApply(Objects::nonNull);
    }

    private DeleteTableRequest buildDeleteTableRequest(){
        return new DeleteTableRequest(TABLE_NAME);
    }
    private CreateTableRequest buildPagesTableCreateRequest() {
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(URL)
                .withAttributeType("S"));

        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
        tableKeySchema.add(new KeySchemaElement()
                .withAttributeName(URL)
                .withKeyType(KeyType.HASH));

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(TABLE_NAME)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 5)
                        .withWriteCapacityUnits((long) 1))
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(tableKeySchema);

        return createTableRequest;
    }
}
