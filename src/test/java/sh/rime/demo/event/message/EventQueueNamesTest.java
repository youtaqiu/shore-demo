package sh.rime.demo.event.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventQueueNamesTest {

    @Test
    public void testDemoEvent() {
        assertEquals("demo_event", EventQueueNames.DEMO_EVENT);
    }
}
