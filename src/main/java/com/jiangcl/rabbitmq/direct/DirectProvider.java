package com.jiangcl.rabbitmq.direct;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author jiangcl
 * @date 2020/12/9
 * @desc 使用Direct方式实现订阅模型
 */
public class DirectProvider {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMqUtil.connection();
        Channel channel = connection.createChannel();

        String exchangeName = "logs_direct";

        channel.exchangeDeclare(exchangeName,"direct");

        //指定路由，只有绑定了此路由的队列才可消费此路由的消息
        String routeKey;

        int counter = 1;
        while (true){
            if(counter % 3 == 0) routeKey = "info";
            else if(counter % 3 == 1) routeKey = "error";
            else routeKey = "warning";
            channel.basicPublish(exchangeName,routeKey,null,("route key为：" + "[" + routeKey + "]的消息，counter = " + counter).getBytes());
            counter++;
            Thread.sleep(500l);
        }

        //RabbitMqUtil.closeConnection(channel,connection);
    }
}
