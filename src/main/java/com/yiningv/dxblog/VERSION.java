package com.yiningv.dxblog;

public final class VERSION {

    public final static int MajorVersion    = 0;
    public final static int MinorVersion    = 0;
    public final static int RevisionVersion = 1;

    public static String getVersionNumber() {
        return VERSION.MajorVersion + "." + VERSION.MinorVersion + "." + VERSION.RevisionVersion;
    }
}
