import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @author wyl
 * @since 2021-11-04 14:14:25
 */
public class RankInitTest {

    private Random random;
    private RestTemplate restTemplate;

    @Before
    public void init() {
        random = new Random();
        restTemplate = new RestTemplate();
    }

    private int genUserId() {
        return random.nextInt(1024);
    }

    private double genScore() {
        return random.nextDouble() * 100;
    }

    @Test
    public void testInitRank() {
        try {
            for (int i = 0; i < 30; i++) {
                restTemplate.getForObject("http://localhost:9080/update?userId=" + genUserId() + "&score=" + genScore(),
                        String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
