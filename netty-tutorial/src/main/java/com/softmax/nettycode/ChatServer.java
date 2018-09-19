package com.softmax.nettycode;

import com.softmax.nettycode.chat.ChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * 引导服务器，设置 ChannelInitializer
 */
public class ChatServer {
    /**
     * 创建 DefaultChannelGroup 用来 保存所有连接的的 WebSocket channel
     */
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup loopGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) {
        //引导 服务器
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(loopGroup)
                .channel(NioServerSocketChannel.class)
                //创建 ChannelInitializer
                .childHandler(new ChatServerInitializer(channelGroup));

        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    /**
     * 处理服务器关闭，包括释放所有资源
     */
    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        channelGroup.close();
        loopGroup.shutdownGracefully();
    }


    public static void main(String[] args) {
        final ChatServer server = new ChatServer();

        ChannelFuture future = server.start(new InetSocketAddress(8080));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.destroy()));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
