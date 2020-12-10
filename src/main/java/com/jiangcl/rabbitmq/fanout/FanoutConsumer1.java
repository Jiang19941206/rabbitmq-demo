package com.jiangcl.rabbitmq.fanout;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author jiangcl
 * @date 2020/12/9
 * @desc 广播模式消费者一
 */
public class FanoutConsumer1 {

    public static void main(String[] args) throws Exception{
        Connection connection = RabbitMqUtil.connection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs","fanout");
        //获取临时队列
        String queueName = channel.queueDeclare().getQueue();
        //将临时队列绑定到交换机
        channel.queueBind(queueName,"logs","");
        /**
         * 消费者1模拟消费速度比较缓慢的情况
         */
        channel.basicQos(1);
        channel.basicConsume(queueName,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者1：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
