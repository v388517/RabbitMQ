package com.example.routing.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitmqUtils;

/**
 * 消息发送者
 */
public class Producer {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform"; //交换机名名称

    //设置路由key
    private static final String ROUTINGKEY_EMAIL="inform_email";
    private static  final String ROUTINGKEY_SMS="inform_sms";

    public static void main(String[] args) {
        Connection connection=null;
        Channel channel=null;
        try {
            connection=RabbitmqUtils.crateConnection();
            channel=connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            //绑定交换机
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_ROUTING_INFORM,ROUTINGKEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,ROUTINGKEY_SMS);

            for (int i = 0; i < 5; i++) {
                String email = "send inform message to user email" + i;
                String sms = "send inform message to user message" + i;
                /**
                 * 参数：
                 * 参数1：交换机，如果不指定将使用mq的默认交换机,不能为null，可设置为空串
                 * 参数2：路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，则应该设置为队列的名称
                 * 参数3：消息的属性
                 * 参数4：消息内容
                 */
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTINGKEY_EMAIL, null, email.getBytes());
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTINGKEY_SMS, null, sms.getBytes());


            }
            System.out.println("发送消息成功....");





        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            RabbitmqUtils.close(connection,channel);

        }


    }



}
