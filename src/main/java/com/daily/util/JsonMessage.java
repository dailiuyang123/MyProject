package com.daily.util;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Azir on 2018/2/1.
 */
public class JsonMessage implements Serializable {

    private String responseCode="0";
    private String errorMessage;
    private Map data;



    public String getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public Map getData() {
        return data;
    }
    public void setData(Map data) {
        this.data = data;
    }

}
