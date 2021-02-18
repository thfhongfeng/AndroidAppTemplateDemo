package com.pine.template.demo.old.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.pine.tool.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class OkHttpClientController {
    private final static String TAG = "OkHttpClientController";

    public static final boolean WITH_HEADER = true;

    private static OkHttpClientController mInstance;

    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    private OkHttpClientController() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public static OkHttpClientController getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientController.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientController();
                }
            }
        }
        return mInstance;
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    // ************* 对外公布的方法 ************


    public static Response getSync(String url) throws IOException {
        return getInstance()._getSync(url);
    }

    public static String getSyncAsString(String url) throws IOException {
        return getInstance()._getSyncAsString(url);
    }

    public static void getAsync(String url, ResultCallback callback) {
        getInstance()._getAsync(url, callback);
    }

    public static Response postSync(String url, Param... params) throws IOException {
        return getInstance()._postSync(url, params);
    }

    public static String postSyncAsString(String url, Param... params) throws IOException {
        return getInstance()._postSyncAsString(url, params);
    }

    public static void postAsync(String url, final ResultCallback callback, Param... params) {
        getInstance()._postAsync(url, callback, params);
    }

    public static void postAsync(String url, final ResultCallback callback,
                                 Map<String, String> params) {
        getInstance()._postAsync(url, callback, params);
    }

    public static Response postSync(String url, File[] files, String[] fileKeys,
                                    Param... params) throws IOException {
        return getInstance()._postSync(url, files, fileKeys, params);
    }

    public static Response postSync(String url, File file, String fileKey) throws IOException {
        return getInstance()._postSync(url, file, fileKey);
    }

    public static Response postSync(String url, File file, String fileKey,
                                    Param... params) throws IOException {
        return getInstance()._postSync(url, file, fileKey, params);
    }

    public static void postAsync(String url, ResultCallback callback, File[] files,
                                 String[] fileKeys, Param... params) throws IOException {
        getInstance()._postAsync(url, callback, files, fileKeys, params);
    }

    public static void postAsync(String url, ResultCallback callback, File file,
                                 String fileKey) throws IOException {
        getInstance()._postAsync(url, callback, file, fileKey);
    }

    public static void postAsync(String url, ResultCallback callback, File file) throws IOException {
        getInstance()._postAsync(url, callback, file);
    }

    public static void postAsync(String url, ResultCallback callback, File file,
                                 String fileKey, Param... params) throws IOException {
        getInstance()._postAsync(url, callback, file, fileKey, params);
    }

    public static void downloadAsync(String url, String destDir, ResultCallback callback) {
        getInstance()._downloadAsync(url, destDir, callback);
    }

    // ****************************

    /**
     * 同步的Get请求，返回Response
     *
     * @param url
     * @return Response
     */
    private Response _getSync(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步的Get请求，返回String
     *
     * @param url
     * @return 字符串
     */
    private String _getSyncAsString(String url) throws IOException {
        Response execute = _getSync(url);
        return execute.body().string();
    }

    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsync(String url, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }

    /**
     * 同步的Post请求，返回response
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _postSync(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 同步的Post请求，返回String
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postSyncAsString(String url, Param... params) throws IOException {
        Response response = _postSync(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsync(String url, final ResultCallback callback, Param... params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsync(String url, final ResultCallback callback,
                            Map<String, String> params) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * 同步基于post的文件上传
     *
     * @param params
     * @return
     */
    private Response _postSync(String url, File[] files, String[] fileKeys,
                               Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _postSync(String url, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file},
                new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _postSync(String url, File file, String fileKey,
                               Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file},
                new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsync(String url, ResultCallback callback, File[] files,
                            String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsync(String url, ResultCallback callback, File file,
                            String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file},
                new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param file
     * @throws IOException
     */
    private void _postAsync(String url, ResultCallback callback, File file) throws IOException {
        Request request = buildPostFileRequest(url, file);
        if (request != null) {
            deliveryResult(callback, request);
        } else {
            sendFailedStringCallback(null, new Exception("request is incorrect!"), callback);
        }
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsync(String url, ResultCallback callback, File file, String fileKey,
                            Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file},
                new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void _downloadAsync(final String url, final String destFileDir,
                                final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Call call, Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }

            }
        });
    }

    private void deliveryResult(final ResultCallback callback, Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                sendFailedStringCallback(call.request(), e, callback);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (WITH_HEADER) {
                        Headers responseHeaders = response.headers();
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            stringBuilder.append(responseHeaders.name(i))
                                    .append(": ")
                                    .append(responseHeaders.value(i))
                                    .append("\n");
                        }
                    }
                    final String string = stringBuilder.append(response.body().string()).toString();
                    if (callback.mType == String.class) {
                        sendSuccessResultCallback(string, callback);
                    } else {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }


                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);

                } catch (com.google.gson.JsonParseException e) {
                    //Json解析的错误
                    sendFailedStringCallback(response.request(), e, callback);
                }

            }
        });
    }

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private Request buildPostFileRequest(String url, File file) {
        if (file != null) {
            String fileName = file.getName();
            return new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse(guessMimeType(fileName)), file))
                    .build();
        }
        return null;
    }

    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params) {
        params = validateParam(params);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition",
                    "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        LogUtils.d(TAG, "guessMimeType contentTypeFor: " + contentTypeFor);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    private void setErrorResId(final ImageView view, final int errorResId) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(errorResId);
            }
        });
    }
}
