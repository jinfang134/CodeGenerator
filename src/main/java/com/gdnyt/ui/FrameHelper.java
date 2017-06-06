package com.gdnyt.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jodd.io.FileUtil;

import com.gdnyt.utils.MarkdownUtil;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;

public class FrameHelper extends JFrame {
	private final Logger log=LoggerFactory.getLogger(this.getClass());
	
	static final DataHolder OPTIONS = PegdownOptionsAdapter
			.flexmarkOptions(Extensions.ALL);

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
			e.printStackTrace();
			log.warn("read failed.{}",e);
		}
		String body = MarkdownUtil.toHtml(md);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("<html><head><link rel=\"stylesheet\" href=\"./markdown.css\"/></head>")
				.append("<body><div class=\"markdown-body\">").append(body)
				.append("</div></body></html>");
		return stringBuilder.toString();
	}

	private void init() {
		URL url = FrameMain.class.getResource("/html/test.html");
		File file = new File(url.getFile());
		
		try {
			FileUtil.writeString(file, markdown());
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
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// scrollPane.setBounds(14, 39, 616, 374);
		scrollPane.setViewportView(editorPane);
		// getContentPane().add(scrollPane);

		getContentPane().add(scrollPane);
		setSize(1024, 768);
		setVisible(true);
	}

	public static void main(String[] args) {
		new FrameHelper();
	}
}
