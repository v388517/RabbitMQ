package com.example.test;

import com.example.RabbitmqApplication;
import com.example.config.RabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class ProducerTest {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发消息
     */
    @Test
    public void testSendMessage() {
        for (int i = 0; i < 5; i++) {
            String message = "sms email inform to user " + i;
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_TOPICS_INFORM, "inform.sms.email", message);
            System.out.println("Send Message is:'" + message + "'");
        }

    }


}
