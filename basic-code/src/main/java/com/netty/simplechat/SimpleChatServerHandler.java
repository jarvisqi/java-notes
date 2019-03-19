package com.netty.simplechat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * handler 是由 Netty 生成用来处理 I/O 事件的
 *
 * @author Jarvis
 * @date 2018/9/19
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

    private final static ChannelGroup CHANNELS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 每当从服务端收到新的客户端连接时，客户端的 Channel 存入 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        CHANNELS.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "加入\n");
        CHANNELS.add(ctx.channel());
    }

    /**
     * 每当从服务端收到客户端断开时，客户端的 Channel 自动从 ChannelGroup 列表中移除了，并通知列表中的其他客户端 Channel
     *
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        CHANNELS.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "离开\n");
    }

    /**
     * 服务端监听到客户端活动
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "在线");
    }

    /**
     * 服务端监听到客户端不活动
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "离线");
    }

    /**
     * 接收消息
     * 每当从服务端读到客户端写入信息时，将信息转发给其他客户端的 Channel
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : CHANNELS) {
            if (channel != incoming) {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "] " + msg + "\n");
            } else {
                channel.writeAndFlush("[you]" + msg + "\n");
            }
        }
    }

    /**
     * 发生异常，关闭 ChannelHandlerContext
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
