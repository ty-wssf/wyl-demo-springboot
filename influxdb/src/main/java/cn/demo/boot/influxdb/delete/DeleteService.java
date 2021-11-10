package cn.demo.boot.influxdb.delete;

import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wyl
 * @since 2021-11-10 17:33:50
 */
@Service
public class DeleteService {
    @Autowired
    private InfluxDBTemplate influxDBTemplate;


    public void delete() {
        Query query = new Query("delete from kline_1_day where id='2'");
        QueryResult result = influxDBTemplate.query(query);
        System.out.println(result);
    }
}
