package cn.demo.boot.protostuff;

import cn.hutool.core.util.ObjectUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.io.Serializable;

/**
 * @author wyl
 * @since 2021-11-09 11:40:33
 */
public class ProtostuffTest {

    @Test
    public void test1() {
        //创建一个user对象
        User user = User.builder().id("1").age(20).name("张三").desc("programmer").build();
        Schema<User> schema = RuntimeSchema.getSchema(User.class);
        LinkedBuffer buffer = LinkedBuffer.allocate(1024);
        byte[] data = ProtobufIOUtil.toByteArray(user, schema, buffer);
        System.out.println(data.length);
        System.out.println(ObjectUtil.serialize(user).length);
    }

}

@Data
@Builder
class User implements Serializable {
    private String id;

    private String name;

    private Integer age;

    private String desc;
}