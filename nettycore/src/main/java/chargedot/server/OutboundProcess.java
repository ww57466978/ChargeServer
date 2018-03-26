package chargedot.server;

import chargedot.protocal.DataPacket;

import java.util.concurrent.BlockingQueue;

/**
 * @author zhengye.zhang
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
