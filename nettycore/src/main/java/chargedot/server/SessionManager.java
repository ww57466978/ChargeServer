package chargedot.server;


import chargedot.domain.Session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/9/6.
 */
public class SessionManager {
    private static SessionManager ourInstance = new SessionManager();

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
    }

    private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>(1000);


}
