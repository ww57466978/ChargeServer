package chargedot.server.handler;


import chargedot.domain.Session;
import chargedot.protocal.DataPacketPicker;
import chargedot.protocal.DataPacketResolver;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public class ProtocolDecoder extends ByteToMessageDecoder {

    private Session session;

    private DataPacketPicker dataPacketPicker;

    private DataPacketResolver dataPacketResolver;

    public ProtocolDecoder(Session session, DataPacketPicker dataPacketPicker, DataPacketResolver dataPacketResolver) {
        this.session = session;
        this.dataPacketPicker = dataPacketPicker;
        this.dataPacketResolver = dataPacketResolver;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
