package com.moon.addon;

import com.moon.addon.commands.*;
import com.moon.addon.hud.*;
import com.moon.addon.modules.*;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Addon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Moon");
    public static final HudGroup HUD_GROUP = new HudGroup("Moon");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Moon Addon");

        // Modules
        Modules.get().add(new AnchorAuraPlus());
        Modules.get().add(new AntiAim());
        Modules.get().add(new AutoGG());
        Modules.get().add(new BedAura());
        Modules.get().add(new BedAuraV2());
        Modules.get().add(new BonemealAura());
        Modules.get().add(new BowAimbot());
        Modules.get().add(new BridgadierCrash());
        Modules.get().add(new ClickAura());
        Modules.get().add(new CompletionCrash());
        Modules.get().add(new CrashChest());
        Modules.get().add(new CreativeFlight());
        Modules.get().add(new CrystalAura());
        Modules.get().add(new CrystalAuraV2());
        Modules.get().add(new FeedAura());
        Modules.get().add(new FightBot());
        Modules.get().add(new ForceOP());
        Modules.get().add(new GrimNoSlow());
        Modules.get().add(new GrimSpeedMine());
        Modules.get().add(new GrimVelocity());
        Modules.get().add(new HandNoClip());
        Modules.get().add(new HeadRoll());
        Modules.get().add(new HealthTags());
        Modules.get().add(new InfiniteAura());
        Modules.get().add(new InfiniteChat());
        Modules.get().add(new InstantBunker());
        Modules.get().add(new ItemExploits());
        Modules.get().add(new Jetpack());
        Modules.get().add(new Kaboom());
        Modules.get().add(new KillauraPlus());
        Modules.get().add(new KillauraLegit());
        Modules.get().add(new Liquids());
        Modules.get().add(new LSD());
        Modules.get().add(new MountBypass());
        Modules.get().add(new MultiAura());
        Modules.get().add(new NocomCrash());
        Modules.get().add(new OpenWaterESP());
        Modules.get().add(new PortalGUI());
        Modules.get().add(new ReachPlus());
        Modules.get().add(new RemoteView());
        Modules.get().add(new Restock());
        Modules.get().add(new ShotbowCrash());
        Modules.get().add(new SlotCrash());
        Modules.get().add(new SnowShoe());
        Modules.get().add(new TillAura());
        Modules.get().add(new TargetStrafe());
        Modules.get().add(new TreeBot());
        Modules.get().add(new TriggerBot());
        Modules.get().add(new UwU());
        Modules.get().add(new VulcanGlide());

        // Commands
        Commands.add(new CommandExample());

        // HUD
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.moon.addon";
    }
}
