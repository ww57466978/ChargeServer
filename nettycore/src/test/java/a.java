import java.math.BigDecimal;

/**
 * @Author: zhengye.zhang
 * @Description:
 * @Date: 2018/4/22 下午8:59
 */
public class a {
    public static void main(String[] args) {
        BigDecimal bigDecimal = BigDecimal.valueOf(10.12333);
        System.out.println(bigDecimal);
        System.out.println(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
