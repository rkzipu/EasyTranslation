package test.com.easytranslation.networking;

import com.google.gson.annotations.SerializedName;

import test.com.easytranslation.home.model.TranslationResponse;

public class Response extends TranslationResponse{
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}
