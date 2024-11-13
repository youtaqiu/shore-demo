package sh.rime.demo;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class ApplicationTest {

    @Test
    void testMainMethod() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            // 调用 main 方法
            Application.main(new String[]{});

            // 验证 SpringApplication.run 被调用一次，参数为 Application.class 和传入的 args
            mockedSpringApplication.verify(() -> SpringApplication.run(eq(Application.class),
                    Mockito.any(String[].class)), times(1));
        }
    }
}
