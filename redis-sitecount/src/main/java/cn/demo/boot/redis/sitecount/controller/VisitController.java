package cn.demo.boot.redis.sitecount.controller;

import cn.demo.boot.redis.sitecount.model.VisitReqDTO;
import cn.demo.boot.redis.sitecount.service.SiteVisitFacade;
import cn.demo.boot.redis.sitecount.vo.SiteVisitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VisitController {
    @Autowired
    private SiteVisitFacade siteVisitFacade;

    @RequestMapping(path = "visit")
    @ResponseBody
    public SiteVisitDTO visit(VisitReqDTO reqDTO) {
        return siteVisitFacade.visit(reqDTO);
    }

}
