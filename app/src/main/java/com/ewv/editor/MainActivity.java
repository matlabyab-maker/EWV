package com.ewv.editor;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int PICK_MEDIA = 1001;
    private TextView playerHint;
    private boolean playing = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerHint = findViewById(R.id.playerHint);

        View.OnClickListener importAction = v -> openMediaPicker();
        findViewById(R.id.toolMedia).setOnClickListener(importAction);
        findViewById(R.id.importButton).setOnClickListener(importAction);

        findViewById(R.id.playButton).setOnClickListener(v -> {
            playing = !playing;
            ((Button)v).setText(playing ? "Ⅱ" : "▶");
            Toast.makeText(this, playing ? "پخش‌کننده در حالت آماده‌سازی است" : "توقف", Toast.LENGTH_SHORT).show();
        });

        bindTool(R.id.toolCut, "برش", "ابزار برش در مرحله بعدی پیاده‌سازی می‌شود.");
        bindTool(R.id.toolTrim, "کوتاه‌سازی", "ابزار کوتاه‌سازی در مرحله بعدی پیاده‌سازی می‌شود.");
        bindTool(R.id.toolCrop, "قاب‌بندی", "ابزار قاب‌بندی در مرحله بعدی پیاده‌سازی می‌شود.");
        bindTool(R.id.toolText, "متن", "ابزار متن در مرحله بعدی پیاده‌سازی می‌شود.");
        bindTool(R.id.toolAudio, "صدا", "ابزار صدا در مرحله بعدی پیاده‌سازی می‌شود.");
        bindTool(R.id.toolFilter, "فیلتر", "ابزار فیلتر در مرحله بعدی پیاده‌سازی می‌شود.");
        bindTool(R.id.toolSpeed, "سرعت", "تنظیم سرعت در مرحله بعدی پیاده‌سازی می‌شود.");
        bindTool(R.id.toolOverlay, "لایه", "مدیریت لایه‌ها در مرحله بعدی پیاده‌سازی می‌شود.");
        bindTool(R.id.toolExport, "خروجی", "موتور رندر خروجی هنوز پیاده‌سازی نشده است.");
        bindTool(R.id.undoButton, "واگرد", "تاریخچه تغییرات در مرحله بعدی اضافه می‌شود.");
        bindTool(R.id.redoButton, "ازنو", "تاریخچه تغییرات در مرحله بعدی اضافه می‌شود.");
        bindTool(R.id.fitButton, "نمایش", "حالت نمایش در مرحله بعدی اضافه می‌شود.");
        bindTool(R.id.layersButton, "لایه‌ها", "پنجره لایه‌ها در مرحله بعدی اضافه می‌شود.");
        bindTool(R.id.projectButton, "پروژه", "مدیریت پروژه در مرحله بعدی اضافه می‌شود.");
        bindTool(R.id.settingsButton, "تنظیمات", "تنظیمات در مرحله بعدی اضافه می‌شود.");
    }

    private void bindTool(int id, String title, String message) {
        findViewById(id).setOnClickListener(v -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    private void openMediaPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"video/*", "audio/*", "image/*"});
        intent.setType("*/*");
        startActivityForResult(intent, PICK_MEDIA);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MEDIA && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                playerHint.setText("رسانه انتخاب شد؛ اتصال به موتور پخش در مرحله بعدی انجام می‌شود");
                Toast.makeText(this, "رسانه انتخاب شد", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
