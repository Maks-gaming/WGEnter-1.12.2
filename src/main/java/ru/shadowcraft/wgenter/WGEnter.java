package ru.shadowcraft.wgenter;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = "WGEnter")
public class WGEnter
{
    public static FMLEventChannel channel;

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        final PacketHandler handler = new PacketHandler();
        (WGEnter.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("wg_enter")).register((Object)handler);
        MinecraftForge.EVENT_BUS.register((Object)handler);
    }
}
