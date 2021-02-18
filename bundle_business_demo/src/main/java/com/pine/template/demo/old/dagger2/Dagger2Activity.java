package com.pine.template.demo.old.dagger2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pine.template.demo.R;
import com.pine.template.demo.util.ConsoleUtils;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class Dagger2Activity extends AppCompatActivity {
    private final static String TAG = "Dagger2Activity";

    private ScrollView mConsoleSv;
    private TextView mConsoleTv;
    private Button mNormalBtn;
    private Button mDependencyBtn;
    private Button mSubBtn;
    private Button mQualifierBtn;
    private Button mScopeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_dagger2);

        mConsoleSv = (ScrollView) findViewById(R.id.console_sv);
        mConsoleTv = (TextView) findViewById(R.id.console_tv);
        mNormalBtn = (Button) findViewById(R.id.normal_btn_ad);
        mDependencyBtn = (Button) findViewById(R.id.dependency_btn_ad);
        mSubBtn = (Button) findViewById(R.id.sub_btn_ad);
        mQualifierBtn = (Button) findViewById(R.id.qualifier_btn_ad);
        mScopeBtn = (Button) findViewById(R.id.scope_btn_ad);

        final NormalComponent normalComponent = DaggerNormalComponent.builder().normalModule(new NormalModule()).build();
        final ParentComponent parentComponent = DaggerParentComponent.builder().parentModule(new ParentModule()).build();
        final DependencyComponent dependencyComponent = DaggerDependencyComponent.builder().parentComponent(parentComponent).dependencyModule(new DependencyModule()).build();
        final SubComponent subComponent = parentComponent.createSubComponent(new SubModule());
        final QualifierComponent qualifierComponent = DaggerQualifierComponent.builder().qualifierModule(new QualifierModule()).build();
        final ScopeComponent scopeComponent = DaggerScopeComponent.builder().scopeModule(new ScopeModule()).build();

        mNormalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalInjectee normalInjectee = new NormalInjectee(normalComponent);
                String injectTrace = normalInjectee.getInjectTrace();
                ConsoleUtils.out(mConsoleSv, mConsoleTv, injectTrace);
            }
        });
        mDependencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DependencyInjectee dependencyInjectee = new DependencyInjectee(dependencyComponent);
                String injectTrace = dependencyInjectee.getInjectTrace();
                ConsoleUtils.out(mConsoleSv, mConsoleTv, injectTrace);
            }
        });
        mSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubInjectee subInjectee = new SubInjectee(subComponent);
                String injectTrace = subInjectee.getInjectTrace();
                ConsoleUtils.out(mConsoleSv, mConsoleTv, injectTrace);
            }
        });
        mQualifierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QualifierInjectee qualifierInjectee = new QualifierInjectee(qualifierComponent);
                String injectTrace = qualifierInjectee.getInjectTrace();
                ConsoleUtils.out(mConsoleSv, mConsoleTv, injectTrace);
            }
        });
        mScopeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScopeInjectee scopeInjectee = new ScopeInjectee(scopeComponent);
                String injectTrace = scopeInjectee.getInjectTrace();
                ConsoleUtils.out(mConsoleSv, mConsoleTv, injectTrace);
            }
        });
    }
}
