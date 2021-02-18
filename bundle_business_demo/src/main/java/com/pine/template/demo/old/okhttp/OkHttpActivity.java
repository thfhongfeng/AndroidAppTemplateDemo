package com.pine.template.demo.old.okhttp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pine.template.demo.R;
import com.pine.template.demo.util.ConsoleUtils;
import com.pine.tool.util.FileUtils;
import com.pine.tool.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class OkHttpActivity extends AppCompatActivity {
    private final static String TAG = "OkHttpActivity";

    private final static int SYNC_TYPE = 1;
    private final static int ASYNC_TYPE = 2;

    private final static int POST_FORM = 1;
    private final static int POST_UPDATE_FILE = 2;
    private final static int POST_DOWNLOAD_FILE = 3;

    private ScrollView mConsoleSv;
    private TextView mConsoleTv;
    private ProgressBar mLoadProgressBar;

    private OkHttpClientController mOkHttpClientController;

    private EditText mUrlEt;
    private ImageButton mCancelBtn;
    private TextView mFilePathTv;

    private String mPreUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_ok_http);

        mOkHttpClientController = OkHttpClientController.getInstance();

        mConsoleSv = (ScrollView) findViewById(R.id.console_sv);
        mConsoleTv = (TextView) findViewById(R.id.console_tv);
        mLoadProgressBar = (ProgressBar) findViewById(R.id.load_progressbar);

        mUrlEt = (EditText) findViewById(R.id.url_et_aoh);

        mFilePathTv = (TextView) findViewById(R.id.file_path_tv_aoh);

//        if (BuildConfig.FORBIDDEN_SOFTKEYBOARD) {
//            mUrlEt.setInputType(InputType.TYPE_NULL);
//        }

        mCancelBtn = (ImageButton) findViewById(R.id.clear_or_cancel_btn_aoh);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrlEt.setText("");
                mPreUrl = "";
                ConsoleUtils.clear(mConsoleTv);
            }
        });

        Button getBtn = (Button) findViewById(R.id.get_btn_aoh);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsoleUtils.out(mConsoleSv, mConsoleTv,
                        "We are going to the website, please wait ......");
                final String url = mUrlEt.getText().toString();
                if (checkUrl(url)) {
                    mPreUrl = url;
                    getByType(ASYNC_TYPE);
                }
            }
        });

        Button postBtn = (Button) findViewById(R.id.post_btn_aoh);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsoleUtils.out(mConsoleSv, mConsoleTv,
                        "We are posting the search key to the website, please wait ......");
                final String url = mUrlEt.getText().toString();
                if (checkUrl(url)) {
                    mPreUrl = url;
                    postByType(POST_FORM);
                }
            }
        });
        Button downloadBtn = (Button) findViewById(R.id.download_btn_aoh);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = mUrlEt.getText().toString();
                if (checkUrl(url)) {
                    mPreUrl = url;
                    postByType(POST_DOWNLOAD_FILE);
                }
            }
        });
        Button fileChooserBtn = (Button) findViewById(R.id.file_chooser_btn_aoh);
        fileChooserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grantExternalRW()) {
                    showFileChooser();
                }
            }
        });
        Button uploadBtn = (Button) findViewById(R.id.upload_btn_aoh);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = mUrlEt.getText().toString();
                if (checkUrl(url)) {
                    mPreUrl = url;
                    postByType(POST_UPDATE_FILE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUrlEt.setText("www.baidu.com");
    }

    private final static int PERMISSIONS_CODE = 1;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        // 授权成功后的逻辑
                        showFileChooser();
                    } else {
                        requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSIONS_CODE);
                    }
                }
            }
        }
    }

    public boolean grantExternalRW() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSIONS_CODE);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = FileUtils.getFileAbsolutePath(this, uri);
                    LogUtils.d(TAG, "uri: " + uri + ", path: " + path);
                    mFilePathTv.setText(path);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent.createChooser(intent, "Select a file to upload"),
                FILE_SELECT_CODE);
    }

    private final static int SHOW_IN_CONSOLE = 1;
    private final Handler mConsoleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            Bundle bundle = msg.getData();
            String content = bundle.getString("content");
            switch (what) {
                case SHOW_IN_CONSOLE:
                    ConsoleUtils.out(mConsoleSv, mConsoleTv, content);
                    break;
                default:
                    break;
            }
        }
    };

    private void sendFailedMessage(final Request request, final Exception e) {
        Message msg = Message.obtain();
        msg.what = SHOW_IN_CONSOLE;
        Bundle bundle = new Bundle();
        bundle.putString("content", e.toString());
        msg.setData(bundle);
        mConsoleHandler.sendMessage(msg);
    }

    private void sendSuccessMessage(final String response) {
        Message msg = Message.obtain();
        msg.what = SHOW_IN_CONSOLE;
        Bundle bundle = new Bundle();
        bundle.putString("content", response);
        msg.setData(bundle);
        mConsoleHandler.sendMessage(msg);
    }

    private void getByType(int type) {
        switch (type) {
            case SYNC_TYPE:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String response = OkHttpClientController.getSyncAsString(
                                    autoCompleteUrl(mPreUrl));
                            sendSuccessMessage(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                            sendFailedMessage(null, e);
                        }
                    }
                }.start();

                break;
            default:
                OkHttpClientController.getAsync(autoCompleteUrl(mPreUrl),
                        new OkHttpClientController.ResultCallback<String>() {
                            @Override
                            public void onError(Request request, Exception e) {
                                sendFailedMessage(request, e);
                            }

                            @Override
                            public void onResponse(String response) {
                                sendSuccessMessage(response);
                            }
                        });
                break;
        }
    }

    private void postByType(int type) {
        switch (type) {
            case POST_DOWNLOAD_FILE:
                String downloadPath = getCacheDir().getPath();
                OkHttpClientController.downloadAsync(autoCompleteUrl(mPreUrl), downloadPath,
                        new OkHttpClientController.ResultCallback<String>() {
                            @Override
                            public void onError(Request request, Exception e) {
                                sendFailedMessage(request, e);
                            }

                            @Override
                            public void onResponse(String response) {
                                sendSuccessMessage(FileUtils.readFile(response));
                            }
                        });
                break;
            case POST_FORM:
                mUrlEt.setText("https://en.wikipedia.org/w/index.php");
                mPreUrl = "https://en.wikipedia.org/w/index.php";
                String searchKey = ((EditText) findViewById(R.id.search_key_et_aoh))
                        .getText().toString();
                Map<String, String> params = new HashMap<String, String>();
                if (searchKey != null && !searchKey.isEmpty()) {
                    params.put("search", searchKey);
                } else {
                    sendFailedMessage(null, new Exception("Search key should not empty!"));
                    return;
                }
                OkHttpClientController.postAsync(autoCompleteUrl(mPreUrl),
                        new OkHttpClientController.ResultCallback<String>() {
                            @Override
                            public void onError(Request request, Exception e) {
                                sendFailedMessage(request, e);
                            }

                            @Override
                            public void onResponse(String response) {
                                sendSuccessMessage(response);
                            }
                        }, params);
                break;
            case POST_UPDATE_FILE:
                mUrlEt.setText("https://api.github.com/markdown/raw");
                mPreUrl = "https://api.github.com/markdown/raw";
                // File uploadFile = new File(getCacheDir(), "readme.txt");
                String uploadFilePath = mFilePathTv.getText().toString();
                if (!new File(uploadFilePath).exists()) {
                    sendFailedMessage(null, new Exception("File is not exist!"));
                    return;
                }
                File uploadFile = new File(uploadFilePath);
                try {
                    // FileUtil.inputStreamToFile(getAssets().open("readme.txt"), uploadFile);
                    OkHttpClientController.postAsync(autoCompleteUrl(mPreUrl),
                            new OkHttpClientController.ResultCallback<String>() {
                                @Override
                                public void onError(Request request, Exception e) {
                                    sendFailedMessage(request, e);
                                }

                                @Override
                                public void onResponse(String response) {
                                    sendSuccessMessage(response);
                                }
                            }, uploadFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                mUrlEt.setText("https://en.wikipedia.org/w/index.php");
                mPreUrl = "https://en.wikipedia.org/w/index.php";
                OkHttpClientController.Param param = new OkHttpClientController.Param("search",
                        "world");
                OkHttpClientController.postAsync(autoCompleteUrl(mPreUrl),
                        new OkHttpClientController.ResultCallback<String>() {
                            @Override
                            public void onError(Request request, Exception e) {
                                sendFailedMessage(request, e);
                            }

                            @Override
                            public void onResponse(String response) {
                                sendSuccessMessage(response);
                            }
                        }, new OkHttpClientController.Param("search", "world"));
                break;
        }
    }

    private boolean checkUrl(String url) {
        if (url == null || url == "") {
            ConsoleUtils.out(mConsoleSv, mConsoleTv, "You must input a non-null website!");
            return false;
        }
        if (mPreUrl == url) {
            Toast.makeText(this, "The website is not change!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (HttpUrl.parse(autoCompleteUrl(url)) == null) {
            ConsoleUtils.out(mConsoleSv, mConsoleTv, "The url: "
                    + " format is incorrect!");
            return false;
        }
        return true;
    }

    private String autoCompleteUrl(String url) {
        String retUrl = url;
        if (retUrl.startsWith("http://") || retUrl.startsWith("https://")) {
            return retUrl;
        }
        return "http://" + url;
    }
}
