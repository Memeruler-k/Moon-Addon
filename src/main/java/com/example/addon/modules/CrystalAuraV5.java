package com.moon.addon.modules;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;

import com.moon.addon.Addon;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;

public class CrystalAuraV5 extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgSwitch = settings.createGroup("Switch");
    private final SettingGroup sgPlace = settings.createGroup("Place");
    private final SettingGroup sgFacePlace = settings.createGroup("Face Place");
    private final SettingGroup sgTiming = settings.createGroup("Timing");
    private final SettingGroup sgStrategy = settings.createGroup("Strategy");
    private final SettingGroup sgCombat = settings.createGroup("Combat");
    private final SettingGroup sgSafety = settings.createGroup("Safety");
    private final SettingGroup sgPerformance = settings.createGroup("Performance");
    private final SettingGroup sgPatterns = settings.createGroup("Patterns");
    private final SettingGroup sgAutomation = settings.createGroup("Automation");
    private final SettingGroup sgAnticheat = settings.createGroup("Anticheat");
    private final SettingGroup sgBreak = settings.createGroup("Break");
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final SettingGroup sgRender = settings.createGroup("Render");

    public enum PlacementMode {
        Closest_To_Enemy, Optimal_Damange, Safe_Distance;
    }

    public enum BreakMode {
        Priority, All_Around, Health_Based;
    }

    public enum AttackMode {
        Single_Target, Multi_Target, Focus;
    }

    public enum TargetingMode {
        Closest_First, Lowest_Health, Most_Dangerous;
    }

    public enum SafetyMode {
        Off, Self_Distance_Check, Block_Under;
    }

    public enum EfficiencyMode {
        Balanced, Performance, Effectiveness;
    }

    public enum CrystalPlacementMode {
        Linear, Cluster, Randomized;
    }

    public enum CrystalBreakMode {
        Sequential, Simultaneous, Strategic;
    }

    public enum AutoSwitchMode {
        None, Crystal_Priority, Tool_Priority;
    }

    public enum AnticheatMode {
        Bypass, Legit, Stealth, Hybrid;
    }

    public enum FailSafeMode {
        Shutdown, Pause, Slowdown, Notification;
    }

    public enum ActionLogLevelMode {
        Minimal, Normal, Verbose, Debug;
    }

    public enum FakelagMode {
        None, Mild, Moderate, Severe;
    }



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
        .build()
    );

    private final Setting<Double> yawSteps = sgGeneral.add(new DoubleSetting.Builder()
        .name("yaw-steps")
        .description("Maximum number of degrees its allowed to rotate in one tick.")
        .defaultValue(180)
        .range(1, 180)
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

    private final Setting<Boolean> forceFacePlace = sgFacePlace.add(new BoolSetting.Builder()
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
        .sliderMax(100.0)
        .build()
    );

    private final Setting<Boolean> hitDelay18 = sgFacePlace.add(new BoolSetting.Builder()
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

    private final Setting<Double> actionTimeout = sgTiming.add(new DoubleSetting.Builder()
        .name("action-timeout")
        .description("Maximum time to wait for an action to complete before moving to the next one")
        .defaultValue(1000)
        .min(1)
        .sliderMax(5000)
        .build()
    );

    private final Setting<Double> failRetryDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("fail-retry-delay")
        .description("Delay before retrying a failed action.")
        .defaultValue(500)
        .min(1)
        .sliderMax(5000)
        .build()
    );

    private final Setting<Double> placeFocusDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("place-focus-delay")
        .description("Delay specifically when placing crystals while focusing on a target.")
        .defaultValue(200)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> breakFocusDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("break-focus-delay")
        .description("Delay specifically when breaking crystals while focusing on a target.")
        .defaultValue(200)
        .min(1)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> adaptiveDelay = sgTiming.add(new DoubleSetting.Builder()
        .name("adaptive-delay")
        .description("Adjust delays dynamically based on the current combat situation.")
        .defaultValue(100)
        .min(1)
        .sliderMax(500)
        .build()
    );

    private final Setting<Double> delayMultiplier = sgTiming.add(new DoubleSetting.Builder()
        .name("delay-multiplier")
        .description("Multiplier to apply to all delay settings, useful for quick adjustments.")
        .defaultValue(1)
        .min(0.1)
        .sliderMax(2)
        .build()
    );

    // Strategy

    private final Setting<PlacementMode> PlacementMode = sgStrategy.add(new EnumSetting.Builder<PlacementMode>() 
            .name("Placement")
            .description("Placement Strategy Modes.")
            .defaultValue(PlacementMode.ClosestToEnemy)
            .build()
    );

    private final Setting<BreakMode> BreakMode = sgStrategy.add(new EnumSetting.Builder<BreakMode>() 
            .name("Break")
            .description("Break Strategy Modes.")
            .defaultValue(Break.Priority)
            .build()
    );

    public final Setting<Boolean> dynamicAdjustment = sgStrategy.add(new BoolSetting.Builder()
        .name("dynamic-adjustment")
        .description("Automatically adjust strategies based on combat situation.")
        .defaultValue(true)
        .build()
    );

    // Combat

    private final Setting<AttackMode> attackMode = sgCombat.add(new EnumSetting.Builder<AttackMode>() 
            .name("attack-mode")
            .description("Attack Combat Modes.")
            .defaultValue(AttackMode.SingleTarget)
            .build()
    );

    private final Setting<TargetingMode> targetingMode = sgCombat.add(new EnumSetting.Builder<TargetingMode>() 
            .name("targeting-mode")
            .description("How crystal aura targets.")
            .defaultValue(TargetingMode.ClosestFirst)
            .build()
    );

    private final Setting<Double> maxTargets = sgCombat.add(new DoubleSetting.Builder()
        .name("max-targets")
        .description("Maximum number of targets to attack.")
        .defaultValue(3)
        .min(1)
        .sliderMax(10)
        .build()
    );

    private final Setting<Double> criticalHitChance = sgCombat.add(new DoubleSetting.Builder()
        .name("critical-hit-chance")
        .description("Adjust the probability of landing critical hits.")
        .defaultValue(50)
        .min(1)
        .sliderMax(100)
        .build()
    );

    // Safety

    private final Setting<SafetyMode> safetyMode = sgSafety.add(new EnumSetting.Builder<SafetyMode>() 
            .name("safety-mode")
            .description("Safety modes.")
            .defaultValue(SafetyMode.Off)
            .build()
    );

    private final Setting<Double> minimumHealthToAct = sgSafety.add(new DoubleSetting.Builder()
        .name("minimum-heath-to-act")
        .description("Minimum health threshold to perform actions.")
        .defaultValue(10)
        .min(1)
        .sliderMax(20)
        .build()
    );

    public final Setting<Boolean> avoidSelfDamage = sgSafety.add(new BoolSetting.Builder()
        .name("avoid-self-damage")
        .description("Enable accitional checks to avoid self damage.")
        .defaultValue(true)
        .build()
    );

    // Performance

    private final Setting<EfficiencyMode> efficiencyMode = sgPerformance.add(new EnumSetting.Builder<EfficiencyMode>() 
            .name("efficiency-mode")
            .description("How fast your performance is.")
            .defaultValue(EfficiencyMode.Balanced)
            .build()
    );

    private final Setting<Double> maxActionsPerTick = sgPerformance.add(new DoubleSetting.Builder()
        .name("max-actions-per-tick")
        .description("Limits the number of actions per tick to reduce load.")
        .defaultValue(5)
        .min(1)
        .sliderMax(10)
        .build()
    );

    public final Setting<Boolean> serverLoadAdaptation = sgPerformance.add(new BoolSetting.Builder()
        .name("server-load-adaptation")
        .description("Automatically adjust settings based on server load.")
        .defaultValue(true)
        .build()
    );

    // Patterns

    private final Setting<CrystalPlacementMode> crystalPlacementMode = sgPatterns.add(new EnumSetting.Builder<CrystalPlacementMode>() 
            .name("crystal-placement-mode")
            .description("Placement modes for crystals.")
            .defaultValue(CrystalPlacementMode.Linear)
            .build()
    );

    private final Setting<CrystalBreakMode> crystalBreakMode = sgPatterns.add(new EnumSetting.Builder<CrystalBreakMode>() 
            .name("crystal-break-mode")
            .description("Break modes for crystals.")
            .defaultValue(CrystalBreakMode.Squential)
            .build()
    );

    private final Setting<Double> placementRadius = sgPatterns.add(new DoubleSetting.Builder()
        .name("placement-radius")
        .description("Defines the radius for placing crystals.")
        .defaultValue(5)
        .min(1)
        .sliderMax(10)
        .build()
    );

    private final Setting<Double> breakRadius = sgPatterns.add(new DoubleSetting.Builder()
        .name("break-radius")
        .description("Defines the radius for breaking crystals.")
        .defaultValue(5)
        .min(1)
        .sliderMax(10)
        .build()
    );

    // Automation

    private final Setting<AutoSwitchMode> autoSwitchMode = sgAutomation.add(new EnumSetting.Builder<AutoSwitchMode>() 
            .name("auto-switch-mode")
            .description("Auto Switch Modes.")
            .defaultValue(AutoSwitchMode.None)
            .build()
    );

    public final Setting<Boolean> autoRetarget = sgAutomation.add(new BoolSetting.Builder()
        .name("auto-retarget")
        .description("Automatically retarget when current target is eliminated.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> inventoryManagementSpeed = sgAutomation.add(new DoubleSetting.Builder()
        .name("inventory-management-speed")
        .description("Speed of switching items in the inventory.")
        .defaultValue(50)
        .min(1)
        .sliderMax(100)
        .build()
    );

    // Anticheat

   private final Setting<AnticheatMode> anticheatMode = sgAnticheat.add(new EnumSetting.Builder<AnticheatMode>() 
            .name("anti-cheat-mode")
            .description("Anticheat bypass modes.")
            .defaultValue(AnticheatMode.Bypass)
            .build()
    );

    private final Setting<Double> actionRandomization = sgAnticheat.add(new DoubleSetting.Builder()
        .name("action-randomization")
        .description("Adds a random delay to actions to avoid patterns.")
        .defaultValue(20)
        .min(1)
        .sliderMax(100)
        .build()
    );

    private final Setting<Double> speedLimiter = sgAnticheat.add(new DoubleSetting.Builder()
        .name("speed-limiter")
        .description("Limits the speed of actions to avoid suspicion.")
        .defaultValue(75)
        .min(50)
        .sliderMax(100)
        .build()
    );

    private final Setting<Double> packetThrottling = sgAnticheat.add(new DoubleSetting.Builder()
        .name("packet-throttling")
        .description("Controls the rate of packet sending to the server.")
        .defaultValue(50)
        .min(1)
        .sliderMax(100)
        .build()
    );

    public final Setting<Boolean> humanLikeMovements = sgAnticheat.add(new BoolSetting.Builder()
        .name("human-like-movements")
        .description("Simulates human-like mouse movements.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> randomizedJitter = sgAnticheat.add(new DoubleSetting.Builder()
        .name("randomized-jitter")
        .description("Adds small random movements to simulate jitter.")
        .defaultValue(2)
        .min(1)
        .sliderMax(10)
        .build()
    );

    public final Setting<Boolean> adaptiveBehavior = sgAnticheat.add(new BoolSetting.Builder()
        .name("adaptive-behavior")
        .description("Adjusts behavior based on server responses.")
        .defaultValue(true)
        .build()
    );

    
    private final Setting<Double> actionCooldownVariation = sgAnticheat.add(new DoubleSetting.Builder()
        .name("action-cooldown-variation")
        .description("Varies the cooldown between actions.")
        .defaultValue(100)
        .min(1)
        .sliderMax(500)
        .build()
    );

    private final Setting<Double> maxActionPerMinute = sgAnticheat.add(new DoubleSetting.Builder()
        .name("max-action-per-minute")
        .description("Sets a cap on the number of actions per minute.")
        .defaultValue(300)
        .min(10)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Double> detectionCooldown = sgAnticheat.add(new DoubleSetting.Builder()
        .name("detection-cooldown")
        .description("Sets a cooldown after a suspected detection.")
        .defaultValue(10000)
        .min(0)
        .sliderMax(60000)
        .build()
    );

    private final Setting<FailSafeMode> failSafeMode = sgAnticheat.add(new EnumSetting.Builder<FailSafeMode>() 
            .name("fail-safe-mode")
            .description("Stops all actions if detection is suspected.")
            .defaultValue(FailSafeMode.Shutdown)
            .build()
    );

    public final Setting<Boolean> pingSpoofing = sgAnticheat.add(new BoolSetting.Builder()
        .name("ping-spoofing")
        .description("Simulates different ping levels to avoid detection.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> serverLagSimulation = sgAnticheat.add(new DoubleSetting.Builder()
        .name("server-lag-simulation")
        .description("Adds artificial lag to simulate server delay.")
        .defaultValue(100)
        .min(0)
        .sliderMax(500)
        .build()
    );

    private final Setting<Double> packetDelay = sgAnticheat.add(new DoubleSetting.Builder()
        .name("packet-delay")
        .description("Introduces a delay between sending packets.")
        .defaultValue(50)
        .min(0)
        .sliderMax(200)
        .build()
    );

    private final Setting<ActionLogLevelMode> actionLogLevelMode = sgAnticheat.add(new EnumSetting.Builder<ActionLogLevelMode>() 
            .name("action-log-level-mode")
            .description("Log everything to your console.")
            .defaultValue(ActionLogLevelMode.Minimal)
            .build()
    );

    private final Setting<Double> monitoringFrequency = sgAnticheat.add(new DoubleSetting.Builder()
        .name("monitoring-frequency")
        .description("Frequency of checking for anticheat detection.")
        .defaultValue(10)
        .min(1)
        .sliderMax(60)
        .build()
    );

    private final Setting<Double> anomalyDetectionSensitivity = sgAnticheat.add(new DoubleSetting.Builder()
        .name("anomaly-detection-sensitivity")
        .description("Sensitivity to detect unusual patterns.")
        .defaultValue(70)
        .min(1)
        .sliderMax(100)
        .build()
    );

    public final Setting<Boolean> anticheatCommunitcation = sgAnticheat.add(new BoolSetting.Builder()
        .name("anticheat-communication")
        .description("Communicates with anticheat systems to appear legitimate.")
        .defaultValue(false)
        .build()
    );

    private final Setting<FakelagMode> fakelagMode = sgAnticheat.add(new EnumSetting.Builder<FakelagMode>() 
            .name("fakelag-mode")
            .description("Cause lag to prevent anticheat detections.")
            .defaultValue(FakelagMode.None)
            .build()
    );

    private final Setting<Double> pingVariation = sgAnticheat.add(new DoubleSetting.Builder()
        .name("ping-variation")
        .description("Adds random variation to the ping.")
        .defaultValue(50)
        .min(0)
        .sliderMax(200)
        .build()
    );

    public final Setting<Boolean> autoAdjustBasedOnDetection = sgAnticheat.add(new BoolSetting.Builder()
        .name("auto-adjust-based-on-detection")
        .description("Automatically adjusts settings if detection is suspected")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> dynamicRandomization = sgAnticheat.add(new DoubleSetting.Builder()
        .name("dynamic-randomization")
        .description("Changes the level of randomization dynamically.")
        .defaultValue(50)
        .min(0)
        .sliderMax(100)
        .build()
    );

    private final Setting<Double> detectionResponceDelay = sgAnticheat.add(new DoubleSetting.Builder()
        .name("detection-response-delay")
        .description("Delay before responding to a suspected detection.")
        .defaultValue(1000)
        .min(0)
        .sliderMax(5000)
        .build()
    );

    public CrystalAuraV5() {
        super(Addon.CATEGORY, "Crystal Aura V5", "An improved version of Crystal Aura.");
    }
}
