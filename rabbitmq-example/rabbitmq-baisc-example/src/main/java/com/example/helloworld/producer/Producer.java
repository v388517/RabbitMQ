package com.example.helloworld.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import util.RabbitmqUtils;



/**
 * 入门程序：消息提供者
 */
public class Producer {
    //队列名称
    private static final String QUEUE_NAME = "helloWorld";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
           /* //创建工厂
            ConnectionFactory factory = new ConnectionFactory();
            //添加参数
            factory.setHost("localhost");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            //设置虚拟机，rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务
            factory.setVirtualHost("/");
            //创建连接
            connection = factory.newConnection();*/
            //使用工具类创建对象
            connection = RabbitmqUtils.crateConnection();

            //创建连接通道
            channel = connection.createChannel();
            /**
             * 声明队列:若没有则创建
             * 参数一：队列名称
             * 参数二：是否持久化。重启后队列还存在
             * 参数三：是否独占连接。队列只允许在该连接中访问，如果connection连接关闭队列则自动删除，如果将此参数设置true可用于临时队列的创建
             * 参数四：自动删除，队列不再使用时是否自动删除此队列，如果将此参数和参数三设置为true，则设置为临时队列
             * 参数五：参数，可以设置一个队列的扩展参数；比如:可设置存活时间
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            /**
             * 发布消息
             * 参数1：交换机，如果不指定将使用mq的默认交换机,不能为null，可设置为空串
             * 参数2：路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，则应该设置为队列的名称
             * 参数3：消息的属性
             * 参数4：消息内容
             * 注：设置的交换机为默认交换机，路由key为队列名称，消息属性null，发送消息为hello rabbitmq
             */
            for (int i = 0; i < 5; i++) {
                channel.basicPublish("", QUEUE_NAME, null, ("hello rabbitmq"+i).getBytes());
            }


            //channel.basicPublish("", QUEUE_NAME, null, "hello rabbitmq".getBytes());
            System.out.println("消息发送成功...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("消息发送失败....");
        } finally {
            //释放资源
            RabbitmqUtils.close(connection, channel);
        }


    }


}
