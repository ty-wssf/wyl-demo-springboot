package cn.demo.boot.mybatis.mapper;
import cn.demo.boot.mybatis.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cn.demo.boot.mybatis.domain.SysUser
 */
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> getByNickName(@Param("nickName") String nickName);

}
