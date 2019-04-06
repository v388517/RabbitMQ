package com.example.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
public class RabbitConfig {
    //声明队列
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    //声明交换机
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_springBoot_topics_inform";

    /**
     * 交换机配置
     */
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        //durable(true)持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }
    /**
     * 声明队列
     */
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL(){
        return  new Queue(QUEUE_INFORM_EMAIL);
    }
    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS(){
        return  new Queue(QUEUE_INFORM_SMS);
    }
    /**
     * 绑定交换机
     */
    @Bean
    public Binding BIDING_INFORM_SMS(@Qualifier(QUEUE_INFORM_SMS)Queue queue,
                                     @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
    }
    @Bean
    public Binding BIDING_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL)Queue queue,
                                     @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
    }



}
