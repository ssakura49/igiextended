package com.ssakura49.igiextended.tags;

import df11zomgraves.ingameinfo.tag.Tag;
import df11zomgraves.ingameinfo.tag.TagRegistry;

public abstract class IGIETags extends Tag {
    public static long lastRemoteUpdate = 0;

    @Override
    public String getCategory() {
        return "IGIExtended";
    }

    public static void register() {
        TagRegistry.INSTANCE.register(new TPS().setName("tps"));
        TagRegistry.INSTANCE.register(new MSPT().setName("mspt"));
    }
}
