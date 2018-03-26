package chargedot.domain;

import chargedot.protocal.DataPacket;
import chargedot.server.InboundDataPacketManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.Random;

/**
 * @author zhengye.zhang
 */
public class Session {

    public final static int INITIAL = 0;
    public final static int READY = 1;
    public final static int ACTIVE = 2;
    public final static int INACTIVE = 3;
    public final static int TIMEOUT = 4;

    private final static Random RAND = new Random();

    private ChannelHandlerContext context;

    private int status;

    private String id;

    private long readyAt;

    private long activeAt;

    private long inactiveAt;

    private long timeoutAt;

    private long refreshedAt;

    private byte serverRandomNumber;

    private String deviceNumber;

    public Session(String id) {
        this.id = id;
        status = INITIAL;
        refreshedAt = System.currentTimeMillis();
        serverRandomNumber = (byte) RAND.nextInt(255);
    }

    /**
     * create a new session
     *
     * @return
     */
    public static Session create() {
        return new Session(SessionIdGenerator.getInstance().generate());
    }

    /**
     * is time out
     *
     * @return
     */
    public boolean isTimeout() {
        if (status == TIMEOUT || status == INACTIVE) {
            return false;
        }
        long now = System.currentTimeMillis();
        if (now > refreshedAt + 30000) {
            return true;
        }
        return false;
    }

    /**
     * is active
     *
     * @return
     */
    public boolean isActive() {
        if (status == ACTIVE) {
            return true;
        }
        return false;
    }

    /**
     * session ready
     *
     * @param ctx
     */
    public void ready(ChannelHandlerContext ctx) {
        context = ctx;
        readyAt = System.currentTimeMillis();
        status = READY;
    }


    public void receive(Session session, DataPacket packet) {
        synchronized (session) {
            session.refresh();
            packet.setSession(session);
            InboundDataPacketManager.getInstance().put(packet);
        }
    }

    /**
     * session active
     *
     * @param deviceNumber
     */
    public void active(String deviceNumber, String port) {
        if (status == ACTIVE) {
            return;
        }
        activeAt = System.currentTimeMillis();
        status = ACTIVE;
        this.deviceNumber = deviceNumber;

    }

    /**
     * session inactive
     */
    public void inactive() {
        if (status == INACTIVE) {
            return;
        }
        inactiveAt = System.currentTimeMillis();
        status = INACTIVE;
        if (context != null) {
            context.close();
            context = null;
        }
    }

    /**
     * session timeout
     */
    public void timeout() {
        if (status == TIMEOUT) {
            return;
        }
        timeoutAt = System.currentTimeMillis();
        status = TIMEOUT;
    }

    /**
     * session refresh
     */
    public void refresh() {
        refreshedAt = System.currentTimeMillis();
    }

    /**
     * send data packet
     *
     * @param packet
     */
    public void send(DataPacket packet) {
        if (context != null) {
            context.writeAndFlush(packet);
        }
    }
}
