package com.jiangcl.rabbitmq.simpledemo;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jiangcl
 * @date 2020/12/3
 * @desc 消费者
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMqUtil.connection();
        //创建通道
        Channel channel = connection.createChannel();

        //通道绑定对象
        channel.queueDeclare("hello",true,false,false,null);
        /**
         * 消费消息
         * 参数1：队列名称
         * 参数2：是否开启消息的自动确认机制
         * 参数3：消费消息的回调函数
         */
        channel.basicConsume("hello",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });
    }
}
