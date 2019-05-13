package com.lucifiere.blogmaker.utils

import com.lucifiere.blogmaker.entity.Page

import java.util.stream.Collectors

final class PageUtils {

    static List<Page> pageList(String navType) {
        Map indexPageMap = [
                BLOG  : "blog-index",
                DIGEST: "digest-index",
                NOTE  : "note-index",
                FAQ   : "faq-index"
        ]
        (1..1).toList().stream().map { it ->
            def page = new Page()
            page.setPageNo(it as String)
            page.setPageUrl("""/page/tech/${indexPageMap.get(navType)}-""" + it)
            page
        }.collect(Collectors.toList())
    }

}
