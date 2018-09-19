package com.softmax.nettycode.simplechat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 *
 * @author Jarvis
 * @date 2018/9/19
 */
public class SimpleChatServer {
    /**
     * 是用来处理I/O操作的多线程事件循环器
     * 第一个经常被叫做‘boss’，用来接收进来的连接
     * 第二个经常被叫做‘worker’，用来处理已经被接收的连接，一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void start(int port) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChatServerHandler())
                //是提供给NioServerSocketChannel 用来接收进来的连接。
                .option(ChannelOption.SO_BACKLOG, 128)
                //是提供给由父管道 ServerChannel 接收到的连接，在这个例子中也是 NioServerSocketChannel。
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        System.out.println("SimpleChatServer 启动了:" + port);

        ChannelFuture future = bootstrap.bind(port).sync();
        future.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new SimpleChatServer().start(8887);
    }
}
