package dto;

/**
 * APIResponse Contains the Response that is to be sent over as HttpResponse to client
 */
public class APIResponse {
    private String message;
    private Object data;

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
