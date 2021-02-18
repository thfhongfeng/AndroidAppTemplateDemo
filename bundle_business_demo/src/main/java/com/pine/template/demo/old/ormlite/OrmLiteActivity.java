package com.pine.template.demo.old.ormlite;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pine.template.demo.R;
import com.pine.template.demo.util.ConsoleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class OrmLiteActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "OrmLiteActivity";

    private PersonDao mPersonDao;
    private ArticleDao mArticleDao;

    private Spinner mTableSpinner;
    private List<String> mTableList = new ArrayList<String>();
    private List<RelativeLayout> mTableLayoutList = new ArrayList<RelativeLayout>();

    private ScrollView mConsoleSv;
    private TextView mConsoleTv;
    private Button mClearConsoleBtn;

    private EditText mAddPersonNameEt;
    private EditText mAddPersonAgeEt;
    private Button mAddPersonBtn;
    private EditText mQueryPersonIdEt;
    private EditText mQueryPersonNameEt;
    private EditText mQueryPersonAgeEt;
    private Button mQueryPersonBtn;
    private EditText mUpdatePersonIdEt;
    private EditText mUpdatePersonNameEt;
    private EditText mUpdatePersonAgeEt;
    private Button mUpdatePersonBtn;
    private EditText mDelPersonIdEt;
    private EditText mDelPersonNameEt;
    private EditText mDelPersonAgeEt;
    private Button mDelPersonBtn;
    private Button mQueryAllPersonBtn;
    private Button mDelAllPersonBtn;

    private EditText mAddArticleTitleEt;
    private EditText mAddArticleAuthorEt;
    private Button mAddArticleBtn;
    private EditText mQueryArticleIdEt;
    private EditText mQueryArticleTitleEt;
    private EditText mQueryArticleAuthorEt;
    private Button mQueryArticleBtn;
    private EditText mUpdateArticleIdEt;
    private EditText mUpdateArticleTitleEt;
    private EditText mUpdateArticleAuthorEt;
    private Button mUpdateArticleBtn;
    private EditText mDelArticleIdEt;
    private EditText mDelArticleTitleEt;
    private EditText mDelArticleAuthorEt;
    private Button mDelArticleBtn;
    private Button mQueryAllArticleBtn;
    private Button mDelAllArticleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_ormlite);

        mPersonDao = new PersonDao(this);
        mArticleDao = new ArticleDao(this);

        mTableSpinner = (Spinner) findViewById(R.id.table_spinner_ao);
        mTableList.add("Person");
        mTableList.add("Article");
        mTableLayoutList.add((RelativeLayout) findViewById(R.id.person_table_rl));
        mTableLayoutList.add((RelativeLayout) findViewById(R.id.article_table_rl));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.demo_my_spinner_item, R.id.text, mTableList);
        mTableSpinner.setAdapter(arrayAdapter);
        mTableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = position % mTableLayoutList.size();
                for (int i = 0; i < mTableLayoutList.size(); i++) {
                    if (i == index) {
                        mTableLayoutList.get(i).setVisibility(View.VISIBLE);
                    } else {
                        mTableLayoutList.get(i).setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mConsoleSv = (ScrollView) findViewById(R.id.console_sv);
        mConsoleTv = (TextView) findViewById(R.id.console_tv);
        mClearConsoleBtn = (Button) findViewById(R.id.clear_btn_ao);
        mClearConsoleBtn.setOnClickListener(this);

        mAddPersonNameEt = (EditText) findViewById(R.id.add_person_name_et_ao);
        mAddPersonAgeEt = (EditText) findViewById(R.id.add_person_age_et_ao);
        mAddPersonBtn = (Button) findViewById(R.id.add_person_btn_ao);
        mQueryPersonIdEt = (EditText) findViewById(R.id.query_person_id_et_ao);
        mQueryPersonNameEt = (EditText) findViewById(R.id.query_person_name_et_ao);
        mQueryPersonAgeEt = (EditText) findViewById(R.id.query_person_age_et_ao);
        mQueryPersonBtn = (Button) findViewById(R.id.query_person_btn_ao);
        mUpdatePersonIdEt = (EditText) findViewById(R.id.update_person_id_et_ao);
        mUpdatePersonNameEt = (EditText) findViewById(R.id.update_person_name_et_ao);
        mUpdatePersonAgeEt = (EditText) findViewById(R.id.update_person_age_et_ao);
        mUpdatePersonBtn = (Button) findViewById(R.id.update_person_btn_ao);
        mDelPersonIdEt = (EditText) findViewById(R.id.delete_person_id_et_ao);
        mDelPersonNameEt = (EditText) findViewById(R.id.delete_person_name_et_ao);
        mDelPersonAgeEt = (EditText) findViewById(R.id.delete_person_age_et_ao);
        mDelPersonBtn = (Button) findViewById(R.id.delete_person_btn_ao);
        mQueryAllPersonBtn = (Button) findViewById(R.id.query_all_person_btn_ao);
        mDelAllPersonBtn = (Button) findViewById(R.id.delete_all_person_btn_ao);
        mAddPersonBtn.setOnClickListener(this);
        mQueryPersonBtn.setOnClickListener(this);
        mUpdatePersonBtn.setOnClickListener(this);
        mDelPersonBtn.setOnClickListener(this);
        mQueryAllPersonBtn.setOnClickListener(this);
        mDelAllPersonBtn.setOnClickListener(this);

        mAddArticleTitleEt = (EditText) findViewById(R.id.add_article_title_et_ao);
        mAddArticleAuthorEt = (EditText) findViewById(R.id.add_article_author_et_ao);
        mAddArticleBtn = (Button) findViewById(R.id.add_article_btn_ao);
        mQueryArticleIdEt = (EditText) findViewById(R.id.query_article_id_et_ao);
        mQueryArticleTitleEt = (EditText) findViewById(R.id.query_article_title_et_ao);
        mQueryArticleAuthorEt = (EditText) findViewById(R.id.query_article_author_et_ao);
        mQueryArticleBtn = (Button) findViewById(R.id.query_article_btn_ao);
        mUpdateArticleIdEt = (EditText) findViewById(R.id.update_article_id_et_ao);
        mUpdateArticleTitleEt = (EditText) findViewById(R.id.update_article_title_et_ao);
        mUpdateArticleAuthorEt = (EditText) findViewById(R.id.update_article_author_et_ao);
        mUpdateArticleBtn = (Button) findViewById(R.id.update_article_btn_ao);
        mDelArticleIdEt = (EditText) findViewById(R.id.delete_article_id_et_ao);
        mDelArticleTitleEt = (EditText) findViewById(R.id.delete_article_title_et_ao);
        mDelArticleAuthorEt = (EditText) findViewById(R.id.delete_article_author_et_ao);
        mDelArticleBtn = (Button) findViewById(R.id.delete_article_btn_ao);
        mQueryAllArticleBtn = (Button) findViewById(R.id.query_all_article_btn_ao);
        mDelAllArticleBtn = (Button) findViewById(R.id.delete_all_article_btn_ao);
        mAddArticleBtn.setOnClickListener(this);
        mQueryArticleBtn.setOnClickListener(this);
        mUpdateArticleBtn.setOnClickListener(this);
        mDelArticleBtn.setOnClickListener(this);
        mQueryAllArticleBtn.setOnClickListener(this);
        mDelAllArticleBtn.setOnClickListener(this);

//        if(BuildConfig.FORBIDDEN_SOFTKEYBOARD){
//            mAddPersonNameEt.setInputType(InputType.TYPE_NULL);
//            mAddPersonAgeEt.setInputType(InputType.TYPE_NULL);
//            mQueryPersonIdEt.setInputType(InputType.TYPE_NULL);
//            mQueryPersonNameEt.setInputType(InputType.TYPE_NULL);
//            mQueryPersonAgeEt.setInputType(InputType.TYPE_NULL);
//            mUpdatePersonIdEt.setInputType(InputType.TYPE_NULL);
//            mUpdatePersonNameEt.setInputType(InputType.TYPE_NULL);
//            mUpdatePersonAgeEt.setInputType(InputType.TYPE_NULL);
//            mDelPersonIdEt.setInputType(InputType.TYPE_NULL);
//            mDelPersonNameEt.setInputType(InputType.TYPE_NULL);
//            mDelPersonAgeEt.setInputType(InputType.TYPE_NULL);
//
//            mAddArticleTitleEt.setInputType(InputType.TYPE_NULL);
//            mAddArticleAuthorEt.setInputType(InputType.TYPE_NULL);
//            mQueryArticleIdEt.setInputType(InputType.TYPE_NULL);
//            mQueryArticleTitleEt.setInputType(InputType.TYPE_NULL);
//            mQueryArticleAuthorEt.setInputType(InputType.TYPE_NULL);
//            mUpdateArticleIdEt.setInputType(InputType.TYPE_NULL);
//            mUpdateArticleTitleEt.setInputType(InputType.TYPE_NULL);
//            mUpdateArticleAuthorEt.setInputType(InputType.TYPE_NULL);
//            mDelArticleIdEt.setInputType(InputType.TYPE_NULL);
//            mDelArticleTitleEt.setInputType(InputType.TYPE_NULL);
//            mDelArticleAuthorEt.setInputType(InputType.TYPE_NULL);
//        }

    }

    @Override
    public void onClick(View v) {
        if (v == mClearConsoleBtn) {
            ConsoleUtils.clear(mConsoleTv);
        } else if (v == mAddPersonBtn) {
            String name = mAddPersonNameEt.getText().toString();
            String age = mAddPersonAgeEt.getText().toString();
            String consoleOut = mPersonDao.addPerson(name, age);
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, consoleOut);
            mAddPersonNameEt.setText("");
            mAddPersonAgeEt.setText("");
        } else if (v == mQueryPersonBtn) {
            String id = mQueryPersonIdEt.getText().toString();
            String name = mQueryPersonNameEt.getText().toString();
            String age = mQueryPersonAgeEt.getText().toString();
            String consoleOut = mPersonDao.queryPerson(id, name, age);
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, consoleOut);
            mQueryPersonIdEt.setText("");
            mQueryPersonNameEt.setText("");
            mQueryPersonAgeEt.setText("");
        } else if (v == mUpdatePersonBtn) {
            String id = mUpdatePersonIdEt.getText().toString();
            String name = mUpdatePersonNameEt.getText().toString();
            String age = mUpdatePersonAgeEt.getText().toString();
            //String consoleOut = updatePerson(id, name, age);
            String consoleOut = mPersonDao.updatePersonBuilder(id, name, age);
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, consoleOut);
            mUpdatePersonIdEt.setText("");
            mUpdatePersonNameEt.setText("");
            mUpdatePersonAgeEt.setText("");
        } else if (v == mDelPersonBtn) {
            String id = mDelPersonIdEt.getText().toString();
            String name = mDelPersonNameEt.getText().toString();
            String age = mDelPersonAgeEt.getText().toString();
            String consoleOut = mPersonDao.deletePerson(id, name, age);
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, consoleOut);
            mDelPersonIdEt.setText("");
            mDelPersonNameEt.setText("");
            mDelPersonAgeEt.setText("");
        } else if (v == mQueryAllPersonBtn) {
            String consoleOut = mPersonDao.queryAllPerson();
            ConsoleUtils.out(mConsoleSv, mConsoleTv, consoleOut);
        } else if (v == mDelAllPersonBtn) {
            String consoleOut = mPersonDao.deleteAllPerson();
            ConsoleUtils.out(mConsoleSv, mConsoleTv, consoleOut);
        } else if (v == mAddArticleBtn) {
            String title = mAddArticleTitleEt.getText().toString();
            String authorId = mAddArticleAuthorEt.getText().toString();
            String consoleOut = mArticleDao.addArticle(title, authorId);
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, consoleOut);
            mAddArticleTitleEt.setText("");
            mAddArticleAuthorEt.setText("");
        } else if (v == mQueryArticleBtn) {
            String id = mQueryArticleIdEt.getText().toString();
            String title = mQueryArticleTitleEt.getText().toString();
            String authorId = mQueryArticleAuthorEt.getText().toString();
            String consoleOut = mArticleDao.queryArticle(id, title, authorId);
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, consoleOut);
            mQueryArticleIdEt.setText("");
            mQueryArticleTitleEt.setText("");
            mQueryArticleAuthorEt.setText("");
        } else if (v == mUpdateArticleBtn) {
            String id = mUpdateArticleIdEt.getText().toString();
            String title = mUpdateArticleTitleEt.getText().toString();
            String authorId = mUpdateArticleAuthorEt.getText().toString();
            //String consoleOut = mArticleDao.updateArticle(id, title, authorId);
            String consoleOut = mArticleDao.updateArticleBuilder(id, title, authorId);
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, consoleOut);
            mUpdateArticleIdEt.setText("");
            mUpdateArticleTitleEt.setText("");
            mUpdateArticleAuthorEt.setText("");
        } else if (v == mDelArticleBtn) {
            String id = mDelArticleIdEt.getText().toString();
            String title = mDelArticleTitleEt.getText().toString();
            String authorId = mDelArticleAuthorEt.getText().toString();
            String consoleOut = mArticleDao.deleteArticle(id, title, authorId);
            ConsoleUtils.outAppend(mConsoleSv, mConsoleTv, consoleOut);
            mDelArticleIdEt.setText("");
            mDelArticleTitleEt.setText("");
            mDelArticleAuthorEt.setText("");
        } else if (v == mQueryAllArticleBtn) {
            String consoleOut = mArticleDao.queryAllArticle();
            ConsoleUtils.out(mConsoleSv, mConsoleTv, consoleOut);
        } else if (v == mDelAllArticleBtn) {
            String consoleOut = mArticleDao.deleteAllArticle();
            ConsoleUtils.out(mConsoleSv, mConsoleTv, consoleOut);
        }
    }
}
