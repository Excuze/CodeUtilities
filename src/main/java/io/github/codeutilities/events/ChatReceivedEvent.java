package io.github.codeutilities.events;

import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.util.ChatType;
import io.github.codeutilities.util.ChatUtil;
import io.github.codeutilities.util.DFInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ChatReceivedEvent {

    public static int rejoinStep = 0;
    public static int pjoinStep = 0;

    public static void onMessage(Text message, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        String text = message.getString();

        boolean cancel = false;

        if (mc.player == null) {
            return;
        }

        //PJoin command
        if (pjoinStep > 0) {
            cancel = true;
            if (text.contains("          ")) {
                pjoinStep--;
            }else if (pjoinStep == 1) {
                if (text.contains("is currently at §6spawn§e:")) {
                    ChatUtil.sendMessage("This player is not in a plot.", ChatType.FAIL);
                }else {
                    try {
                        String cmd = "/join " + (StringUtils.substringBetween(text, "[§7", "§8]"));
                        if (cmd.matches("/join [0-9]+")) {
                            mc.player.sendChatMessage(cmd);
                        } else {
                            ChatUtil.sendMessage("Error while trying to join the plot.", ChatType.FAIL);
                            pjoinStep = 0;
                        }
                    }catch (Exception e) {
                        ChatUtil.sendMessage("Error while trying to join the plot.", ChatType.FAIL);
                        e.printStackTrace();
                        pjoinStep = 0;
                    }
                }
            }
        }

        //Cancelling (set cancel to true)
        if (cancel) {
            CodeUtilities.log(Level.INFO, "[CANCELLED] " + text);
            ci.cancel();
        }
    }
}
