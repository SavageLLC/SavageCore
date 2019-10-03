package net.savagellc.savagecore;

import net.prosavage.baseplugin.BasePlugin;
import net.savagellc.savagecore.commands.BaseCommand;
import net.savagellc.savagecore.listeners.*;
import net.savagellc.savagecore.listeners.autorespawn.AutoRespawn;
import net.savagellc.savagecore.listeners.factions.AntiWildernessSpawner;
import net.savagellc.savagecore.listeners.mobs.FastIronGolemDeath;
import net.savagellc.savagecore.listeners.pvp.BloodSpray;
import net.savagellc.savagecore.persist.Config;
import net.savagellc.savagecore.persist.Messages;
import net.savagellc.trackx.TrackX;
import org.bukkit.event.Listener;
import java.util.Objects;
import java.util.logging.Logger;

public final class SavageCore extends BasePlugin implements Listener {
    public static Logger logger;
    public BaseCommand command;

    @Override
    public void onEnable() {
        super.onEnable();
        loadData();
        if (Config.enableTrackX){
            TrackX.startTracking("savagecorex", this.getDescription().getVersion(), "net.savagellc.savagecore");
        }
        loadLists();
        loadCmds();
        if (Config.cleanScoreboardDatOnStart){
            getServer().getScoreboardManager().getMainScoreboard().getTeams().forEach(t -> t.unregister());
        }
    }

    @Override
    public void onDisable() { saveData(); }

    private void loadData() { Config.load(); Messages.load(); }

    private void saveData() { Config.save(); Messages.save(); }

    private void loadCmds() {
        this.command = new BaseCommand(this);
        Objects.requireNonNull(getCommand("savagecore")).setExecutor(this.command);
    }

    private void loadLists() {
        registerListeners(new DenyItemBurn(),
                new DenyIceMelt(),
                new DenyBabyMob(),
                new DenyWeather(),
                new DenyEndermanAI(),
                new DenyFireSpread(),
                new DenyBlazeDrowning(),
                new DenyWaterItems(),
                new DenyIPPost(),
                new DenySpawnerStorage(),
                new DenySpawnerGuard(),
                new DenyPearlGlitch(),
                new DenyMobItemPickUp(),
                new FastIronGolemDeath(),
                new FastIceBreak(),
                new AutoRespawn(),
                new AntiWildernessSpawner(),
                new BloodSpray());
    }
}