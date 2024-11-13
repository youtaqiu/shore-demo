package sh.rime.demo.event.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventQueueNamesTest {


    @Test
    void testDemoEventConstant() {
        // 引用 EventQueueNames 类，确保其被加载
        assertEquals("demo_event", EventQueueNames.DEMO_EVENT, "DEMO_EVENT 常量值应为 'demo_event'");

        // 确保 EventQueueNames 类加载后覆盖
        Class<?> clazz = EventQueueNames.class;
        assertEquals("EventQueueNames", clazz.getSimpleName());
    }
}
