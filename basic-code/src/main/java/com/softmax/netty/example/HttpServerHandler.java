package com.softmax.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaderNames.*;


/**
 * HTTP 请求处理类
 *
 * @author Jarvis
 * @date 2019/3/13
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String FAVICON_ICO = "/favicon.ico";

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        //HTTP客户端程序有一个实体的主体部分要发送给服务器，但希望在发送之前查看下服务器是否会
        //接受这个实体，所以在发送实体之前先发送了一个携带100 Continue的Expect请求首部的请求。
        //服务器在收到这样的请求后，应该用 100 Continue或一条错误码来进行响应。
        //在使用curl做POST的时候, 当要POST的数据大于1024字节的时候, curl并不会直接就发起POST请求, 而是会分为俩步,
        // 1. 发送一个请求, 包含一个Expect:100-continue, 询问Server使用愿意接受数据
        // 2. 接收到Server返回的100-continue应答以后, 才把数据POST给Server
        String uri = req.uri();
        if (uri.equals(FAVICON_ICO)) {
            return;
        }
        if (HttpUtil.is100ContinueExpected(req)) {
            ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
        }
        boolean keepAlive = HttpUtil.isKeepAlive(req);
        System.out.println("【method】" + req.method() + "【uri】" + req.uri());
        ByteBuf content = ctx.alloc().buffer();
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        if (!keepAlive) {
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.writeAndFlush(response);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
