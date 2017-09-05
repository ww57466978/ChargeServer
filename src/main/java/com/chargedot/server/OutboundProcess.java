package com.chargedot.server;

import com.chargedot.protocal.DataPacket;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2017/9/5.
 */
public class OutboundProcess implements Runnable {

    private BlockingQueue<DataPacket> queue;

    public OutboundProcess(BlockingQueue<DataPacket> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            DataPacket dataPacket = queue.take();
            dataPacket.process();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
