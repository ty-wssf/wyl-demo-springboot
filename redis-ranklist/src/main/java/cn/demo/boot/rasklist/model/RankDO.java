package cn.demo.boot.rasklist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wyl
 * @since 2021-11-04 14:08:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankDO implements Serializable {
    private static final long serialVersionUID = 4804922606006935590L;

    /**
     * 排名
     */
    private Long rank;

    /**
     * 积分
     */
    private Float score;

    /**
     * 用户id
     */
    private Long userId;
}
