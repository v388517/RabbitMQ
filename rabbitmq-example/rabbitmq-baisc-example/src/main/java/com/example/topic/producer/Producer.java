package com.example.topic.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitmqUtils;

/**
 * 消息发送者：
 * 测试topic模式
 */
public class Producer {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
   //交换机名称
    private static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
    private static final String ROUTINGKEY_EMAIL="inform.#.email.#";
    private static final String ROUTINGKEY_SMS="inform.#.sms.#";

    public static void main(String[] args) {
        Connection connection=null;
        Channel channel=null;
        try {
            connection=RabbitmqUtils.crateConnection();
            channel=connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);
            //声明队列
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);
            //交换机和队列绑定
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_TOPICS_INFORM,ROUTINGKEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_TOPICS_INFORM,ROUTINGKEY_SMS);
            //发送消息
            for (int i = 0; i < 5; i++) {
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,ROUTINGKEY_EMAIL,null,("email message"+i).getBytes());
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,ROUTINGKEY_SMS,null,("msg message"+i).getBytes());

            }
            System.out.println("发送成功...");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送失败....");
        }finally {
            RabbitmqUtils.close(connection,channel);


        }


    }

}
