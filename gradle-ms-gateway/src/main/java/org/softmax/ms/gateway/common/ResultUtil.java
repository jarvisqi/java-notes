package org.softmax.ms.gateway.common;

public class ResultUtil {

    /**
     * 请求出错返回
     *
     * @param path
     * @param <T>
     * @return
     */
    public static <T> Result<T> buildError(String path) {
        return build(ApiConstant.ERROR_CODE, ApiConstant.ERROR_MESSAGE, path, null);
    }

    /**
     * 请求出错返回
     *
     * @param errorCode
     * @param message
     * @param path
     * @param <T>
     * @return
     */
    public static <T> Result<T> buildError(int errorCode, String message, String path) {
        return build(errorCode, message, path, null);
    }

    /**
     * 请求成功返回
     *
     * @param path
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> buildSuccess(String path, T data) {
        return build(ApiConstant.SUCCESS_CODE, ApiConstant.SUCCESS_MESSAGE, path, data);
    }


    /**
     * 构建返回对象方法
     *
     * @param code
     * @param message
     * @param path
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> build(Integer code, String message, String path, T data) {
        if (code == null) {
            code = ApiConstant.SUCCESS_CODE;
        }
        if (message == null) {
            message = ApiConstant.SUCCESS_MESSAGE;
        }
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        result.setPath(path);

        return result;
    }
}
