package chargedot.server;

import chargedot.domain.Session;
import chargedot.protocal.DataPacketPicker;
import chargedot.protocal.DataPacketResolver;
import chargedot.server.handler.DataPacketHandler;
import chargedot.server.handler.ProtocolDecoder;
import chargedot.server.handler.ProtocolEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhengye.zhang
 */
public class Server {
    private static final int NET_PORT = 18701;

    private static final int WORK_THREAD_COUNT = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static Server ourInstance = new Server();
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

    private Server() {
    }

    private static Server getInstance() {
        return ourInstance;
    }

    private void init() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(WORK_THREAD_COUNT);
        bootstrap = new ServerBootstrap();
        final DataPacketResolver dataPacketResolver = new DataPacketResolver();
        final DataPacketPicker dataPacketPicker = new DataPacketPicker();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        Session session = Session.create();
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ProtocolDecoder(session, dataPacketPicker, dataPacketResolver));
                        pipeline.addLast(new ProtocolEncoder(session));
                        pipeline.addLast(new DataPacketHandler(session));
                    }
                })
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
    }

    private void run() {
        init();
        try {
            ChannelFuture channelFuture = bootstrap.bind(NET_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("server is stop ... ", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        LOGGER.info("server is starting...  ");
        Server.getInstance().run();
    }
}
