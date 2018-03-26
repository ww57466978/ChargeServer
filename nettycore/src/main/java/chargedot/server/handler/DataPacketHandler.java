package chargedot.server.handler;

import chargedot.domain.Session;
import chargedot.protocal.DataPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/9/5.
 */
public class DataPacketHandler extends ChannelInboundHandlerAdapter {

    private Session session;

    public DataPacketHandler(Session session) {
        this.session = session;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       session.ready(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        session.inactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        session.receive(session, (DataPacket) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
