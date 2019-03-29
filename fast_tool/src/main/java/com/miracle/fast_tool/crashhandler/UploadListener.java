package com.miracle.fast_tool.crashhandler;

public interface UploadListener<Request, Result> {
    void progress(int present);
    void success(Request request, Result result);
    void failure(Request request, Exception clientException, Exception serviceException);
}