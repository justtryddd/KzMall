import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @date 2023/3/20 - 18:33
 */

public class test {
    @Test
    public static void main(String[] args) {
        String i = "1";
        String[] split = i.split(",");
        for (String s : split) {
            System.out.print(s);
        }
    }

}
