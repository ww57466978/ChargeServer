package chargedot.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/5.
 */
public class SessionIdGenerator {
    private final static SimpleDateFormat TS = new SimpleDateFormat("yyMMddHHmmss");

    /**
     * count
     */
    private int cnt;
    /**
     * minute
     */
    private long minute;

    /**
     * instance
     */
    private static SessionIdGenerator instance = new SessionIdGenerator();

    /**
     *
     */
    private SessionIdGenerator() {
    }

    /**
     * get instance
     * @return
     */
    public static SessionIdGenerator getInstance() {
        return instance;
    }

    /**
     * generate an id
     * @return
     */
    public synchronized String generate() {
        long now = System.currentTimeMillis();
        long m = (long) (now / 1000 / 60 * 60 * 1000);
        if (minute < m) {
            minute = m;
            cnt = 0;
        }
        cnt++;
        return TS.format(new Date(minute)) + cnt;
    }
}
