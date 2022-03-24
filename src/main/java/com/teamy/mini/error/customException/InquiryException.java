package com.teamy.mini.error.customException;

import com.teamy.mini.error.ErrorCode;

public class InquiryException extends RuntimeException {
    public InquiryException(String message) { super(message); }
    public ErrorCode getErrorCode() {
        return ErrorCode.INQUIRY_NO_RESULT;
    }
}
