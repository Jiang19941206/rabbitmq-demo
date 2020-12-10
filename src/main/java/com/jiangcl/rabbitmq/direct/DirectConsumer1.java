package com.jiangcl.rabbitmq.direct;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author jiangcl
 * @date 2020/12/9
 * @desc 消费者1
 *          消费：info，error 路由key的消息
 */
public class DirectConsumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.connection();
        Channel channel = connection.createChannel();

        String exchangeName = "logs_direct";
        channel.exchangeDeclare(exchangeName,"direct");

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,exchangeName,"info");
        channel.queueBind(queueName,exchangeName,"error");

        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body));
            }
        });
    }
}
