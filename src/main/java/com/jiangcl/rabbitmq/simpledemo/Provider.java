package com.jiangcl.rabbitmq.simpledemo;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jiangcl
 * @date 2020/12/3
 * @desc 生产者
 */
public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接对象
        Connection connection = RabbitMqUtil.connection();

        //获取连接通道
        Channel channel = connection.createChannel();

        /**
         * 绑定对应消息队列
         * 参数1：队列名称，如果此队列不存在则自动创建
         * 参数2：用来定义队列是否要持久化 true：持久化 false：不持久化
                  若消息不持久化，则当rabbitmq服务重启的时候，此消息会被清除
         * 参数3：是否独占队列 true 独占 false：非独占
         * 参数4：消费完成后是否删除队列 true：删除 false：不删除
         * 参数5：额外附加参数
         */
        channel.queueDeclare("hello",true,false,false,null);

        /**
         * 发布消息
         * 参数1：交换机名称
         * 参数2：队列名称
         * 参数3：传递消息额外设置
         * 参数4：消息的具体内容
         */
        channel.basicPublish("","hello",null,"我是第一条消息".getBytes());
        RabbitMqUtil.closeConnection(channel,connection);
    }
}
