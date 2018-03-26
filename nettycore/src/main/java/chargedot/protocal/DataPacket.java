package chargedot.protocal;


import chargedot.domain.Session;

/**
 * @author zhengye.zhang
 */
public abstract class DataPacket {
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public abstract void process();

    public abstract void processComplete();
}
