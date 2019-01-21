package com.yiningv.dxblog.util;

import com.google.common.collect.Sets;
import com.vladsch.flexmark.Extension;
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
import com.yiningv.dxblog.DxConst;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MarkdownUtils {

    private MarkdownUtils() {
        throw new UnsupportedOperationException();
    }

    private static final Set<Extension> EXTENSIONS = Sets.newHashSet(TablesExtension.create(),
            StrikethroughExtension.create(),
            TaskListExtension.create(),
            TocExtension.create(),
            YamlFrontMatterExtension.create());
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, EXTENSIONS);
    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();


    public static ArticleMeta renderToArticle(String markdownText, String imgSourceUrl) {
        Node node = PARSER.parse(markdownText);
        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        visitor.visit(node);

        ArticleMeta articleMeta = new ArticleMeta();
        Map<String, List<String>> metaData = visitor.getData();
        metaData.forEach((key, value) -> {
            if ("title".equals(key)) {
                if (value.size() > 0) { articleMeta.setTitle(value.get(0)); }
            }
            if ("description".equals(key)) {
                if (value.size() > 0) { articleMeta.setDescription(value.get(0)); }
            }
            if ("category".equals(key)) {
                if (value.size() > 0) { articleMeta.setCategory(value.get(0)); }
            }
            if ("tags".equals(key)) {
                if (value.size() > 0) { articleMeta.setTags(value.get(0)); }
            }
            if ("date".equals(key)) {
                if (value.size() > 0) { articleMeta.setDate(value.get(0)); }
            }
        });

        String htmlContent = RENDERER.render(node);
        Document document = Jsoup.parseBodyFragment(htmlContent);
        Elements allImgs = document.getElementsByTag("img");
        for (Element img : allImgs) {
            transformImageSource(img, imgSourceUrl);
        }

        if (articleMeta.getDescription() == null) {
            Elements allP = document.getElementsByTag("p");
            if (allP.size() > 0) {
                Element p = allP.get(0);
                articleMeta.setDescription(p.html());
            }
        }

        articleMeta.setContent(document.body().html());

        return articleMeta;
    }

    private static void transformImageSource(Element img, String rawUrl) {
        String source = img.attr("src");

        if (!source.startsWith("http://") && !source.startsWith("https://")) {

            if (!rawUrl.endsWith("/")) {
                rawUrl = rawUrl.concat("/");
            }

            if (source.startsWith("../") && source.contains(DxConst.IMAGES_PREFIX.concat("/"))) {
                source = source.substring(source.indexOf(DxConst.IMAGES_PREFIX.concat("/")));
                source = rawUrl + source;
            }

            img.attr("src", source);
        }
    }

    @Data
    @ToString
    @NoArgsConstructor
    public static class ArticleMeta {
        private String title;
        private String description;
        private String category;
        private String tags;
        private String date;
        private String content;
    }

}
