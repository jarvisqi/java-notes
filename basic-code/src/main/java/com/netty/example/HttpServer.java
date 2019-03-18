package com.netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Jarvis
 * @date 2019/3/13
 */
public class HttpServer {

    private Integer port;

    public HttpServer(Integer port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        //为启动引导器
        ServerBootstrap bootstrap = new ServerBootstrap();
        //时间循环器,两个组实际上是两个线程组
        //bossGroup 负责获取客户端连接，接收到之后会将该连接转发到 workerGroup 进行处理
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        bootstrap.group(boss, work)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpServerInitializer());

        Channel ch = bootstrap.bind(port).sync().channel();
        System.out.println("Netty http server listening on port " + port);
        ch.closeFuture().sync();
    }
}
