package me.ryder.savagecore;

import me.ryder.savagecore.commands.BaseCommand;
import me.ryder.savagecore.events.*;
import me.ryder.savagecore.events.autorespawn.AutoRespawnEvent;
import me.ryder.savagecore.events.factions.AntiWildernessSpawner;
import me.ryder.savagecore.events.factions.FastGolemDeathEvent;
import me.ryder.savagecore.events.player.BloodSprayEvent;
import me.ryder.savagecore.persist.Config;
import me.ryder.savagecore.utils.FileManager;
import me.ryder.savagecore.utils.FileManager.Files;
import net.prosavage.baseplugin.BasePlugin;
import org.bukkit.event.Listener;
import java.util.Objects;
import java.util.logging.Logger;

public final class SavageCore extends BasePlugin implements Listener {
    public static Logger logger;
    private FileManager fm = FileManager.getInstance();
    public BaseCommand command;

    @Override
    public void onEnable() {
        super.onEnable();
        loadData();
        loadLists();
        loadCmds();
    }

    @Override
    public void onDisable() {
        saveData();
    }

    private void loadData() {
        Config.load();
        fm.logInfo(true).setup(this);
    }

    private void saveData() {
        Config.save();
        Files.messages.saveFile();
    }

    private void loadCmds() {
        this.command = new BaseCommand(this);
        Objects.requireNonNull(getCommand("sc")).setExecutor(this.command);
    }

    private void loadLists() {
        registerListeners(new DenyItemBurnEvent());
        registerListeners(new DenyIceMeltEvent());
        registerListeners(new DenyBabyMobEvent());
        registerListeners(new DenyWeatherEvent());
        registerListeners(new DenyEndermanEvent());
        registerListeners(new DenyFireSpreadEvent());
        registerListeners(new DenyBlazeDrowning());
        registerListeners(new DenyPoppyDropEvent());
        registerListeners(new DenyWaterRedstone());
        registerListeners(new DenyIPPostEvent());
        registerListeners(new DenySpawnerStorage());
        registerListeners(new FastGolemDeathEvent());

        registerListeners(new AutoRespawnEvent());

        // Faction Checks
        registerListeners(new AntiWildernessSpawner());
        registerListeners(new DenyPearlGlitchEvent());

        // PVP / Player Based Checks
        registerListeners(new BloodSprayEvent());
    }
}