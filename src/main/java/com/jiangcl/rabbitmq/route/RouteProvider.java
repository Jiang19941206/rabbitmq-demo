package com.jiangcl.rabbitmq.route;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author jiangcl
 * @date 2020/12/9
 * @desc 使用Route方式实现订阅模型
 *      使用通配符来执行route key
 * audit.#    匹配audit.irs.corporate或者 audit.irs 等
 * audit.*   只能匹配 audit.irs
 */
public class RouteProvider {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMqUtil.connection();
        Channel channel = connection.createChannel();

        String exchangeName = "topics";

        channel.exchangeDeclare(exchangeName,"topic");

        //指定路由，只有绑定了此路由的队列才可消费此路由的消息
        channel.basicPublish(exchangeName,"user.save",null,"route key为：[user.save]的消息".getBytes());
        RabbitMqUtil.closeConnection(channel,connection);
    }
}
