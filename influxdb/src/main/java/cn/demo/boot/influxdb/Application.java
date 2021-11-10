package cn.demo.boot.influxdb;

import cn.demo.boot.influxdb.delete.DeleteService;
import cn.demo.boot.influxdb.insert.InsertService;
import cn.demo.boot.influxdb.query.QueryService;
import cn.demo.boot.influxdb.schema.SchemaService;
import cn.demo.boot.influxdb.update.UpdateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wyl
 * @since 2021-11-10 17:18:19
 */
@SpringBootApplication
public class Application {

    public Application(SchemaService schemaService, InsertService insertService,
                       UpdateService updateService, QueryService queryService, DeleteService deleteService) {
        // 创建数据库
        //schemaService.createDatabase();
        // 插入数据
        //insertService.time();
        // 查询
        // queryService.query();
        // 更新
        // updateService.update();
        // 删除
        deleteService.delete();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
