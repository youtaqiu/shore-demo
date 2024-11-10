package sh.rime.demo.event.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sh.rime.reactor.rabbitmq.message.QueueEvent;

import static sh.rime.demo.event.message.EventQueueNames.DEMO_EVENT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DemoMessage extends QueueEvent {

    private String message;

    @Override
    public String getQueue() {
        return DEMO_EVENT;
    }
}
