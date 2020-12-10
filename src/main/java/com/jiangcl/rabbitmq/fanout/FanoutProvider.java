package com.jiangcl.rabbitmq.fanout;

import com.jiangcl.rabbitmq.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author jiangcl
 * @date 2020/12/9
 * @desc 广播模式，一条消息被绑定该交换机的所有消费者消费
 */
public class FanoutProvider {

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = RabbitMqUtil.connection();
        Channel channel = connection.createChannel();
        //声明交换机 参数一：交换机名称   参数二：交换机类型，广播模式为“fanout”
        channel.exchangeDeclare("logs","fanout");
        //发送消息  参数二：路由key，广播模式中不需要此参数
        int counter = 1;
        while (true){
            channel.basicPublish("logs","",null,("消息---" + counter).getBytes());
            counter++;
            Thread.sleep(500l);
        }
        //RabbitMqUtil.closeConnection(channel,connection);
    }
}
