import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode("哈喽啊", "UTF-8");
        System.out.println(encode);
    }
}
