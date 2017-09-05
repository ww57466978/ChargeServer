package com.chargedot.server;

import com.chargedot.protocal.DataPacket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2017/9/5.
 * 处理Server返回给clinet的DataPacket
 */
public class OutboundDataPacketManager {
    private static OutboundDataPacketManager ourInstance = new OutboundDataPacketManager();

    public static OutboundDataPacketManager getInstance() {
        return ourInstance;
    }

    private OutboundDataPacketManager() {
    }

    private BlockingQueue<DataPacket> queue;

    public void init(){
        queue = new ArrayBlockingQueue<DataPacket>(100);
        for (int i =0 ; i < 3; i++){

        }
    }

    public void put(DataPacket dataPacket){
        try {
            queue.put(dataPacket);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
