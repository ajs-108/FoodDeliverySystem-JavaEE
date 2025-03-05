package util;

/**
 * APIResponse Contains the Response that is to be sent over as HttpResponse
 */
public class APIResponse {

    String message;
    Object data;

    public APIResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
