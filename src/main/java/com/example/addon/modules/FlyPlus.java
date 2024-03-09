package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;

public class FlyPlus extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgBypass = settings.createGroup("Bypass");
    private final SettingGroup sgClient = settings.createGroup("Client");
    private final SettingGroup sgMovement = settings.createGroup("Movement");
    public FlyPlus() {
        super(Addon.CATEGORY, "Fly+", "Better fly with bypasses.");
    }
}
