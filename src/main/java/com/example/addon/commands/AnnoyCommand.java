package com.moon.addon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class AnnoyCommand extends Command {
    public AnnoyCommand() {
        super("annoy", "Allows you to annoy players by repeating everything they say.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("Please enter player username to annoy.");
            return SINGLE_SUCCESS;
        });
    }
}
