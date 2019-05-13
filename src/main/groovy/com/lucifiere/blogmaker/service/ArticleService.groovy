package com.lucifiere.blogmaker.service


import com.lucifiere.blogmaker.entity.Article
import com.lucifiere.blogmaker.entity.Category
import com.lucifiere.blogmaker.entity.Digest

interface ArticleService {

    List<Article> getArticleList(String nav)

    List<Category> getCategoryList(String nav)

    List<Digest> getDigestList(String nav)

    Article getArticle(String id)

}