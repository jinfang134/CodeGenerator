package com.gdnyt.utils;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;

public final class MarkdownUtil {
	private static final DataHolder OPTIONS = PegdownOptionsAdapter
			.flexmarkOptions(Extensions.ALL);
	
	private MarkdownUtil(){}

	/**
	 * 将markdown文本转换成html
	 * @param markdown markdown文本
	 * @return
	 */
	public static String toHtml(String markdown){
		Parser parser = Parser.builder(OPTIONS).build();
		HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();
		Node document = parser.parse(markdown);
		String html = renderer.render(document);
		return html;
	}
	
	
}
