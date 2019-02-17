import cloud.localstack.docker.LocalstackDocker;
import cloud.localstack.docker.LocalstackDockerTestRunner;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.Message;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(LocalstackDockerTestRunner.class)
@LocalstackDockerProperties(randomizePorts = true, services = {"sqs"}, pullNewImage = false)
public class SqsTest {

    private Sqs sqs = new Sqs(URI.create(LocalstackDocker.INSTANCE.getEndpointSQS()));

    @Test
    public void createQueue() {
        CreateQueueResponse queue = sqs.createQueue("createQueue");
        assertEquals(queue.queueUrl(), LocalstackDocker.INSTANCE.getEndpointSQS()+"/queue/createQueue");
    }

    @Test
    public void receiveMessage() {
        CreateQueueResponse queue = sqs.createQueue("sendMessage");
        String msgTest = "Blog do Gabriel Feitosa";
        sqs.sendMessage(queue.queueUrl(), msgTest);
        List<Message> messages = sqs.receiveMessage(queue.queueUrl());
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0).body(),msgTest);
    }
}
