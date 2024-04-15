package com.imark.printapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Base64;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.printapp.R;
import com.imark.printapp.Utils.ViewUtils;
import com.imark.printapp.bitmap.CreateBitmap;
import com.imark.printapp.service.PrintConnectionService;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private Button bPrint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrintConnectionService.myInstance().init(this);
        PrintConnectionService.myInstance().bindService();
        setContentView(R.layout.activity_main);
        bPrint = findViewById(R.id.btn_print);
        bPrint.setOnClickListener(view -> {
            if (ViewUtils.isFastClick())
                return;

            printCall();
        });
    }

    private void printCall() {
        CreateBitmap createBitmap = new CreateBitmap();
        Bitmap bitmap = createBitmap.getBitmap();
        String base64Image = convertBitmapToBase64(bitmap);
        try {
            PrintConnectionService service = PrintConnectionService.myInstance();
            if (service == null || !PrintConnectionService.isServiceConnected) {
                ViewUtils.alertDialog(this, "PrintApp", "Service Bind Failed, Please assure Server Service is Running or Not!");
            } else {
                PrintConnectionService.myInstance().print(base64Image);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
