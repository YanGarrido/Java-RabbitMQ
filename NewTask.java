import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.util.Map;

public class NewTask {
    //name the queue
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        //create a connection to the server:
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            Map<String, Object> args = Map.of("x-queue-type", "quorum");
            channel.queueDeclare(QUEUE_NAME, true,false,false,args);

            String message = String.join(" ", argv);
            channel.basicPublish("","hello",null, message.getBytes());
            System.out.println("[x] Sent '" + message + "'");
        }
    }
}