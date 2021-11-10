package cn.demo.boot.influxdb.update;

import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

/**
 * @author wyl
 * @since 2021-11-10 17:31:15
 */
@Service
public class UpdateService {

    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    public static BigDecimal formatDecimal(double num) {
        return new BigDecimal(num).setScale(8, RoundingMode.CEILING);
    }

    /**
     * 插入一个tag完全相同, 以及时间戳也完全相同, 就表示覆盖数据
     */
    public void update() {
        Point point = Point.measurement("kline_1_day").tag("id", "3").time(1547078400000L, TimeUnit.MILLISECONDS)
                .addField("open", formatDecimal(1.213)).addField("close", formatDecimal(1.32))
                .addField("high", formatDecimal(1.52143132424)).addField("low", formatDecimal(1.000123))
                .addField("amount", formatDecimal(200)).addField("volume", formatDecimal(200)).build();
        influxDBTemplate.write(point);

        Query query = new Query("select * from kline_1_day where id='3'", "hhui");
        QueryResult result = influxDBTemplate.query(query);
        System.out.println(result);
    }

}
