package chargedot.server.handler;


import chargedot.domain.Session;
import chargedot.protocal.DataPacket;
import chargedot.protocal.DataPacketPicker;
import chargedot.protocal.DataPacketResolver;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

/**
 * @author zhengye.zhang
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
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
            //process will read one completed packet
            DataPacket dataPacket = process(ctx, in);
            out.add(dataPacket);
    }
    /**
     *
     * @param ctx
     * @param in
     * @return
     */
    private DataPacket process(ChannelHandlerContext ctx, ByteBuf in) {
        //read with netty read api
        return dataPacketResolver.resolve(new Session(UUID.randomUUID().toString()),  in.array());
    }
}
