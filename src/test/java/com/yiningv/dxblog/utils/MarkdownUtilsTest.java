package com.yiningv.dxblog.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MarkdownUtilsTest {

	@Test
	public void testRenderToHtml() {
		List<String> inputs = Arrays.asList("This is *Sparta*",
				"# This is title h1\ncontent\n",
				"## This is title h2",
				"- dot",
				"```go\nfunc main(){}\n```",
				"[MySQL](https://www.mysql.com)",
				"1231hajajs\n<a>hhh</a>",
				"- [ ] Replace Jade with Thymeleaf(HTML)",
				"[TOC]\n# title\ncontent\n## title2\n### 中文\n## title2"
		);
		inputs.forEach(input -> {
			String html = MarkdownUtils.renderToHtml(input);
			System.out.println(html);
		});
	}

}
