package com.moon.addon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class AnticheatsCommand extends Command {
    public AnticheatsCommand() {
        super("Anticheats", "Tells you what anticheat the server is using.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("Checking Server Anticheats.");
            return SINGLE_SUCCESS;
        });
    }
}
