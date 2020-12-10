package com.jiangcl.rabbitmq.workqueue;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author jiangcl
 * @date 2020/12/4
 * @desc 消费者二
 */
public class Consumer2 {
    public static void main(String[] args) throws Exception {

        //获取连接
        Connection connection = RabbitMqUtil.connection();
        //创建通道
        final Channel channel = connection.createChannel();
        /**
         * 每次只消费一个消息，避免一次性消费所有消息
         *      一次性读取所有消息的弊端：
         *          当程序阻塞或者宕机时，会造成为处理的消息丢失
         */
        channel.basicQos(1);
        channel.queueDeclare("work_queue",true,false,false,null);
        /**
         * 参数2：autoAck是否自动确认
         *          当为true时，消费者每消费一条消息，队列中就删除一条
         *          当为false时，消费者消费完消息过后，消息并不会自动删除，需要手动进行确认后删除
         */
        channel.basicConsume("work_queue",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者——>" + new String(body));
                /**
                 * 参数一 ：确认队列中的那个具体消息
                 * 参数二：是否开启多条消息同时确认
                 */
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
