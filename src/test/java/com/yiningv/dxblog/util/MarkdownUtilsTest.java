package com.yiningv.dxblog.util;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;

@RunWith(JUnit4.class)
public class MarkdownUtilsTest {

	@Test
	public void renderToArticle() throws Exception {
		InputStream resourceAsStream = MarkdownUtilsTest.class.getResourceAsStream("/markdown_test.md");
		String content = IOUtils.toString(resourceAsStream, "UTF-8");
		resourceAsStream.close();
		MarkdownUtils.ArticleMeta articleMeta = MarkdownUtils.renderToArticle(content, "https://raw.githubusercontent.com/yiningv/blog/master");
		System.out.println(articleMeta);
	}

}
