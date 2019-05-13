package com.lucifiere.blogmaker.utils


import com.lucifiere.blogmaker.entity.Page

import java.util.stream.Collectors

final class PageUtils {

    static final int PAGE_SIZE = 20

    static List<Page> pageList(String navType, int pageCount) {
        Map indexPageMap = [
                BLOG  : "blog-index",
                DIGEST: "digest-index",
                NOTE  : "note-index",
                FAQ   : "faq-index"
        ]
        int pageNo = (pageCount / PageUtils.PAGE_SIZE + 1) as Integer
        (1..pageNo).toList().stream().map { it ->
            def page = new Page()
            page.setPageNo(it as String)
            page.setPageUrl("""/page/tech/${indexPageMap.get(navType)}-""" + it)
            page
        }.collect(Collectors.toList())
    }

    static <T> List<T> paging(List<T> digests, Integer pageNo) {
        if(pageNo == null) return digests
        int s = (pageNo - 1) * PAGE_SIZE
        int e = s + PAGE_SIZE
        digests[s..e]
    }

}
