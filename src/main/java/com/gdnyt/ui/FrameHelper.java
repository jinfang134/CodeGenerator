package com.gdnyt.ui;

import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

import jodd.io.FileUtil;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class FrameHelper extends JFrame {

	public FrameHelper() {
		init();
	}

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

		List<Extension> extensions = Arrays.asList(TablesExtension.create());
		Parser parser = Parser.builder().extensions(extensions).build();
		Node document = parser.parse(md);
		HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions)
				.build();

		String html = renderer.render(document); // "<p>This is <em>Sparta</em></p>\n"
		return html;
	}

	private void init() {
		URL url = FrameMain.class.getResource("/html/test.html");
		File file = new File(url.getFile());

		JEditorPane editorPane = new JEditorPane();
		String pathString = "http://" + file.getPath();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		editorPane
				.setText("<html><head><style> table {width: 100%;border-collapse: collapse;border: 1px solid #ccc;} "
						+ "thead td {font-size: 12px;color: #333333;text-align: center;border: 1px solid #ccc; font-weight:bold;}"
						+ " tbody tr {background: #fff;font-size: 12px;color: #666666;}  "
						+ " td{line-height:20px;text-align: left;padding:4px 10px 3px 10px;height: 18px;border: 1px solid #ccc;} "
						+ "</style></head>" + markdown() + "</html>");// (pathString);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(editorPane);
		add(scrollPane);
		setSize(500, 300);
		setVisible(true);
	}

	public static void main(String[] args) {
		new FrameHelper();
	}
}
