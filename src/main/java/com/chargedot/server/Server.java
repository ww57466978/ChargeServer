package com.chargedot.server;

import com.chargedot.domain.Session;
import com.chargedot.server.handler.DataPacketHandler;
import com.chargedot.server.handler.ProtocolDecoder;
import com.jcraft.jsch.Channel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2017/9/5.
 */
public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private static Server ourInstance = new Server();

    public static Server getInstance() {
        return ourInstance;
    }

    private Server() {
    }

    /**
     * netty boss group
     */
    private EventLoopGroup bossGroup;
    /**
     * netty worker group
     */
    private EventLoopGroup workerGroup;
    /**
     * netty server bootstrap
     */
    private ServerBootstrap bootstrap;


    private void init(){
        InboundDataPacketManager.getInstance().init();
        OutboundDataPacketManager.getInstance().init();

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(4);
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        Session session = Session.create();
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ProtocolDecoder(session));
                        pipeline.addLast(new ProtocolDecoder(session));
                        pipeline.addLast(new DataPacketHandler(session));
                    }
                })
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void run(){
        init();
        try {
            ChannelFuture channelFuture = bootstrap.bind(18701).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        log.info("server is starting...");
        Server.getInstance().run();
    }
}
