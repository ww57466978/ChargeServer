package chargedot.server;


import chargedot.domain.Session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhengye.zhang
 */
public class SessionManager {
    private static SessionManager ourInstance = new SessionManager();
    private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>(1000);

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return ourInstance;
    }


}
