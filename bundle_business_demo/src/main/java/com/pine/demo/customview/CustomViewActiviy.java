package com.pine.demo.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pine.demo.R;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class CustomViewActiviy extends AppCompatActivity {

    private CustomConsoleContainer mConsoleContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_activiy);

        mConsoleContainer = (CustomConsoleContainer) findViewById(R.id.console_container);

        Button showTvBtn = (Button) findViewById(R.id.show_tv_btn);
        showTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.changeConsoleMode(CustomConsoleContainer.TEXT_MODE);
            }
        });

        Button showImgBtn = (Button) findViewById(R.id.show_img_btn);
        showImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.changeConsoleMode(CustomConsoleContainer.IMAGE_MODE);
            }
        });

        Button addFire = (Button) findViewById(R.id.add_fire_btn);
        addFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.printlnAppended(CustomConsoleContainer.ITEM_FIRE);
            }
        });

        Button addComputer = (Button) findViewById(R.id.add_computer_btn);
        addComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.printlnAppended(CustomConsoleContainer.ITEM_COMPUTER);
            }
        });

        Button addTree = (Button) findViewById(R.id.add_tree_btn);
        addTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.printlnAppended(CustomConsoleContainer.ITEM_TREE);
            }
        });

        Button addStar = (Button) findViewById(R.id.add_star_btn);
        addStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.printlnAppended(CustomConsoleContainer.ITEM_STAR);
            }
        });

        Button addPhone = (Button) findViewById(R.id.add_phone_btn);
        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.printlnAppended(CustomConsoleContainer.ITEM_PHONE);
            }
        });

        Button addPadBtn = (Button) findViewById(R.id.add_pad_btn);
        addPadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.printlnAppended(CustomConsoleContainer.ITEM_PAD);
            }
        });

        Button changeOrientationBtn = (Button) findViewById(R.id.toggle_text_orientation_btn);
        changeOrientationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.toggleTextOrientationForImgMode();
            }
        });

        Button removeAllItemsBtn = (Button) findViewById(R.id.remove_all_items_btn);
        removeAllItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConsoleContainer.clearAll();
            }
        });
    }
}
