package cn.demo.boot.h2database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wyl
 * @since 2021-11-15 14:46:20
 */
@Entity
@Table(name = "image_count")
public class TestCountEntity {

    @Id
    private Integer id;
    @Column
    private Long count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long name) {
        this.count = name;
    }

}
