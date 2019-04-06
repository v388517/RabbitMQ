package com.example.routing.Consumer;

import com.rabbitmq.client.*;
import util.RabbitmqUtils;

import java.io.IOException;

/**
 * 消息消费者
 * 若一次发送多个消息，有多个消费者在进行监听，则使用轮询的方式进行消息处理
 */
public class Consumer_Email {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";

    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform"; //交换机名名称

    //设置路由key
    private static final String ROUTINGKEY_EMAIL="inform_email";


    public static void main(String[] args) {

        Connection connection = null;

        Channel channel = null;
        try {
            //获取连接
            connection = RabbitmqUtils.crateConnection();
            //创建通道
            channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);

            //声明队列
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            //交换机和队列绑定
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, ROUTINGKEY_EMAIL);


            /**
             * 监听队列
             * 1、队列名称
             * 2、是否自动回复,若设置为false，则需要手动回复，否则消息不被消费
             * 3、消息方法
             */
            channel.basicConsume(QUEUE_INFORM_EMAIL, true, new DefaultConsumer(channel) {
                /**
                 *当接收到消息后会调用此方法
                 * @param consumerTag 消费者标签，可以设置也可以不设置，可以在监听队列的时候设置
                 * @param envelope 信封 ,通过这个可以获取消息的id,用于手动关闭消息，此外还可以获取exchange
                 * @param properties 消息的属性
                 * @param body 消息内容
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //获取交换机

                    System.out.println("收到的消息是：" + new String(body, "utf-8"));

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
