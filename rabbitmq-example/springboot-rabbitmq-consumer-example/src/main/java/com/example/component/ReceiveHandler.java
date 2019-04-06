package com.example.component;

import com.example.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听
 **/
@Component
public class ReceiveHandler {

    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_EMAIL})
    public void send_email(String msg,Message message,Channel channel){
        System.out.println("receive message is:"+msg);
    }


    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_SMS})
    public void send_sms(String msg,Message message,Channel channel){
        System.out.println("receive message is:"+msg);
    }

}
