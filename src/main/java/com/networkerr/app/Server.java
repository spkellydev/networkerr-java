package com.networkerr.app;

import com.networkerr.app.handlers.NetworkerrServerHandler;
import com.networkerr.core.database.*;
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
        System.out.println("Database connected");
        MySQLORM db = MySQLORM.getInstance();
        db.connect("networkerr", "root", "");
        MySqlWriter writer = new MySqlWriter();
        String tableSql = writer.createTableBegin("companies")
                .createTableColumn("CompanyId", false, SQLTypes.INTEGER, SQLFlags.NOT_NULL, SQLFlags.AUTO_INCREMENT, SQLFlags.PRIMARY_KEY)
                .createTableColumn("CompanyDomain", false, SQLTypes.VARCHAR32)
                .createTableColumn("CompanyName", false, SQLTypes.VARCHAR64)
                .createTableColumn("CompanyProfileId", true, SQLTypes.INTEGER, SQLFlags.NOT_NULL)
                .createForeignKey("CompanyProfileId", "CompanyProfiles", "CompanyProfileId", SQLForeignKeyFlags.DELETE_CASCADE)
                .createTableEnd()
                .getTableStatement();
        db.execute(tableSql);
        System.out.println(tableSql);
        new Server().run();
    }

    public void run() {
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
                                .addLast(new NetworkerrServerHandler());
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
