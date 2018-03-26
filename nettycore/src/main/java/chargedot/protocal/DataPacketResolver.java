package chargedot.protocal;


import chargedot.domain.Session;

/**
 * Created by Administrator on 2017/9/7.
 */
public class DataPacketResolver {

    public DataPacket resolve(Session session, byte[] data){
        return  new DataPacket() {
            @Override
            public void process() {

            }

            @Override
            public void processComplete() {

            }
        };
    }
}
