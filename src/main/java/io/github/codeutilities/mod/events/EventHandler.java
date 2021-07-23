package io.github.codeutilities.mod.events;

import io.github.codeutilities.sys.commands.IManager;
import io.github.codeutilities.mod.events.register.ChangeStateEvent;
import io.github.codeutilities.mod.events.register.ReceiveChatMessageEvent;
import io.github.codeutilities.mod.events.register.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class EventHandler implements IManager<Object> {
    private final List<Object> registeredEvents = new ArrayList<>();

    @Override
    public void initialize() {
        register(
                new ReceiveChatMessageEvent(),
                new ChangeStateEvent(),
                new TickEvent()
        );
    }

    @Override
    public void register(Object object) {
        registeredEvents.add(object);
    }

    public void register(Object... objects) {
        for (Object object : objects) register(object);
    }

    @Override
    public List<Object> getRegistered() {
        return registeredEvents;
    }
}