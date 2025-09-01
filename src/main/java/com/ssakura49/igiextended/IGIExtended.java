package com.ssakura49.igiextended;

import com.mojang.logging.LogUtils;
import com.ssakura49.igiextended.network.PackHandler;
import com.ssakura49.igiextended.tags.IGIETags;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(IGIExtended.MODID)
public class IGIExtended {
    public static final String MODID = "igiextended_reborn";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CompoundTag cachedData =  new CompoundTag();

    public IGIExtended(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        PackHandler.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        IGIETags.register();
    }
}
