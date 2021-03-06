package com.lucifiere.blogmaker.controller


import com.lucifiere.blogmaker.service.ArticleServiceImpl
import com.lucifiere.blogmaker.utils.PageUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("tech")
class TechBlogController {

    @Autowired
    private ArticleServiceImpl articleService

    @RequestMapping("index")
    ModelAndView index(String navType, Integer pageNo) {
        ModelAndView mv = new ModelAndView("tech/index")
        List digestList = articleService.getDigestList(navType)
        mv.addObject("digestList", PageUtils.paging(digestList, pageNo))
        mv.addObject("categoryList", articleService.getCategoryList(navType))
        mv.addObject("navType", navType)
        mv.addObject("pageList", PageUtils.pageList(navType, digestList.size()))
        mv
    }

    @RequestMapping("article")
    ModelAndView article(String id) {
        ModelAndView mv = new ModelAndView("tech/blog")
        mv.addObject("a", articleService.getArticle(id))
        mv
    }

}
