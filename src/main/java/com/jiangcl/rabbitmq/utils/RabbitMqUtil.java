package com.jiangcl.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jiangcl
 * @date 2020/12/4
 * @desc RabbitMq连接工具
 */
public class RabbitMqUtil {
    private static ConnectionFactory connectionFactory;


    /**
     * @desc 初始化ConnectionFactory
     * @author jiangcl
     * @date 2020/12/4
     * @param null
     * @return
     */
    static {
        connectionFactory = new ConnectionFactory();
        //设置连接mq的主机
        connectionFactory.setHost("192.168.163.128");
        //端口
        connectionFactory.setPort(5672);
        //设置连接的虚拟主机
        connectionFactory.setVirtualHost("jiangcl");
        //设置具有访问此主机权限的用户名密码
        connectionFactory.setUsername("jiangcl");
        connectionFactory.setPassword("123");
    }

    /**
     * @desc 获取rabbitmq连接
     * @author jiangcl
     * @date 2020/12/4
     * @param
     * @return com.rabbitmq.client.Connection
     */
    public static Connection connection(){
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @desc 关闭连接
     * @author jiangcl
     * @date 2020/12/4
     * @param channel
     * @param connection
     * @return void
     */
    public static void closeConnection(Channel channel,Connection connection){
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
