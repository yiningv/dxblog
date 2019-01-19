package com.yiningv.dxblog.support;

import com.yiningv.dxblog.DxConst;
import com.yiningv.dxblog.model.SiteSetting;
import com.yiningv.dxblog.util.MapCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class SiteSettingConfig {

    @Value("${site-setting.title}")
    private String title;
    @Value("${site-setting.subtitle}")
    private String subtitle;
    @Value("${site-setting.description}")
    private String description;
    @Value("${site-setting.author}")
    private String author;

    @Bean
    public SiteSetting siteSetting() {
        Object siteSettingObj = MapCache.single().get(DxConst.SITE_SETTING);
        if (siteSettingObj != null) {
            return (SiteSetting) siteSettingObj;
        }
        SiteSetting siteSetting = SiteSetting.builder()
                .title(StringUtils.hasText(title)? title : "yining's Blog")
                .subtitle(StringUtils.hasText(title)? title : "yining's Blog")
                .description(StringUtils.hasText(title)? title : "yining's Blog")
                .author(StringUtils.hasText(title)? title : "yining")
                .build();

        MapCache.single().set(DxConst.SITE_SETTING, siteSetting);
        return siteSetting;
    }

    private static String ifBlankDefault(String str, String defaultStr) {
        return (StringUtils.hasText(str)? defaultStr : str);
    }
}
