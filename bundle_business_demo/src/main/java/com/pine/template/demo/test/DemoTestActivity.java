package com.pine.template.demo.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.pine.template.demo.R;

public class DemoTestActivity extends Activity {
    private TextView out_put_tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_console);

        out_put_tv = findViewById(R.id.out_put_tv);
        main();
    }

    private void output(String outputText) {
        out_put_tv.setText(outputText);
    }

    private void main() {
        output(getResources().getResourceName(out_put_tv.getId()));
    }
}
