package com.softmax.netty.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetAddress;


/**
 * 自定义实现的一个服务初始化器
 *
 * @author Jarvis
 * @date 2019/3/13
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        //channel 代表了一个socket.
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ReadTimeoutHandler(1));

        /**
         * http-request解码器
         * http服务器端对request解码
         */
        pipeline.addLast("decoder", new HttpRequestDecoder(8192, 8192, 8192));
        /**
         * http-response解码器
         * http服务器端对response编码
         */
        pipeline.addLast("encoder", new HttpResponseEncoder());

        /**
         * 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
         */
        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));

        /**
         * 压缩
         */
        pipeline.addLast(new HttpContentCompressor());

        /**
         * handler分为两种，inbound handler,outbound handler,分别处理 流入，流出。
         * 服务端业务逻辑
         */
        pipeline.addLast(new HttpServerHandler());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接客户端地址：" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端" + InetAddress.getLocalHost().getHostName() + "成功与服务器建立连接");
        super.channelActive(ctx);
    }
}
