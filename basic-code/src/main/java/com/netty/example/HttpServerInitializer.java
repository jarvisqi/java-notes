package com.netty.example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;


/**
 * 自定义实现的一个服务初始化器
 *
 * @author Jarvis
 * @date 2019/3/13
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
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
         * 压缩
         */
        pipeline.addLast(new HttpContentCompressor());

        //handler分为两种，inbound handler,outbound handler,分别处理 流入，流出。
        pipeline.addLast(new HttpServerHandler());

    }
}
