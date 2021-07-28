package com.yilan.sdk.sdkdemo.ad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yilan.sdk.common.ui.dialog.LoadingDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseAdActivity extends AppCompatActivity {
    protected LoadingDialog dialog;
    protected boolean succeed = false;
    protected AppCompatActivity context;
    protected StringBuffer buffer;
    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        dialog = new LoadingDialog(this);
    }

    private void append(String text) {
        if (buffer == null) {
            buffer = new StringBuffer();
        }
        buffer.append(text);
        buffer.append("\n");
    }

    protected void clearBuffer() {
        buffer = new StringBuffer();
    }

    private String getBufferString() {
        return buffer != null ? buffer.toString() : "";
    }

    public final void log(TextView textView, String log) {
        if (textView == null || log == null) {
            return;
        }
        append(format.format(new Date()) + " : " + log);
        textView.setText(getBufferString());
    }
}
