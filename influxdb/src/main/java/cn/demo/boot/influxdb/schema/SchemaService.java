package cn.demo.boot.influxdb.schema;

import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wyl
 * @since 2021-11-10 17:26:45
 */
@Service
public class SchemaService {
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    public void createDatabase() {
        influxDBTemplate.createDatabase();
    }
}

