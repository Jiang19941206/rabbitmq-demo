package com.jiangcl.rabbitmq.fanout;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author jiangcl
 * @date 2020/12/9
 * @desc
 */
public class FanoutConsumer3 {

    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMqUtil.connection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs","fanout");
        //获取临时队列
        String queueName = channel.queueDeclare().getQueue();
        //将临时队列绑定到交换机
        channel.queueBind(queueName,"logs","");

        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者3：" + new String(body));
            }
        });
    }
}
