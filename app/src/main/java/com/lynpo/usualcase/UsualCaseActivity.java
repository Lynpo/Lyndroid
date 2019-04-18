package com.lynpo.usualcase;

import android.os.Bundle;
import android.util.Log;

import com.lynpo.R;
import com.lynpo.eternal.base.ui.BaseActivity;

import androidx.annotation.Nullable;


/**
 * UsualCaseActivity
 * *
 * Create by fujw on 2019/4/18.
 */
public class UsualCaseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emtpy);

        Parent parent = new Child();
        parent.function();
        // 输出:
        // D/DEBUG_TAG: parent:0
        // D/DEBUG_TAG: parent:0
        // D/DEBUG_TAG: child:0
        // D/DEBUG_TAG: child:0
        // 结论：子类中通过 super 调用父类方法，父类方法使用 return 结束后，不会终止子类重写的方法继续执行
    }

    static class Parent {
        void function() {
            int parent = 0;
            Log.d("DEBUG_TAG", "parent:" + parent);
            Log.d("DEBUG_TAG", "parent:" + parent);
            parent++;
            if (parent > 0) {
                return;
            }
            Log.d("DEBUG_TAG", "parent:" + parent);
        }
    }

    static class Child extends Parent {
        @Override
        void function() {
            super.function();
            int child = 0;
            Log.d("DEBUG_TAG", "child:" + child);
            Log.d("DEBUG_TAG", "child:" + child);
            child++;
            if (child > 0) {
                return;
            }
            Log.d("DEBUG_TAG", "child:" + child);
        }
    }
}
