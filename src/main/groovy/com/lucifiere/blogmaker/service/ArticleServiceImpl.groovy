package com.lucifiere.blogmaker.service

import com.alibaba.fastjson.JSON
import com.google.common.collect.Lists
import com.lucifiere.blogmaker.constants.NavEnum
import com.lucifiere.blogmaker.entity.Article
import com.lucifiere.blogmaker.entity.Category
import com.lucifiere.blogmaker.entity.Digest
import com.lucifiere.blogmaker.entity.Phase
import com.lucifiere.blogmaker.utils.BlogReader
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl implements ArticleService {

    public static final BlogReader BLOG_READER = BlogReader.inst()

    List<Article> getArticleList(String nav) {
        BLOG_READER.getByNavType(nav)
    }

    List<Category> getCategoryList(String nav) {
        []
    }

    List<Digest> getDigestList(String nav) {
        BLOG_READER.getDigest(nav)
    }

    Article getArticle(String id) {
        BLOG_READER.getById(id)
    }

    static void main(String[] args) {
        System.out.println(JSON.toJSONString(article()))
    }

    private static Article article() {
        def a = new Article()
        a.setTitle("文章标题")
        a.setDigest(digest())
        a.setPhases(Lists.newArrayList(phase(), phase()))
        a.setNavType(NavEnum.NOTE.name())
        a
    }

    private static Phase phase() {
        def p = new Phase()
        p.setTitle("段落")
        p.setSegment(["妃嫔媵嫱，王子皇孙，辞楼下殿，辇来于秦，朝歌夜弦，为秦宫人。明星荧荧，开妆镜也；绿云扰扰，梳晓鬟也；渭流涨腻，弃脂水也；烟斜雾横，焚椒兰也。雷霆乍惊，宫车过也；辘辘远听，杳不知其所之也。一肌一容，尽态极妍，缦立远视，而望幸焉；有不得见者，三十六年。"])
        p
    }

    private static Digest digest() {
        def d = new Digest()
        d.setTitle("标题")
        d.setPicUrl("http://img.mp.itc.cn/upload/20161129/309082ea1b5a484bac7186fbcfde3060_th.jpg")
        d.setContent("须臾客去，予亦就睡。梦一道士，羽衣蹁跹，过临皋之下，揖予而言曰：“赤壁之游乐乎？”问其姓名，俯而不答。“呜呼！噫嘻！我知之矣。畴昔之夜，飞鸣而过我者，非子也邪？”道士顾笑，予亦惊寤。开户视之，不见其处。")
        d.setArticleUrl("/index.html")
        d.setTag("Java")
        d
    }

    private static Category category() {
        def c = new Category()
        c.setTitle("标题")
        c.setUrl("http://img.mp.itc.cn/upload/20161129/309082ea1b5a484bac7186fbcfde3060_th.jpg")
        c
    }

}
