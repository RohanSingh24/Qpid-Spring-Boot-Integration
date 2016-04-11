package com.qpid.spring.integration.qpid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.*;

@EnableScheduling
public class QpidProvider {

    private static final Logger logger = LoggerFactory.getLogger(QpidProvider.class.getSimpleName());

    Connection connection;
    Queue queue;

    @Autowired
    public QpidProvider(Connection connection, Queue queue) {
        this.connection = connection;
        this.queue = queue;
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 10000)
    public void enqueue() {

        Session session = null;
        MessageProducer messageProducer=null;
        Message message=null;
        try {
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer=session.createProducer(queue);
            message.setStringProperty("Message", "Hello");
            messageProducer.send(message);
            messageProducer.close();

        } catch (JMSException e1) {
            logger.error(e1.getMessage());
        }
    }

    @Scheduled(initialDelay = 4000, fixedDelay = 15000)
    public void dequeue() {
        Session session=null;
        Message message=null;
        MessageConsumer messageConsumer=null;
        try {
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            messageConsumer = session.createConsumer(queue);
            message = messageConsumer.receive(3000);
            messageConsumer.close();

        } catch (JMSException e1) {
            logger.error("Error in retrieving notification from queue.\n {}", e1.getMessage());
        }

        if(message!=null) {
           try {
               System.out.println("Message: "+message.getStringProperty("Message"));
            } catch (JMSException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
