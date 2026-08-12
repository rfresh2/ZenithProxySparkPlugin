package dev.zenith.spark;

import com.zenith.event.client.ClientBotTick;
import com.zenith.event.client.ClientTickEvent;
import me.lucko.spark.common.tick.SimpleTickReporter;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.rfresh2.EventConsumer.of;
import static com.zenith.Globals.EVENT_BUS;

public class ZenithTickReporter extends SimpleTickReporter {
    private final AtomicBoolean botTicking = new AtomicBoolean(false);

    @Override
    public void start() {
        EVENT_BUS.subscribe(
            this,
            of(ClientTickEvent.class, Integer.MAX_VALUE, this::onStartTick),
            of(ClientTickEvent.class, Integer.MIN_VALUE, this::onEndTick),
            of(ClientBotTick.Starting.class, this::onBotTickStarting),
            of(ClientBotTick.Stopped.class, this::onBotTickStopped),
            of(ClientBotTick.class, Integer.MIN_VALUE, this::onBotTickEnd)
        );
    }

    @Override
    public void close() {
        EVENT_BUS.unsubscribe(this);
    }

    private void onStartTick(ClientTickEvent event) {
        onStart();
    }

    private void onEndTick(ClientTickEvent event) {
        // bot tick occurs after client tick
        // so the real end of the tick is at the end of the bot tick
        // but the bot may not always be ticking
        if (!botTicking.get()) {
            onEnd();
        }
    }

    private void onBotTickStarting(ClientBotTick.Starting event) {
        botTicking.set(true);
    }

    private void onBotTickStopped(ClientBotTick.Stopped event) {
        botTicking.set(false);
    }

    private void onBotTickEnd(ClientBotTick event) {
        onEnd();
    }
}
