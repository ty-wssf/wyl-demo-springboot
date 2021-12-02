package cn.demo.boot.longpolling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 长轮询数据仓库
 *
 * @author wyl
 * @since 2021-09-22 17:38:54
 */
public class LongPollingDataRepo {

    private static Map<String, String> longPollingDataRepo = new ConcurrentHashMap<>();

    public static void save(String dataId, String data) {
        longPollingDataRepo.put(dataId, data);
        LongPollingService.getInstance().push(dataId, data);
    }

    public static String get(String dataId) {
        return longPollingDataRepo.get(dataId);
    }

}
