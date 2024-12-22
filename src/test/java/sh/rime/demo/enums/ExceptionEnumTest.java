package sh.rime.demo.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionEnumTest {

    @Test
    void testResourceFileTooLarge() {
        ExceptionEnum exceptionEnum = ExceptionEnum.RESOURCE_FILE_TOO_LARGE;
        assertEquals(2901, exceptionEnum.code());
        assertEquals("Resource file too large", exceptionEnum.message());
    }

    @Test
    void testUploadFileFailed() {
        ExceptionEnum exceptionEnum = ExceptionEnum.UPLOAD_FILE_FAILED;
        assertEquals(2902, exceptionEnum.code());
        assertEquals("Upload file failed", exceptionEnum.message());
    }
}
