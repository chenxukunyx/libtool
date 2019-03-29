package com.miracle.fast_tool.crashhandler;

import com.alibaba.sdk.android.oss.*;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.*;
import com.miracle.fast_tool.BaseApplication;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CarshUploadToAliyun {

    private OSS mOSSClient;
    private OSSCredentialProvider mCredentialProvider;
    private ClientConfiguration mClientConfiguration;
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    private CarshUploadToAliyun() {
        mCredentialProvider = new OSSPlainTextAKSKCredentialProvider(AliyunConfig.OSS_ACCESS_KEY_ID, AliyunConfig.OSS_ACCESS_KEY_SECRET);
        mClientConfiguration = new ClientConfiguration();
        mClientConfiguration.setConnectionTimeout(15 * 1000);
        mClientConfiguration.setSocketTimeout(15 * 1000);
        mClientConfiguration.setMaxConcurrentRequest(5);
        mOSSClient = new OSSClient(BaseApplication.getContext(), AliyunConfig.endpoint, mCredentialProvider, mClientConfiguration);
        OSSLog.enableLog();
        createBucket(new UploadListener<CreateBucketRequest, CreateBucketResult>() {
            @Override
            public void progress(int present) {

            }

            @Override
            public void success(CreateBucketRequest createBucketRequest, CreateBucketResult createBucketResult) {
                OSSLog.logInfo("create bucket success");
            }

            @Override
            public void failure(CreateBucketRequest createBucketRequest, Exception clientException, Exception serviceException) {
                OSSLog.logInfo("create bucket failure");
            }
        });
    }

    private static class Holder {
        private static CarshUploadToAliyun INSTANCE = new CarshUploadToAliyun();
    }

    public static CarshUploadToAliyun getInstance() {
        return CarshUploadToAliyun.Holder.INSTANCE;
    }

    private void createBucket(final UploadListener<CreateBucketRequest, CreateBucketResult> listener) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(AliyunConfig.bucket);

        // 指定Bucket的ACL权限。
        createBucketRequest.setBucketACL(CannedAccessControlList.Private);
        // 指定Bucket所在的数据中心。
        createBucketRequest.setLocationConstraint("oss-cn-hangzhou");

        // 异步创建存储空间。
        OSSAsyncTask createTask = mOSSClient.asyncCreateBucket(createBucketRequest, new OSSCompletedCallback<CreateBucketRequest, CreateBucketResult>() {
            @Override
            public void onSuccess(CreateBucketRequest request, CreateBucketResult result) {
                if (listener != null)
                    listener.success(request, result);
            }

            @Override
            public void onFailure(CreateBucketRequest request, ClientException clientException, ServiceException serviceException) {
                if (listener != null)
                    listener.failure(request, clientException, serviceException);

            }
        });
        createTask.waitUntilFinished();
    }

    public void putToAliyunAsync(final String path, final String objectName, final UploadListener<PutObjectRequest, PutObjectResult> listener) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                _putToAliyunAsync(path, objectName, listener);
            }
        });
    }

    private void _putToAliyunAsync(String path, String objectName, final UploadListener<PutObjectRequest, PutObjectResult> listener) {
        File file = new File(path);
        if (!file.exists()) {
            OSSLog.logInfo("current file is not exist...");
            return;
        }
        OSSLog.logInfo("create PutObjectRequest...");
        PutObjectRequest put = new PutObjectRequest(AliyunConfig.bucket, objectName, path);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                int present = (int) (currentSize * 100 / totalSize);
                OSSLog.logInfo(present + "%");
                if (listener != null)
                    listener.progress(present);
            }
        });

        OSSAsyncTask task = mOSSClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (listener != null)
                    listener.success(request, result);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                clientException.printStackTrace();
                serviceException.printStackTrace();
                if (listener != null)
                    listener.failure(request, clientException, serviceException);
            }
        });
        task.waitUntilFinished();
    }
}
