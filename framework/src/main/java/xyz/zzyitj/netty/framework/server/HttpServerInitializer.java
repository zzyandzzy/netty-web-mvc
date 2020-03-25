package xyz.zzyitj.netty.framework.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import xyz.zzyitj.netty.framework.handler.HttpServerHandler;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/232 下午
 * @email zzy.main@gmail.com
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private final HttpDispatcher dispatcher;

    public HttpServerInitializer(SslContext sslCtx, HttpDispatcher dispatcher) {
        this.sslCtx = sslCtx;
        this.dispatcher = dispatcher;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpRequestDecoder());
        // Uncomment the following line if you don't want to handle HttpChunks.
        //p.addLast(new HttpObjectAggregator(10576));
        p.addLast(new HttpResponseEncoder());
        // Remove the following line if you don't want automatic content compression.
        //p.addLast(new HttpContentCompressor());
        p.addLast(new HttpServerHandler(dispatcher));
    }
}
