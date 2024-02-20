package demo.et59.solr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/01 15:36
 */
@Controller
public class IndexController {

    /**
     * 数据库搜索首页
     * @return
     */
    @RequestMapping("")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("type","db");
        return mav;
    }

    /**
     * 文档搜索首页
     * @return
     */
    @RequestMapping("/doc")
    public ModelAndView docIndex(){
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("type","file");
        return mav;
    }

}
