package com.moon.addon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class AddaltCommand extends Command {
    public AddaltCommand() {
        super("addalt", "Adds a player to your alt manager list.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("Please type player name to add.");
            return SINGLE_SUCCESS;
        });
    }
}
