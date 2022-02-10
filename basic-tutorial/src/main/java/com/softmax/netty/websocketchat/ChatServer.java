package com.softmax.netty.websocketchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * 引导服务器，设置 ChannelInitializer
 *
 * @author Jarvis
 * @date 2018/09/19
 */
public class ChatServer {
    final EventLoopGroup loopGroup = new NioEventLoopGroup();
    final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void start(int port) {

        try {
            //引导 服务器
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(loopGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //创建 ChannelInitializer
                    .childHandler(new ChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("WebsocketChatServer 启动了" + port);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = bootstrap.bind(port).sync();
            // 关闭服务器。
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            loopGroup.shutdownGracefully();

            System.out.println("WebsocketChatServer 关闭了");
        }
    }

    public static void main(String[] args) {
        int port = 8888;
        new ChatServer().start(port);
        System.out.println("WebSocketChatServer 启动：" + port);

    }
}
