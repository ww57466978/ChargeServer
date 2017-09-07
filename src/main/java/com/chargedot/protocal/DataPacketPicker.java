package com.chargedot.protocal;

import com.chargedot.domain.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
public class DataPacketPicker {

    public byte[] pickup(Session session, ByteBuf in)  {
        byte[] data = null;
        return data;
    }

}
