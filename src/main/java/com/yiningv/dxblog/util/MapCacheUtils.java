package com.yiningv.dxblog.util;

import com.yiningv.dxblog.DxConst;
import com.yiningv.dxblog.model.SiteSetting;

public class MapCacheUtils {

    private MapCacheUtils() {
        throw new UnsupportedOperationException();
    }

    public static SiteSetting siteSetting() {
        Object siteSettingObj = MapCache.single().get(DxConst.SITE_SETTING);
        if (siteSettingObj == null) {
            SiteSetting siteSetting = SiteSetting.builder()
                    .title("yining's Blog")
                    .subtitle("yining's Blog")
                    .description("yining的博客")
                    .author("yining")
                    .build();
            return siteSetting;
        }
        return (SiteSetting) siteSettingObj;
    }
}
