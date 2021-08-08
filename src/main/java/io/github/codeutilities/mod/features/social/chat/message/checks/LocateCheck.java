package io.github.codeutilities.mod.features.social.chat.message.checks;

import io.github.codeutilities.mod.features.social.chat.message.Message;
import io.github.codeutilities.mod.features.social.chat.message.MessageCheck;
import io.github.codeutilities.mod.features.social.chat.message.MessageType;
import io.github.codeutilities.sys.networking.State;
import io.github.codeutilities.sys.player.DFInfo;

public class LocateCheck extends MessageCheck {

    @Override
    protected MessageType getType() {
        return MessageType.LOCATE;
    }

    @Override
    protected boolean check(Message message, String stripped) {
        return stripped.contains("\nYou are currently");
    }

    @Override
    protected void onReceive(Message message) {
        DFInfo.setCurrentState(State.fromLocate(message));
    }
}