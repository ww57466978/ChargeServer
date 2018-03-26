package chargedot.server;


import chargedot.protocal.DataPacket;

import java.util.concurrent.BlockingQueue;

/**
 * @author zhengye.zhang
 */
public class InboundProcess implements Runnable {

    private BlockingQueue<DataPacket> queue;

    public InboundProcess(BlockingQueue<DataPacket> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            DataPacket packet = queue.take();
            packet.process();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
