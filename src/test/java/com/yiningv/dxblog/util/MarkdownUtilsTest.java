package com.yiningv.dxblog.util;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;

public class MarkdownUtilsTest {

	@Test
	public void testRenderToHtml() throws Exception {
		InputStream resourceAsStream = MarkdownUtilsTest.class.getResourceAsStream("/markdown_test.md");
		String content = IOUtils.toString(resourceAsStream, "UTF-8");
		MarkdownUtils.ArticleMeta articleMeta = MarkdownUtils.renderToArticle(content, "https://raw.githubusercontent.com/yiningv/blog/master");
		System.out.println(articleMeta);
	}

}
