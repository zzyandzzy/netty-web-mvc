package xyz.zzyitj.netty.framework.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import xyz.zzyitj.netty.framework.util.FrameworkUtils;

import java.net.InetSocketAddress;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 4:30 下午
 * @email zzy.main@gmail.com
 */
public class HttpServer implements NettyService {
    public static InetSocketAddress localAddress;

    public void start() throws Exception {
        HttpDispatcher dispatcher = new HttpDispatcher();
        localAddress = new InetSocketAddress(dispatcher.getHost(), dispatcher.getPort());
        dispatcher.initHandlerMapping();
        System.err.println("netty http mvc framework is init.");
        // Configure SSL.
        final SslContext sslCtx;
        if (dispatcher.isSsl()) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer(sslCtx, dispatcher));
            Channel ch = b.bind(localAddress).sync().channel();
            System.err.println("Open your web browser and navigate to " +
                    FrameworkUtils.getWebUrl(localAddress, dispatcher.isSsl()));
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
