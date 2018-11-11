package com.networkerr.app;

import com.networkerr.app.handlers.NetworkerrServerHandler;
import com.networkerr.core.routers.Router;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;

public class Server {
    public static void main(String[] args) {
        System.out.println("Server running on port 8080");
        Router router = new Router();
        new Server().run(router);
    }

    public void run(Router router) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                .addFirst("idleStateHandler", new IdleStateHandler(60, 30, 0))
                                .addLast(new HttpServerCodec())
                                .addLast("aggregator", new HttpObjectAggregator(Short.MAX_VALUE))
                                .addLast(new NetworkerrServerHandler(router));
                        }
                    });
            // Bind and accept incoming connections;
            ChannelFuture f = b.bind(8080).sync();
            // Wait until socket has closed
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
