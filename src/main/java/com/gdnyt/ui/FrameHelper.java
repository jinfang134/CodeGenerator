package com.gdnyt.ui;

import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

import jodd.io.FileUtil;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;

public class FrameHelper extends JFrame {
	static final DataHolder OPTIONS = PegdownOptionsAdapter
			.flexmarkOptions(Extensions.ALL);

	public FrameHelper() {
		init();
	}

	// private String markdown() {
	// URL url = FrameMain.class.getResource("/markdown/string.md");
	// File file = new File(url.getFile());
	// String md = null;
	// try {
	// md = FileUtil.readString(file);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// // List<Extension> extensions = Arrays.asList(TablesExtension.create());
	// // Parser parser = Parser.builder().extensions(extensions).build();
	// // Node document = parser.parse(md);
	// // HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions)
	// // .build();
	// //
	// // String html = renderer.render(document); //
	// "<p>This is <em>Sparta</em></p>\n"
	// // return html;
	// }

	private String markdown() {
		URL url = FrameMain.class.getResource("/markdown/string.md");
		File file = new File(url.getFile());
		String md = null;
		try {
			md = FileUtil.readString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MutableDataSet options = new MutableDataSet();
		Parser parser = Parser.builder(OPTIONS).build();
		HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();

		// You can re-use parser and renderer instances
		Node document = parser.parse(md);
		String html = renderer.render(document); // "<p>This is <em>Sparta</em></p>\n"
		System.out.println(html);
		return html;
	}

	private void init() {
		URL url = FrameMain.class.getResource("/html/test.html");
		File file = new File(url.getFile());
		String data="<html><head><link rel=\"stylesheet\" href=\"./markdown.css\"/></head>"
				+ "<body><div class=\"markdown-body\">"+markdown()+"</div></body></html>";
		try {
			FileUtil.writeString(file, data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JEditorPane editorPane = new JEditorPane();
		String pathString = "file://" + file.getPath();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		try {
			editorPane.setPage(pathString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    //  scrollPane.setBounds(14, 39, 616, 374);
		scrollPane.setViewportView(editorPane);
//		getContentPane().add(scrollPane);
		
		
		getContentPane().add(scrollPane);
		setSize(500, 300);
		setVisible(true);
	}

	public static void main(String[] args) {
		new FrameHelper();
	}
}
