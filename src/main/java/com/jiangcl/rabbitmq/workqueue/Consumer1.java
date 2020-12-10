package com.jiangcl.rabbitmq.workqueue;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author jiangcl
 * @date 2020/12/4
 * @desc 消费者一
 */
public class Consumer1 {

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = RabbitMqUtil.connection();
        //创建通道
        final Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.queueDeclare("work_queue",true,false,false,null);
        channel.basicConsume("work_queue",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                /**
                 * 若两个消费者的处理能力相同，则会平均消费此队列中的消息
                 * 将消费者1延迟一秒，实现能者多劳场景
                 *      能者多劳：由于consumer2的消息处理能力强于consumer1，所以处理的消息会比consumer1更多
                 */
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者——>" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
