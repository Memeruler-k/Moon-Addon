package com.moon.addon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class CrashCommand extends Command {
    public CrashCommand() {
        super("Crash", "Ultimate methods to crash servers.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("Please choose server crash method first before using.");
            return SINGLE_SUCCESS;
        });
    }
}
