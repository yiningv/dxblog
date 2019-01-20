package com.yiningv.dxblog.config;

import com.yiningv.dxblog.DxConst;
import com.yiningv.dxblog.model.SiteSetting;
import com.yiningv.dxblog.util.MapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SiteSettingInitializer implements ApplicationRunner {

    @Autowired
    private Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SiteSetting siteSetting = SiteSetting.builder()
                .title(env.getProperty("site-setting.title", "yining's Blog"))
                .subtitle(env.getProperty("site-setting.subtitle", "yining's Blog"))
                .description(env.getProperty("site-setting.description", "yining的博客"))
                .author(env.getProperty("site-setting.author", "yining"))
                .build();

        MapCache.single().set(DxConst.SITE_SETTING, siteSetting);
    }

}
