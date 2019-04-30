/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shentu.lib_http.interceptor;

import com.shentu.lib_http.api.Constant;
import com.shentu.lib_tools.MLog;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Invocation;


public final class LoggingInterceptor implements Interceptor {

    private static final String TAG = Constant.HTTP_TAG;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {

        NONE,

        BASIC,

        HEADERS,

        BODY
    }

    public LoggingInterceptor() {

    }


    private volatile Level level = Level.NONE;

    private String getTag(Request request) {
        Invocation tag = request.tag(Invocation.class);
        if (tag == null) {
            return "";
        }
        Method method = tag.method();
        return method.getName();
    }

    /**
     * Change the level at which this interceptor logs.
     */
    public LoggingInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        String requestStartMessage = "Request-->::【 "
                + request.url()
                + ", interface: " + getTag(request) + "(),"
                + " body: ";
        if (logHeaders) {
            if (!logBody || !hasRequestBody) {
                MLog.i(TAG, requestStartMessage + "null】");
            } else if (bodyHasUnknownEncoding(request.headers())) {
                MLog.i(TAG, requestStartMessage + "encoded body omitted】");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (isPlaintext(buffer)) {
                    String bufferString = buffer.readString(charset);
                    MLog.i(TAG, requestStartMessage + bufferString + "】");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            MLog.e(TAG, "Response::error【 " + e + " 】");
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        String msg = "Response->::【 "
                + response.request().url()
                + ", interface: " + getTag(response.request()) + "(), "
                + response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + " (" + tookMs + "ms)" + ", "
                + " response:";

        if (logHeaders) {
            if (!logBody || !HttpHeaders.hasBody(response)) {
                MLog.i(TAG, msg + "null 】");
            } else if (bodyHasUnknownEncoding(response.headers())) {
                MLog.i(TAG, msg + "encoded body omitted 】");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (contentLength != 0) {
                    MLog.i(TAG, msg + buffer.clone().readString(charset) + " 】");
                }
            }
        }

        return response;
    }


    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private static boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }
}
