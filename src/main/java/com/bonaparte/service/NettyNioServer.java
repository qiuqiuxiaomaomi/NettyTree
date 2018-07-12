package com.bonaparte.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Created by yangmingquan on 2018/7/12.
 * Netty Server端
 * 基于NIO模式的服務器端
 */
public class NettyNioServer {
    public static void main(String[] args) throws CertificateException, SSLException, InterruptedException {
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        SslContext sslContext = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());

        //EventLoopGroup Netty实现的线程池接口
        //处理客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //处理IO事件
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //ServerBootstrap 启动服务器端程序，Bootstrap 启动客户端程序
            ServerBootstrap b = new ServerBootstrap();
            //group用于指定一个或者多个reactor，也就是EventLoopGroup实例
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //指定TCP的参数等
                    .option(ChannelOption.SO_BACKLOG, 100)
                    //连接Reactor的处理器，默认情况可不指定，已经添加
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //IO处理Reactor的处理器，
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel){
                            //在ChannelPipeline中使用handler来处理业务逻辑
                            //ChannelPipeline与ChannelHandler的关系类似于Servelet与Filter的关系，
                            //这类拦截器实际上是职责链模式的一种变形，主要为了方便事件的拦截和用户业务逻辑的定制
                            ChannelPipeline p = socketChannel.pipeline();
                            if (sslContext != null){
                                p.addLast(sslContext.newHandler(socketChannel.alloc()));
                            }
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new NettyNioServerHandler());
                        }
                    });
            //将服务端Channel绑定到本地端口，接收客户端的连接
            ChannelFuture f = b.bind(8007).sync();

            //阻塞操作，等到服务器端接收客户端连接的channel关闭时才执行连接Reactor, IO处理Reactor关闭
            f.channel().closeFuture().sync();
        }finally {
            //Netty中的优雅退出
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
