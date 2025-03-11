package dev.zenith.spark;

import com.zenith.event.module.ClientTickEvent;
import me.lucko.spark.common.tick.AbstractTickHook;

import static com.github.rfresh2.EventConsumer.of;
import static com.zenith.Shared.EVENT_BUS;

public class ZenithTickHook extends AbstractTickHook {

    private void onClientTick(ClientTickEvent event) {
        super.onTick();
    }

    @Override
    public void start() {
        EVENT_BUS.subscribe(
            this,
            of(ClientTickEvent.class, this::onClientTick)
        );
    }

    @Override
    public void close() {
        EVENT_BUS.unsubscribe(this);
    }
}
