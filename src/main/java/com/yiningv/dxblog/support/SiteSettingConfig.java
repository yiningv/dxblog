package com.yiningv.dxblog.support;

import com.yiningv.dxblog.DxConst;
import com.yiningv.dxblog.model.SiteSetting;
import com.yiningv.dxblog.util.MapCache;
import com.yiningv.dxblog.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                .title(StringUtils.ifBlankDefault(title, "yining's Blog"))
                .subtitle(StringUtils.ifBlankDefault(subtitle, "yining's Blog"))
                .description(StringUtils.ifBlankDefault(description, "yining's Blog"))
                .author(StringUtils.ifBlankDefault(author, "yining"))
                .build();

        MapCache.single().set(DxConst.SITE_SETTING, siteSetting);
        return siteSetting;
    }
}
