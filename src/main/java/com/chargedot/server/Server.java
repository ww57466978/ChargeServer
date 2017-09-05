package com.chargedot.server;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2017/9/5.
 */
public class Server {
    private static Server ourInstance = new Server();

    public static Server getInstance() {
        return ourInstance;
    }

    private Server() {
    }

    private void init(){
        InboundDataPacketManager.getInstance().init();
        OutboundDataPacketManager.getInstance().init();
    }


}
