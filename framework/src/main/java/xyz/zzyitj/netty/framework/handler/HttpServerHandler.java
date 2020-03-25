package xyz.zzyitj.netty.framework.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import xyz.zzyitj.netty.framework.server.HttpDispatcher;


/**
 * @author intent
 * @version 1.
 * @date 2020/3/24 4:33 下午
 * @email zzy.main@gmail.com
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {

    private HttpDispatcher dispatcher;

    public HttpServerHandler(HttpDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        dispatcher.doDispatch(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
