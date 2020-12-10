package com.jiangcl.rabbitmq.workqueue;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author jiangcl
 * @date 2020/12/4
 * @desc 负载均衡模式，一条消息随机被几个消费者中的其中一个消费
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = RabbitMqUtil.connection();
        //创建通道
        Channel channel = connection.createChannel();
        //绑定队列
        channel.queueDeclare("work_queue",true,false,false,null);
        int counter = 1;
        while (true){
            System.out.println(counter);
            //发送消息
            channel.basicPublish("","work_queue",null,String.valueOf("消息：" + counter).getBytes());
            Thread.sleep(200);
            counter++;
        }



    }
}
