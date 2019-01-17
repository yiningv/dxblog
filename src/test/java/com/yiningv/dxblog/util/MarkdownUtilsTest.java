package com.yiningv.dxblog.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MarkdownUtilsTest {

	@Test
	public void testRenderToHtml() {
		List<String> inputs = Arrays.asList(
//				"This is *Sparta*",
//				"# This is title h1\ncontent\n",
//				"## This is title h2",
//				"- dot",
//				"```go\nfunc main(){}\n```",
//				"[MySQL](https://www.mysql.com)",
//				"1231hajajs\n<a>hhh</a>",
//				"- [ ] Replace Jade with Thymeleaf(HTML)",
//				"# title\ncontent\n## title2\n### 中文\n## title2",
				"# h1\n## h2\n### h3"
//				"---" +
//						"\nhello: world" +
//						"\n..." +
//						"\n" +
//						"\ngreat",
//				"http://github.com/vsch/flexmark-java"
		);
		inputs.forEach(input -> {
			String html = MarkdownUtils.renderToHtml(input);
			System.out.println(html);
		});
	}

}
