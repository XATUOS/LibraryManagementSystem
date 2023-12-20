package dev.dubhe.libms.data;

public class RequestResult {
    public final int code;
    public final String msg;

    public RequestResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RequestResult success() {
        return new RequestResult(200, "success");
    }

    public static <T> DataRequestResult<T> success(T data) {
        return new DataRequestResult<>(200, "success", data);
    }

    public static class DataRequestResult<T> extends RequestResult {
        public final T data;

        public DataRequestResult(int code, String msg, T data) {
            super(code, msg);
            this.data = data;
        }
    }
}
