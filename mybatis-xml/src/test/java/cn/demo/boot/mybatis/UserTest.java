package cn.demo.boot.mybatis;

import cn.demo.boot.mybatis.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wyl
 * @since 2021-11-05 17:01:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void testDeleteByPrimaryKey() {
        sysUserMapper.deleteByPrimaryKey(1L);
    }

    @Test
    public void testSelectByPrimaryKey() {
        sysUserMapper.selectByPrimaryKey(2L);
    }

    /**
     * https://mp.baomidou.com/guide/mybatisx-idea-plugin.html#%E5%8A%9F%E8%83%BD
     * 测试通过 MybatisX 插件生成的方法
     */
    @Test
    public void testGetByNickName() {
        sysUserMapper.getByNickName("管理员");
    }

}
