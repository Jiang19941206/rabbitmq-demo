package com.jiangcl.rabbitmq.direct;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author jiangcl
 * @date 2020/12/9
 * @desc 消费者2
 *          消费：error，warning 路由key的消息
 */
public class DirectConsumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.connection();
        Channel channel = connection.createChannel();

        String exchangeName = "logs_direct";
        channel.exchangeDeclare(exchangeName,"direct");

        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机和队列，消费者可以再次指定消费哪些routingKey的消息
        channel.queueBind(queueName,exchangeName,"error");
        channel.queueBind(queueName,exchangeName,"warning");

        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body));
            }
        });
    }
}
