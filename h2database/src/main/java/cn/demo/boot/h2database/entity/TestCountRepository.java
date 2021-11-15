package cn.demo.boot.h2database.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wyl
 * @since 2021-11-15 14:48:25
 */
@Repository
public interface TestCountRepository extends CrudRepository<TestCountEntity, Integer> {
}
