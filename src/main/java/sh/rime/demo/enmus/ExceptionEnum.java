package sh.rime.demo.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sh.rime.reactor.commons.exception.ServerFailure;

/**
 * 异常枚举
 *
 * @author youta
 **/
@AllArgsConstructor
@Getter
public enum ExceptionEnum implements ServerFailure {

    /**
     * resource file too large
     */
    RESOURCE_FILE_TOO_LARGE(2901, "Resource file too large"),

    /**
     * 上传文件失败
     */
    UPLOAD_FILE_FAILED(2902, "Upload file failed"),
    ;
    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String msg;

    /**
     * 错误码
     *
     * @return 错误码
     */
    @Override
    public int code() {
        return this.code;
    }

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    @Override
    public String message() {
        return this.msg;
    }

}
