package com.lucifiere.blogmaker.utils

import com.alibaba.fastjson.JSON
import com.lucifiere.blogmaker.entity.Article
import com.lucifiere.blogmaker.entity.Digest
import org.apache.commons.collections4.CollectionUtils

import java.util.stream.Collectors

class BlogReader {

    private BlogReader() {
        init()
    }

    private static BlogReader INST

    static BlogReader inst() {
        if (Objects.isNull(INST)) {
            synchronized (BlogReader) {
                if (Objects.isNull(INST)) {
                    INST = new BlogReader()
                }
            }
        }
        INST
    }

    private static final ROOT_DIR = "./src/main/resources/blog"

    private static Map<String, List<Article>> NAV_MAP

    private static Map<String, Article> ID_MAP

    private static void init() {
        def articles = []
        new File(ROOT_DIR).eachFile {
            articles << JSON.parseObject(it.bytes, Article.class)
        }
        preHandleArticle(articles)
        NAV_MAP = MapUtils.multiGroupByFiled(articles, { k -> (k as Article).getNavType() })
        ID_MAP = MapUtils.singleGroupByFiled(articles, { k -> (k as Article).getId() })
    }

    private static void preHandleArticle(List<Article> articles) {
        articles.forEach {
            it.getPhases().each {
                it.setSegment(it.segment.stream().map({
                    def ind = "&nbsp;&nbsp;&nbsp;"
                    ind + it
                }).collect(Collectors.toList()))
            }
        }
    }

    List<Article> getByNavType(String nav) {
        NAV_MAP.get(nav)
    }

    Article getById(String id) {
        ID_MAP.get(id)
    }

    List<Digest> getDigest(String nav) {
        List<Article> articles = getByNavType(nav)
        if (CollectionUtils.isEmpty(articles)) return []
        articles.stream().filter({ it.getNavType() == nav }).map({ it -> it.getDigest() }).collect(Collectors.toList())
    }

}
