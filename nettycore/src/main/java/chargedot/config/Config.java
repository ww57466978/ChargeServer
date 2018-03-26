package chargedot.config;

/**
 * Created by Administrator on 2017/9/8.
 */
public class Config {
    public static int port;

    /**
     * kafka bootstrap servers
     */
    public static String bootstrapServers;

    /**
     * kafka zookeeper connect
     */
    public static String zookeeperConnect;

    /**
     * kafka key serializer
     */
    public static String keySerializer;

    /**
     * kafka value serializer
     */
    public static String valueSerializer;
    /**
     * kafka group id
     */
    public static String groupId;

    /**
     * business database host
     */
    public static String businessDBHost;

    /**
     * business database port
     */
    public static String businessDBPort;

    /**
     * business database name
     */
    public static String businessDBName;

    /**
     * business database user
     */
    public static String businessDBUser;

    /**
     * business database pass
     */
    public static String businessDBPass;
}
