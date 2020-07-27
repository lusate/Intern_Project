package com.omnilab.template_kotlin.config

import org.sitemesh.builder.SiteMeshFilterBuilder
import org.sitemesh.config.ConfigurableSiteMeshFilter
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle

class SiteMeshFilter : ConfigurableSiteMeshFilter() {

    override fun applyCustomConfiguration(builder: SiteMeshFilterBuilder?) {
        super.applyCustomConfiguration(builder)
        builder?.addTagRuleBundle(Sm2TagRuleBundle())
        builder?.addDecoratorPath("/index.mi", "/WEB-INF/layout/main/decorator-index.jsp")
                ?.addDecoratorPath("/", "/WEB-INF/layout/main/decorator-index.jsp")
                ?.addDecoratorPath("/security.mi", "/WEB-INF/layout/main/decorator-index.jsp")
                ?.addDecoratorPath("/common/*", "/WEB-INF/layout/main/decorator-main-header.jsp")
                ?.addDecoratorPath("/m01/*", "/WEB-INF/layout/main/decorator-main-header.jsp")
                ?.addDecoratorPath("/m02/*", "/WEB-INF/layout/main/decorator-main-header.jsp")
                ?.addDecoratorPath("/m03/*", "/WEB-INF/layout/main/decorator-main-header.jsp")
                ?.addDecoratorPath("/m04/*", "/WEB-INF/layout/main/decorator-main-header.jsp")
                ?.addDecoratorPath("/m05/*", "/WEB-INF/layout/main/decorator-main-header.jsp")
                ?.addDecoratorPath("/pop/*", "/WEB-INF/layout/main/decorator-main-noheader.jsp")
                ?.addDecoratorPath("/m01/*.pop", "/WEB-INF/layout/main/decorator-main-noheader.jsp")
                ?.addDecoratorPath("/certification.mi", "/WEB-INF/layout/main/decorator-index.jsp")
                ?.addExcludedPath("/html/*")
                ?.addExcludedPath("/js/*")
                ?.addExcludedPath("/inc/*")
                ?.addExcludedPath("*.ajax")
                ?.addExcludedPath("/thumbnail/*")
                ?.addExcludedPath("/temp/*")
                ?.addExcludedPath("*.json")
    }
}