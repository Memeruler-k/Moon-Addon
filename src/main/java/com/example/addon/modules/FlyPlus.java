package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;

public class FlyPlus extends Module {
  private final SettingGroup sgGeneral = settings.getDefaultGroup();

  private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("grim")
        .description("Bypass for Grim.")
        .defaultValue(Mode.Grim)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("nocheatplus")
        .description("Bypass for NoCheatPlus.")
        .defaultValue(Mode.NoCheatPlus)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("vulcan")
        .description("Bypass for Vulcan.")
        .defaultValue(Mode.Vulcan)
        .build()
    );
  
    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("spartan")
        .description("Bypass for Spartan.")
        .defaultValue(Mode.Spartan)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("watchdog")
        .description("Bypass for Watchdog.")
        .defaultValue(Mode.Watchdog)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("cubecraft")
        .description("Bypass for Cubecraft.")
        .defaultValue(Mode.Cubecraft)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mineplex")
        .description("Bypass for Mineplex.")
        .defaultValue(Mode.Mineplex)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("pvpland")
        .description("Bypass for PVP Land.")
        .defaultValue(Mode.PVPLand)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("pvplegacy")
        .description("Bypass for PVP Legacy.")
        .defaultValue(Mode.PVPLegacy)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("matrix")
        .description("Bypass for Matrix.")
        .defaultValue(Mode.Matrix)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("donutsmp")
        .description("Bypass for DonutSMP.")
        .defaultValue(Mode.DonutSMP)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("negativity")
        .description("Bypass for Negativity.")
        .defaultValue(Mode.Negativity)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("negativityv2")
        .description("Bypass for Negativity V2.")
        .defaultValue(Mode.NegativityV2)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("uncp")
        .description("Bypass for UNCP.")
        .defaultValue(Mode.UNCP)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("intave")
        .description("Bypass for Intave.")
        .defaultValue(Mode.Intave)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("polar")
        .description("Bypass for Polar.")
        .defaultValue(Mode.Polar)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("wraith")
        .description("Bypass for Wraith.")
        .defaultValue(Mode.Wraith)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("themis")
        .description("Bypass for Themis")
        .defaultValue(Mode.Themis)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("aac")
        .description("Bypass for AAC")
        .defaultValue(Mode.AAC)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("custom")
        .description("Bypass for Custom Anticheats")
        .defaultValue(Mode.Grim)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("phoenix")
        .description("Bypass for Phoenix.")
        .defaultValue(Mode.Phoenix)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("horizon")
        .description("Bypass for Horizon.")
        .defaultValue(Mode.Horizon)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("pikanetwork")
        .description("Bypass for PikaNetwork.")
        .defaultValue(Mode.PikaNetwork)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("minemen")
        .description("Bypass for Minemen.")
        .defaultValue(Mode.Minemen)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("blocksmc")
        .description("Bypass for BlocksMC.")
        .defaultValue(Mode.BlocksMC)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("verus")
        .description("Bypass for Verus.")
        .defaultValue(Mode.Verus)
        .build()
    );

    private final Setting<Mode> BypassMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("taka")
        .description("Bypass for Taka.")
        .defaultValue(Mode.Taka)
        .build()
    );
  
public class FlyPlus {
    public FlyPlus() {
        super(Addon.CATEGORY, "Fly+", "Fly made to bypass certain anticheats or gamemodes.");
    }

    public enum Mode {
        Grim,
        Cubecraft,
        Watchdog,
        Wraith,
        Mineplex,
        Taka,
        BlocksMC,
        Spartan,
        Verus,
        UNCP,
        Matrix,
        NCP,
        Minemen,
        PikaNetwork,
        Horizon,
        Vulcan,
        Themis,
        AAC,
        Intave,
        Polar,
        Custom,
        PAC,
        Themis,
        PVPLand,
        PVPLegacy,
        DonutSMP,
        Negativity,
        NegativityV2
