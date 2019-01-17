package com.yiningv.dxblog.util;

import com.google.common.collect.Sets;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarkdownUtils {

    private MarkdownUtils() {}

    private static final Set<Extension> EXTENSIONS = Sets.newHashSet(TablesExtension.create(),
            AutolinkExtension.create(),
            StrikethroughExtension.create(),
            TaskListExtension.create(),
            TocExtension.create(),
            YamlFrontMatterExtension.create());
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(TocExtension.LIST_CLASS, "111")
            .set(Parser.EXTENSIONS, EXTENSIONS);
    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();


    public static String renderToHtml(String markdownText) {
        markdownText = markdownText + "\n[TOC levels=2-4]";
        Node node = PARSER.parse(markdownText);
        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        visitor.visit(node);

        Map<String, List<String>> data = visitor.getData();
        data.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });


        return RENDERER.render(node);
    }

}
