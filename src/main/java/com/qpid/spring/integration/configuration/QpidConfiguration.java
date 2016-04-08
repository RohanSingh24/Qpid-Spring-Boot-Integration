package com.qpid.spring.integration.configuration;

import org.apache.qpid.AMQException;
import org.apache.qpid.client.AMQConnection;
import org.apache.qpid.client.AMQQueue;
import org.apache.qpid.url.URLSyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.jms.Connection;
import javax.jms.Queue;
import java.net.URISyntaxException;


@Configuration
@PropertySource("classpath:application.properties")
public class QpidConfiguration {

    @Value("${qpid.provider.url}")
    private String url;

    @Value("${queue.topic.name}")
    private String queueName;

    public final String addr = "ADDR:";
    public final String queueinstruction = "; {create: always}";

    @Bean
    public Connection getJMSConnection() {
        Connection connection = null;
        try {
            connection = new AMQConnection(url);
        } catch (AMQException e) {
            e.printStackTrace();
        } catch (URLSyntaxException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Bean
    public Queue jmsQueue()
    {
        Queue queue = null;
        try {
            queue = new AMQQueue(addr + queueName + queueinstruction);
        }catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return queue;
    }
}
