package chargedot.server;


import chargedot.protocal.DataPacket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2017/9/5.
 * 处理client发送给server的DataPacket
 */
public class InboundDataPacketManager {
    private static InboundDataPacketManager ourInstance = new InboundDataPacketManager();

    public static InboundDataPacketManager getInstance() {
        return ourInstance;
    }

    private InboundDataPacketManager() {
    }

    private BlockingQueue<DataPacket> queue;

    private ExecutorService inboundThreadPool;

    public void init(){
        queue = new ArrayBlockingQueue<DataPacket>(1000);
        for (int i = 0; i < 3 ; i++){
            inboundThreadPool.execute(new InboundProcess((queue)));
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
