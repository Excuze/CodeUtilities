package me.reasonless.codeutilities.commands.nbs;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.realmsclient.gui.ChatFormatting;

import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import me.reasonless.codeutilities.util.TemplateNBT;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;

public class NBSPlayerCommand {
	public static int execute(MinecraftClient mc, CommandContext<CottonClientCommandSource> ctx) throws Exception {
		ItemStack stack = new ItemStack(Items.JUKEBOX);

		TemplateNBT.setTemplateNBTGZIP(stack, "Music Player", "CodeUtilities", "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");
		stack.setCustomName(new LiteralText(ChatFormatting.DARK_PURPLE + "" + ChatFormatting.BOLD + "MUSIC PLAYER PACK"));

		mc.interactionManager.clickCreativeStack(stack, 36 + mc.player.inventory.selectedSlot);
		
		mc.player.sendMessage(new LiteralText(ChatFormatting.GOLD + " - " + ChatFormatting.YELLOW + "You have been given a music player pack!"));
		mc.player.sendMessage(new LiteralText(ChatFormatting.GOLD + " - " + ChatFormatting.YELLOW + "Place this in your codespace and open the chest to get music functions!"));
		mc.player.sendMessage(new LiteralText(ChatFormatting.DARK_PURPLE + " - " + ChatFormatting.LIGHT_PURPLE + "If you don't know how to use, check our discord!"));
		mc.player.sendMessage(new LiteralText(ChatFormatting.DARK_AQUA + " - " + ChatFormatting.AQUA + "" + ChatFormatting.UNDERLINE + "https://discord.gg/QBmXaGb"));

		return 1;
	}
}
