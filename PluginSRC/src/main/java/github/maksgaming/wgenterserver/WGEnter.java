package github.maksgaming.wgenterserver;

import org.bukkit.plugin.java.*;
import org.bukkit.event.*;
import com.sk89q.worldguard.bukkit.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import com.sk89q.worldguard.protection.regions.*;
import java.util.logging.*;
import java.io.*;
import org.bukkit.entity.*;
import com.sk89q.worldguard.protection.*;
import java.util.*;

public class WGEnter extends JavaPlugin implements Listener
{
    static WorldGuardPlugin wg;

    static {
        WGEnter.wg = (WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard");
    }

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)this, "wg_enter");
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                try {
                    Player[] onlinePlayers;
                    for (int length = (onlinePlayers = WGEnter.this.getServer().getOnlinePlayers().toArray(new Player[0])).length, i = 0; i < length; ++i) {
                        final Player player = onlinePlayers[i];
                        final ApplicableRegionSet set = WGEnter.wg.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
                        String regionName = "GLOBAL";
                        boolean canBuild = true;
                        if (set.size() > 0) {
                            final Iterator iter = set.iterator();
                            if (iter.hasNext()) {
                                final ProtectedRegion region = (ProtectedRegion) iter.next();
                                regionName = region.getId();
                                canBuild = WGEnter.wg.canBuild(player, player.getLocation());
                            }
                        }
                        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        final DataOutputStream dos = new DataOutputStream(bos);
                        dos.writeUTF(regionName);
                        dos.writeBoolean(canBuild);
                        player.sendPluginMessage((Plugin)WGEnter.this, "wg_enter", bos.toByteArray());
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(WGEnter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 20L, 20L);
    }
}
