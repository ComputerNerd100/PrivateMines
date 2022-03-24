package me.untouchedodin0.privatemines.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import me.untouchedodin0.privatemines.PrivateMines;
import me.untouchedodin0.privatemines.config.MineConfig;
import me.untouchedodin0.privatemines.factory.MineFactory;
import me.untouchedodin0.privatemines.mine.Mine;
import me.untouchedodin0.privatemines.storage.MineStorage;
import me.untouchedodin0.privatemines.type.MineType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("privatemines|pmines|pmine")
public class PrivateMinesCommand extends BaseCommand {

    PrivateMines privateMines;
    MineStorage mineStorage;

    public PrivateMinesCommand(PrivateMines privateMines) {
        this.privateMines = privateMines;
        this.mineStorage = privateMines.getMineStorage();
    }

    @Subcommand("give")
    @CommandCompletion("@players")
    public void give(Player player, Player target) {
        player.sendMessage("Giving " + target.getName() + " a private mine!");
        MineFactory mineFactory = new MineFactory();
        MineType mineType = MineConfig.mineTypes.get("Test");
        player.sendMessage("mine factory: " + mineFactory);
        mineFactory.create(player, player.getLocation(), mineType);
    }

    @Subcommand("delete")
    @CommandCompletion("@players")
    public void delete(Player player, Player target) {

        if (target != null) {
            if (!privateMines.getMineStorage().hasMine(target.getUniqueId())) {
                player.sendMessage("Player doesn't own a mine!");
            } else {
                Mine mine = privateMines.getMineStorage().get(target.getUniqueId());
                player.sendMessage("Deleting " + target.getName() + "'s private mine!");
                mine.delete();
                privateMines.getMineStorage().removeMine(target.getUniqueId());
                Bukkit.getLogger().info("mines: " + privateMines.getMineStorage().getMines());
            }
        }
    }

    @Subcommand("teleport")
    public void teleport(Player player) {
        if (!mineStorage.hasMine(player.getUniqueId())) {
            player.sendMessage("Player doesn't own a mine!");
        } else {
            Mine mine = mineStorage.get(player.getUniqueId());
            mine.teleport(player);
        }
    }

    @Subcommand("reset")
    public void reset(Player player) {
        if (!mineStorage.hasMine(player.getUniqueId())) {
            player.sendMessage("Player doesn't own a mine!");
        } else {
            Mine mine = mineStorage.get(player.getUniqueId());
            mine.reset();
        }
    }
}
