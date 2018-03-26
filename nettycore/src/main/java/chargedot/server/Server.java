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

    private static final Logger log = LoggerFactory.getLogger(Server.class);

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
        workerGroup = new NioEventLoopGroup(4);
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
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    private void run() {
        init();
        try {
            ChannelFuture channelFuture = bootstrap.bind(18701).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        log.info("server is starting... ");
        Server.getInstance().run();
    }
}
