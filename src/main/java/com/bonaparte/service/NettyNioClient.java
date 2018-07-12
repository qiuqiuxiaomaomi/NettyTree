package com.bonaparte.service;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * Created by yangmingquan on 2018/7/12.
 */
public class NettyNioClient {

    public static void main(String[] args) throws SSLException, InterruptedException {
        SslContext sslContext = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);

        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            if (sslContext != null){
                                p.addLast(sslContext.newHandler(socketChannel.alloc(), "127.0.0.1", 8007));
                            }
                            p.addLast(new ObjectEncoder(), new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new NettyNioClientHandler());
                        }
                    });
            ChannelFuture f = b.connect("127.0.0.1", 8007).sync();
            f.channel().closeFuture().sync();
        }finally {
            loopGroup.shutdownGracefully();
        }
    }
}
