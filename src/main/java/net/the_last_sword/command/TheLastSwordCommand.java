package net.the_last_sword.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class TheLastSwordCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("the_last_sword")
                .requires(net.minecraft.commands.Commands.hasPermission(net.minecraft.commands.Commands.LEVEL_GAMEMASTERS))
                //攻击系统子命令
                .then(AttackCommand.registerSubCommand())
                //防御系统子命令
                .then(DefenceCommand.registerSubCommand())
        );
    }
}
