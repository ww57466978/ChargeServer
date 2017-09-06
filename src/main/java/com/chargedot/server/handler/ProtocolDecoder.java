package com.chargedot.server.handler;

import com.chargedot.domain.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public class ProtocolDecoder extends ByteToMessageDecoder {

    private Session session;

    public ProtocolDecoder(Session session){
        this.session = session;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
