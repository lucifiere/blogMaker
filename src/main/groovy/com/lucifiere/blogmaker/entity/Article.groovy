package com.lucifiere.blogmaker.entity

import lombok.Data

@Data
class Article {

    String id

    String title

    Digest digest

    List<Phase> phases

    String navType

}
