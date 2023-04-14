package com.omnilab.templatekotlin.config

import org.sitemesh.builder.SiteMeshFilterBuilder
import org.sitemesh.config.ConfigurableSiteMeshFilter
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle

class SiteMeshFilter : ConfigurableSiteMeshFilter() {

    private val INDEX: String = "/WEB-INF/layout/main/decorator-index.jsp"
    private val MAINHEADER: String = "/WEB-INF/layout/main/decorator-main-header.jsp"

    override fun applyCustomConfiguration(builder: SiteMeshFilterBuilder) {
        super.applyCustomConfiguration(builder)
        builder.addTagRuleBundle(Sm2TagRuleBundle())
        builder.addDecoratorPath("/index.mi", INDEX)
                .addDecoratorPath("/", INDEX)
                .addDecoratorPath("/security.mi", INDEX)
                .addDecoratorPath("/common/*", MAINHEADER)
                .addDecoratorPath("/m01/*", MAINHEADER)
                .addDecoratorPath("/m02/*", MAINHEADER)
                .addDecoratorPath("/m03/*", MAINHEADER)
                .addDecoratorPath("/m04/*", MAINHEADER)
                .addDecoratorPath("/m05/*", MAINHEADER)
                .addDecoratorPath("/pop/*", MAINHEADER)
                .addDecoratorPath("/m01/*.pop", "/WEB-INF/layout/main/decorator-main-noheader.jsp")
                .addDecoratorPath("/certification.mi", INDEX)
                .addExcludedPath("/html/*")
                .addExcludedPath("/js/*")
                .addExcludedPath("/inc/*")
                .addExcludedPath("*.ajax")
                .addExcludedPath("/thumbnail/*")
                .addExcludedPath("/temp/*")
                .addExcludedPath("*.json")
    }
}