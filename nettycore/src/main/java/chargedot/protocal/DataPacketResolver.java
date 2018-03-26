package chargedot.protocal;


import chargedot.domain.Session;

/**
 * @author zhengye.zhang
 */
public class DataPacketResolver {

    public DataPacket resolve(Session session, byte[] data) {
        return new DataPacket() {
            @Override
            public void process() {
                //do sth.
            }

            @Override
            public void processComplete() {
                //do sth.
            }
        };
    }
}
