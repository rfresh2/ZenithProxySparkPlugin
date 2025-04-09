package dev.zenith.spark;

import com.zenith.api.event.client.ClientTickEvent;
import me.lucko.spark.common.tick.SimpleTickReporter;

import static com.github.rfresh2.EventConsumer.of;
import static com.zenith.Globals.EVENT_BUS;

public class ZenithTickReporter extends SimpleTickReporter {
    @Override
    public void start() {
        EVENT_BUS.subscribe(
            this,
            of(ClientTickEvent.class, Integer.MAX_VALUE, this::onStartTick),
            of(ClientTickEvent.class, Integer.MIN_VALUE, this::onEndTick)
        );
    }

    @Override
    public void close() {
        EVENT_BUS.unsubscribe(this);
    }

    private void onEndTick(ClientTickEvent event) {
        onEnd();
    }

    private void onStartTick(ClientTickEvent event) {
        onStart();
    }
}
