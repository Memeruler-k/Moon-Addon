package com.moon.addon.modules;

import com.moon.addon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;

public class CrystalAuraV5 extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgSwitch = settings.createGroup("Switch");
    private final SettingGroup sgPlace = settings.createGroup("Place");
    private final SettingGroup sgFacePlace = settings.createGroup("Face Place");
    private final SettingGroup sgTiming = settings.createGroup("Timing");
    private final SettingGroup sgBreak = settings.createGroup("Break");
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final SettingGroup sgRender = settings.createGroup("Render");

    // General

    private final Setting<Double> targetRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("target-range")
        .description("Range in which to target players.")
        .defaultValue(10)
        .min(0)
        .sliderMax(16)
        .build()
    );

    private final Setting<Boolean> predictMovement = sgGeneral.add(new BoolSetting.Builder()
        .name("predict-movement")
        .description("Predicts target movement.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> minDamage = sgGeneral.add(new DoubleSetting.Builder()
        .name("min-damage")
        .description("Minimum damage the crystal needs to deal to your target.")
        .defaultValue(6)
        .min(0)
        .build()
    );

    private final Setting<Double> maxDamage = sgGeneral.add(new DoubleSetting.Builder()
        .name("max-damage")
        .description("Maximum damage crystals can deal to yourself.")
        .defaultValue(6)
        .range(0, 36)
        .sliderMax(36)
        .build()
    );

    private final Setting<Boolean> antiSuicide = sgGeneral.add(new BoolSetting.Builder()
        .name("anti-suicide")
        .description("Will not place and break crystals if they will kill you.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignoreNakeds = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-nakeds")
        .description("Ignore players with no items.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Rotates server-side towards the crystals being hit/placed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<YawStepMode> yawStepMode = sgGeneral.add(new EnumSetting.Builder<YawStepMode>()
        .name("yaw-steps-mode")
        .description("When to run the yaw steps check.")
        .defaultValue(YawStepMode.Break)
        .visible(rotate::get)
        .build()
    );

    private final Setting<Double> yawSteps = sgGeneral.add(new DoubleSetting.Builder()
        .name("yaw-steps")
        .description("Maximum number of degrees its allowed to rotate in one tick.")
        .defaultValue(180)
        .range(1, 180)
        .visible(rotate::get)
        .build()
    );

    private final Setting<Set<EntityType<?>>> entities = sgGeneral.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Entities to attack.")
        .onlyAttackable()
        .defaultValue(EntityType.PLAYER, EntityType.WARDEN, EntityType.WITHER)
        .build()
    );

    // Switch

    private final Setting<AutoSwitchMode> autoSwitch = sgSwitch.add(new EnumSetting.Builder<AutoSwitchMode>()
        .name("auto-switch")
        .description("Switches to crystals in your hotbar once a target is found.")
        .defaultValue(AutoSwitchMode.Normal)
        .build()
    );

    private final Setting<Integer> switchDelay = sgSwitch.add(new IntSetting.Builder()
        .name("switch-delay")
        .description("The delay in ticks to wait to break a crystal after switching hotbar slot.")
        .defaultValue(0)
        .min(0)
        .build()
    );

    private final Setting<Boolean> noGapSwitch = sgSwitch.add(new BoolSetting.Builder()
        .name("no-gap-switch")
        .description("Won't auto switch if you're holding a gapple.")
        .defaultValue(true)
        .visible(() -> autoSwitch.get() == AutoSwitchMode.Normal)
        .build()
    );

    private final Setting<Boolean> noBowSwitch = sgSwitch.add(new BoolSetting.Builder()
        .name("no-bow-switch")
        .description("Won't auto switch if you're holding a bow.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> antiWeakness = sgSwitch.add(new BoolSetting.Builder()
        .name("anti-weakness")
        .description("Switches to tools with so you can break crystals with the weakness effect.")
        .defaultValue(true)
        .build()
    );

    // Place

    private final Setting<Boolean> doPlace = sgPlace.add(new BoolSetting.Builder()
        .name("place")
        .description("If the CA should place crystals.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Integer> placeDelay = sgPlace.add(new IntSetting.Builder()
        .name("place-delay")
        .description("The delay in ticks to wait to place a crystal after it's exploded.")
        .defaultValue(0)
        .min(0)
        .sliderMax(20)
        .build()
    );

    private final Setting<Double> placeRange = sgPlace.add(new DoubleSetting.Builder()
        .name("place-range")
        .description("Range in which to place crystals.")
        .defaultValue(4.5)
        .min(0)
        .sliderMax(6)
        .build()
    );

    private final Setting<Double> placeWallsRange = sgPlace.add(new DoubleSetting.Builder()
        .name("walls-range")
        .description("Range in which to place crystals when behind blocks.")
        .defaultValue(4.5)
        .min(0)
        .sliderMax(6)
        .build()
    );

    private final Setting<Boolean> placement112 = sgPlace.add(new BoolSetting.Builder()
        .name("1.12-placement")
        .description("Uses 1.12 crystal placement.")
        .defaultValue(false)
        .build()
    );

    private final Setting<SupportMode> support = sgPlace.add(new EnumSetting.Builder<SupportMode>()
        .name("support")
        .description("Places a support block in air if no other position have been found.")
        .defaultValue(SupportMode.Disabled)
        .build()
    );

    private final Setting<Integer> supportDelay = sgPlace.add(new IntSetting.Builder()
        .name("support-delay")
        .description("Delay in ticks after placing support block.")
        .defaultValue(1)
        .min(0)
        .visible(() -> support.get() != SupportMode.Disabled)
        .build()
    );

    // Face place

    private final Setting<Boolean> facePlace = sgFacePlace.add(new BoolSetting.Builder()
        .name("face-place")
        .description("Will face-place when target is below a certain health or armor durability threshold.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> facePlaceHealth = sgFacePlace.add(new DoubleSetting.Builder()
        .name("face-place-health")
        .description("The health the target has to be at to start face placing.")
        .defaultValue(8)
        .min(1)
        .sliderMin(1)
        .sliderMax(36)
        .visible(facePlace::get)
        .build()
    );

    private final Setting<Double> facePlaceDurability = sgFacePlace.add(new DoubleSetting.Builder()
        .name("face-place-durability")
        .description("The durability threshold percentage to be able to face-place.")
        .defaultValue(2)
        .min(1)
        .sliderMin(1)
        .sliderMax(100)
        .visible(facePlace::get)
        .build()
    );

    private final Setting<Boolean> facePlaceArmor = sgFacePlace.add(new BoolSetting.Builder()
        .name("face-place-missing-armor")
        .description("Automatically starts face placing when a target misses a piece of armor.")
        .defaultValue(false)
        .visible(facePlace::get)
        .build()
    );

    private final Setting<Keybind> forceFacePlace = sgFacePlace.add(new KeybindSetting.Builder()
        .name("force-face-place")
        .description("Starts face place when this button is pressed.")
        .defaultValue(Keybind.none())
        .build()
    );

    // Timing

    private final Setting<Double> targetTiming = sgTiming.add(new DoubleSetting.Builder()
        .name("target-timing")
        .description("Target hit timing.")
        .defaultValue(10)
        .min(0.01)
        .sliderMax(1000.0)
        .build()
    );

    private final Setting<Boolean> 18hitdelay = sgFacePlace.add(new BoolSetting.Builder()
        .name("Use 1.8 hit delay")
        .description("Allows you to combo someone like in 1.8.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> placeDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("place-delay")
        .description("Delay between placing crystals.")
        .defaultValue(50)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> breakDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("break-delay")
        .description("Delay between breaking crystals.")
        .defaultValue(50)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> attackSpeed = sgTiming.add(new DoubleSetting.Builder()
        .name("attack-speed")
        .description("How fast on hitting enemies.")
        .defaultValue(10)
        .min(1)
        .sliderMax(20)
        .build()
    );

    private final Setting<Double> tickRate = sgTiming.add(new DoubleSetting.Builder()
        .name("tick-rate")
        .description("How often check for actions.")
        .defaultValue(5)
        .min(1)
        .sliderMax(20)
        .build()
    );

    private final Setting<Double> actionDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("action-delay")
        .description("How fast to break and place crystals.")
        .defaultValue(100)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> multiPlaceDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("multi-place-delay")
        .description("Delay between placing multiple crystals.")
        .defaultValue(50)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> multiBreakDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("multi-break-delay")
        .description("Delay between breaking multiple crystals.")
        .defaultValue(50)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> swingDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("swing-delay")
        .description("Delay between the swing animation.")
        .defaultValue(100)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> pingCompensation = sgTiming.add(new DoubleSetting.Builder()
        .name("ping-compensation")
        .description("Delay to match server ping and network latency.")
        .defaultValue(20)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> burstDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("burst-delay")
        .description("Delay between placing multiple crystals rapidly.")
        .defaultValue(200)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> failSafeDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("max-burst-actions")
        .description("How many burst actions allowed to use.")
        .defaultValue(5)
        .min(1)
        .sliderMax(10)
        .build()
    );

    private final Setting<Double> randomization = sgTiming.add(new DoubleSetting.Builder()
        .name("randomization")
        .description("Apply random delay to avoid detection by anticheats.")
        .defaultValue(10)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> actionCooldown = sgTiming.add(new DoubleSetting.Builder()
        .name("action-cooldown")
        .description("Delay to stop in order to stop excessive usage or server lag.")
        .defaultValue(1000)
        .min(1)
        .sliderMax(5000)
        .build()
    );

    private final Setting<Double> interactionInterval = sgTiming.add(new DoubleSetting.Builder()
        .name("interaction-interval")
        .description("Delay between different types of interactions.")
        .defaultValue(200)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> breakSwingDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("break-swing-delay")
        .description("Specific delay for the swing animation when breaking crystals.")
        .defaultValue(150)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> placeSwingDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("place-swing-delay")
        .description("Specific delay for the swing animation when placing crystals.")
        .defaultValue(150)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> preAttackDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("pre-attack-delay")
        .description("Delay before crystal aura attempts to attack after placing a crystal.")
        .defaultValue(300)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> postAttackDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("post-attack-delay")
        .description("Delay after an attack action before crystal aura proceeds with the next action.")
        .defaultValue(300)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> predictionDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("prediction-delay")
        .description("Timing delay based of predicting player movement or positons.")
        .defaultValue(100)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> resetDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("reset-delay")
        .description("Delay to reset the state machine if no action was successful")
        .defaultValue(2000)
        .min(1)
        .sliderMax(5000)
        .build()
    );

    private final Setting<Double> smartDelayAdjustment = sgTiming.add(new DoubleSetting.Builder()
        .name("smart-delay-adjustment")
        .description("Automatically adjust delay based on server TPS.")
        .defaultValue(100)
        .min(1)
        .sliderMax(500)
        .build()
    );

    private final Setting<Double> sequentialActionDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("sequential-action-delay")
        .description("Delay between sequential actions when performing a series of placements and breaks.")
        .defaultValue(250)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    public CrystalAuraV5() {
        super(Addon.CATEGORY, "Crystal Aura V5", "An improved version of Crystal Aura.");
    }
}
