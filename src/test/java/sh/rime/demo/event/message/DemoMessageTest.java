package sh.rime.demo.event.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sh.rime.demo.event.message.EventQueueNames.DEMO_EVENT;

class DemoMessageTest {

    @Test
    public void testGetQueue() {
        DemoMessage demoMessage = new DemoMessage("Hello, world!");
        assertEquals(DEMO_EVENT, demoMessage.getQueue());
    }
}
