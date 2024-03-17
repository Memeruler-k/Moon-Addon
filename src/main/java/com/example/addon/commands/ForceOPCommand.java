package com.moon.addon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class CommandExample extends Command {
    public CommandExample() {
        super("ForceOP", "Gives you Operator. ONLY WORKS IN CRACKED SERVERS.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("Succesfully gave you op!");
            return SINGLE_SUCCESS;
        });
    }
}
