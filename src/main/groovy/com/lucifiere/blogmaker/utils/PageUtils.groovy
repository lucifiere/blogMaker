package com.lucifiere.blogmaker.utils

import com.lucifiere.blogmaker.entity.Page

import java.util.stream.Collectors

final class PageUtils {

    static List<Page> pageList() {
        (1..1).toList().stream().map { it ->
            def page = new Page()
            page.setPageNo(it as String)
            page.setPageUrl("/index" + it)
            page
        }.collect(Collectors.toList())
    }

}
