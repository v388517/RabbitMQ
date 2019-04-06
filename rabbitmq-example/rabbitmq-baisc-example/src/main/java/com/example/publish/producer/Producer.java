package com.example.publish.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitmqUtils;

/**
 * 工作模式： 发送一个消息，多个人接收
 * 相当于topic广播
 *
 * 案例：发送邮件和email
 */
public class Producer {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email"; //用于发送email
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";//用于发送sms
    //交换机名名称
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    public static void main(String[] args) {
        Connection connection=null;
        Channel channel=null;
        try {
          connection= RabbitmqUtils.crateConnection();
          channel=connection.createChannel();
            /**
             * 声明交换机
             * 1、交换机名称
             * 2、交换机类型
             *      fanout：对应工作模式：发布订阅public/subscribe
             *      topic： 对应Topics工作模式
             *      direct: 对应的Routing工作模式
             *      headers ：对应headers工作模式
             */

            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            /**
             * 绑定交换机
             * 1、队列名称
             * 2、交换机名称
             * 2、路由key，作用是交换机根据路由key的值将消息转发到指定的队列中，在发布订阅模式中调协为空串
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,"");
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,"");
            //发送消息
            for (int i = 0; i < 5; i++) {
                /**
                 * 参数：
                 * 参数1：交换机，如果不指定将使用mq的默认交换机,不能为null，可设置为空串
                 * 参数2：路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，则应该设置为队列的名称
                 * 参数3：消息的属性
                 * 参数4：消息内容
                 */
                channel.basicPublish(EXCHANGE_FANOUT_INFORM,"",null,("我是topic"+i).getBytes());
            }
            System.out.println("消息发送成功...");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            RabbitmqUtils.close(connection,channel);
        }


    }


}
