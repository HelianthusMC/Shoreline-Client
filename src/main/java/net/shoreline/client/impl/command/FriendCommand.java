package net.shoreline.client.impl.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;
import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.PlayerArgumentType;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.chat.ChatUtil;

/**
 * @author linus
 * @since 1.0
 */
public class FriendCommand extends Command {

    /**
     *
     */
    public FriendCommand() {
        super("Friend", "Adds/Removes a friend from the player list", literal("friend"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("add/del", StringArgumentType.string()).suggests(suggest("add", "del", "remove"))
                .then(argument("friend_name", PlayerArgumentType.player()).executes(c -> {
                    String playerName = PlayerArgumentType.getPlayer(c, "friend_name");
                    final String action = StringArgumentType.getString(c, "add/del");
                    if (action.equalsIgnoreCase("add")) {
                        ChatUtil.clientSendMessage("§sAdded friend with name§f " + playerName);
                        Managers.SOCIAL.addFriend(playerName);
                    } else if (action.equalsIgnoreCase("remove")
                            || action.equalsIgnoreCase("del")) {
                        ChatUtil.clientSendMessage("§sRemoved friend with name§f " + playerName);
                        Managers.SOCIAL.remove(playerName);
                    }
                    return 1;
                })).executes(c -> {
                    ChatUtil.error("Must provide player to friend!");
                    return 1;
                })).executes(c -> {
                    ChatUtil.error("Invalid usage! Usage: " + getUsage());
                    return 1;
                });
    }
}
