package com.imark.printapp.bitmap;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.imark.printapp.constants.PrintSize;

public class CreateBitmap {
    public Bitmap getBitmap() {
        BitmapDraw bitmapDraw = new BitmapDraw();
        bitmapDraw.text("Print Test", PrintSize.TYPE, true, Paint.Align.CENTER);
        // bitmapDraw.text(water(), PrintSize.TYPE, true, Paint.Align.CENTER);
        bitmapDraw.text("Name: Imark",null, PrintSize.SMALL, false);
        bitmapDraw.text("Address: Dillibazar, Kathmandu", null, PrintSize.SMALL, false);
        bitmapDraw.text("Number: 01-*******",null,PrintSize.SMALL, false);

        bitmapDraw.text("-------------x----------------x-------------", 30, false, Paint.Align.CENTER);
        bitmapDraw.feedPaper(100);
        return bitmapDraw.getBitmap();
    }
}
