package cn.demo.boot.rasklist.controller;

import cn.demo.boot.rasklist.model.RankDO;
import cn.demo.boot.rasklist.service.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wyl
 * @since 2021-11-04 14:11:13
 */
@RestController
public class RankController {
    @Autowired
    private RankListService rankListComponent;

    @GetMapping(path = "/topn")
    public List<RankDO> showTopN(int n) {
        return rankListComponent.getTopNRanks(n);
    }

    @GetMapping(path = "/update")
    public RankDO updateScore(long userId, float score) {
        return rankListComponent.updateRank(userId, score);
    }

    @GetMapping(path = "/rank")
    public RankDO queryRank(long userId) {
        return rankListComponent.getRank(userId);
    }

    @GetMapping(path = "/around")
    public List<RankDO> around(long userId, int n) {
        return rankListComponent.getRankAroundUser(userId, n);
    }

}
