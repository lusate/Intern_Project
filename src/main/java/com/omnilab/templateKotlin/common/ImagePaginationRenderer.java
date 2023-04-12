package com.omnilab.templateKotlin.common;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

public class ImagePaginationRenderer extends AbstractPaginationRenderer {
	
	public ImagePaginationRenderer() {
		firstPageLabel = "<li class=\"page first\" onclick=\"{0}({1}); return false;\"></li>"; 
        previousPageLabel = "<li class=\"page prev\" onclick=\"{0}({1}); return false;\"></li>";
        currentPageLabel = "<li class=\"page now\">{0}</li>";
        otherPageLabel = "<li class=\"page\" onclick=\"{0}({1}); return false;\">{2}</li>";
        nextPageLabel = "<li class=\"page next\" onclick=\"{0}({1}); return false;\"></li>";
        lastPageLabel = "<li class=\"page last\" onclick=\"{0}({1}); return false;\"></li>";
    }
}
