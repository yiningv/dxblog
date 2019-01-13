package com.yiningv.dxblog.utils;

import static org.junit.Assert.*;

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
				"- [ ] Replace Jade with Thymeleaf(HTML)"
		);
		inputs.forEach(input -> {
			String html = MarkdownUtils.renderToHtml(input);
			System.out.println(html);
		});
	}

}
