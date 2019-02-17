package com.gabrielfeitosa;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.net.URI;
import java.util.List;

public class Sqs {

    private SqsClient client;

    public Sqs(URI endpoint) {
        client = SqsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(endpoint)
                .build();
    }

    public List<Message> receiveMessage(String queueUrl) {
        return client.receiveMessage(ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(10)
                    .build())
                .messages();
    }

    public CreateQueueResponse createQueue(String queueName) {
        return client.createQueue(CreateQueueRequest.builder()
                .queueName(queueName)
                .build());
    }

    public SendMessageResponse sendMessage(String queueUrl, String msg) {
        return client.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(msg)
                .build());
    }
}
