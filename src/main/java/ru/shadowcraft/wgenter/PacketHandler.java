package ru.shadowcraft.wgenter;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PacketHandler
{
    private static String currentRegion;
    private static boolean canBuild;

    @SubscribeEvent
    public void onClientPacket(final FMLNetworkEvent.ClientCustomPacketEvent event) {
        final ByteBuf buf = event.getPacket().payload();
        ByteBufUtils.readUTF8String(buf);
        PacketHandler.currentRegion = ByteBufUtils.readUTF8String(buf);
        PacketHandler.canBuild = buf.readBoolean();
    }

    @SubscribeEvent
    public void onRenderOverlay(final RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT && PacketHandler.currentRegion != null && !PacketHandler.currentRegion.equals("GLOBAL")) {
            final TextFormatting color = PacketHandler.canBuild ? TextFormatting.GREEN : TextFormatting.RED;
            Minecraft.getMinecraft().fontRenderer.drawString(color + "[" + TextFormatting.WHITE + PacketHandler.currentRegion + color + "]", 10, 20, 16777215);
        }
    }
}
