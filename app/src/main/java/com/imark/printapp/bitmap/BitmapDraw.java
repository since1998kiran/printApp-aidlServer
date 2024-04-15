
package com.imark.printapp.bitmap;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IntRange;


/**
 * Draw a bitmap by text or image.
 * <p>e.g.</p>
 * <pre>
 * BitmapDraw bitmapDraw = new BitmapDraw();
 * bitmapDraw.addImage(bitmap,Paint.Align.CENTER,100);
 * bitmapDraw.newline();
 * bitmapDraw.text("merchant:", 22, false, Paint.Align.LEFT,0);
 * bitmapDraw.addText("123456789012345", 22, false, Paint.Align.RIGHT,100);
 * bitmapDraw.newline();
 * bitmapDraw.addText("$11.23", 30, true, Paint.Align.CENTER,100);
 * bitmapDraw.feedPaper(40);
 * Bitmap bitmap = bitmapDraw.getBitmap();
 * </pre>
 */
public class BitmapDraw {
    /**
     * Paper width.
     */
    private final static float PAPER_WIDTH_NORMAL = 384;
    /**
     * Large paper width.
     */
    private final static float PAPER_WIDTH_LARGE = 576;
    /**
     * Text or Image line space.
     */
    private final static int LINE_SPACE = 2;
    /**
     * Content canvas width.
     */
    private static float PAPER_CANVAS_WIDTH = PAPER_WIDTH_NORMAL;
    /**
     * Content canvas
     */
    private Canvas mCanvas;
    /**
     * result bitmap
     */
    private Bitmap resultBmp;
    /**
     * Current top Y coordinate
     */
    private float topY = 0;
    /**
     * Current bottom Y coordinate
     */
    private float bottomY = 0;
    /**
     * Current left X coordinate
     */
    private float leftX = 0;

    public BitmapDraw() {
        Log.d("BitmapDraw", "instantiate BitmapDraw");
        if (Build.MODEL.contains("CPOS")) {
            //CPOS is big paper
            PAPER_CANVAS_WIDTH = PAPER_WIDTH_LARGE;
        }
    }

    /**
     * create a bitmap.
     */
    public Bitmap getBitmap() {
        deleteExtraCanvas();
        mCanvas = null;
        return resultBmp;
    }

    /**
     * add a text.
     *
     * @param text     Text to be drawn.
     * @param textSize Text size in pixels.
     * @param bold     Bold font.
     * @param align    Align specifies how drawText aligns its text relative to the [x,y] coordinates.
     */
    public void text(String text, float textSize, boolean bold, Paint.Align align) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Log.d("BitmapDraw", text);
        addText(text, textSize, bold, align, 100);
        newline();
    }

    /**
     * add two text [LEFT,RIGHT].
     *
     * @param leftText  Left text to be drawn.
     * @param rightText Right text to be drawn.
     * @param textSize  Text size in pixels.
     * @param bold      Bold font.
     */
    public void text(String leftText, String rightText, float textSize, boolean bold) {
        if (TextUtils.isEmpty(leftText) && TextUtils.isEmpty(rightText)) {
            return;
        }
        Log.d("BitmapDraw", leftText + " " + rightText);
        addText(leftText, textSize, bold, Paint.Align.LEFT, 0);
        addText(rightText, textSize, bold, Paint.Align.RIGHT, 100);
        newline();
    }

    /**
     * add three text [LEFT,CENTER,RIGHT].
     *
     * @param leftText   Left text to be drawn.
     * @param centerText Center text to be drawn.
     * @param rightText  Right text to be drawn.
     * @param textSize   Text size in pixels.
     * @param bold       Bold font.
     */
    public void text(String leftText, String centerText, String rightText, float textSize, boolean bold) {
        if (TextUtils.isEmpty(leftText)
                && TextUtils.isEmpty(centerText)
                && TextUtils.isEmpty(rightText)) {
            return;
        }
        Log.d("BitmapDraw", leftText + " " + centerText + " " + rightText);
        addText(leftText, textSize, bold, Paint.Align.LEFT, 33);
        addText(centerText, textSize, bold, Paint.Align.CENTER, 34);
        addText(rightText, textSize, bold, Paint.Align.RIGHT, 34);
        newline();
    }

    /**
     * add four text [LEFT,CENTER,CENTER,RIGHT]
     *
     * @param first    First text to be drawn.
     * @param second   Second text to be drawn.
     * @param third    Third text to be drawn.
     * @param fourth   Fourth text to be drawn.
     * @param textSize Text size in pixels.
     * @param bold     Bold font.
     */
    public void text(String first, String second, String third, String fourth, float textSize, boolean bold) {
        if (TextUtils.isEmpty(first)
                && TextUtils.isEmpty(second)
                && TextUtils.isEmpty(third)
                && TextUtils.isEmpty(fourth)) {
            return;
        }
        Log.d("BitmapDraw", first + " " + second + " " + third + " " + fourth);
        addText(first, textSize, bold, Paint.Align.LEFT, 25);
        addText(second, textSize, bold, Paint.Align.CENTER, 25);
        addText(third, textSize, bold, Paint.Align.CENTER, 25);
        addText(fourth, textSize, bold, Paint.Align.RIGHT, 25);
        newline();
    }

    /**
     * add multiple text and allocate the width according to 'percents'.
     *
     * @param percents Percentage of width occupied by each text.
     * @param texts    Text array to be drawn.
     * @param textSize Text size in pixels.
     * @param bold     Bold font.
     */
    public void text(int[] percents, String[] texts, float textSize, boolean bold) {
        boolean isEmpty = true;
        for (String text : texts) {
            if (!TextUtils.isEmpty(text)) {
                isEmpty = false;
                break;
            }
        }
        if (isEmpty) {
            return;
        }
        switch (texts.length) {
            case 1:
                Log.d("BitmapDraw", texts[0]);
                addText(texts[0], textSize, bold, Paint.Align.LEFT, 0);
                newline();
                break;
            case 2:
                Log.d("BitmapDraw", texts[0] + " " + texts[1]);
                addText(texts[0], textSize, bold, Paint.Align.LEFT, percents[0]);
                addText(texts[1], textSize, bold, Paint.Align.RIGHT, percents[1]);
                newline();
                break;
            case 3:
                Log.d("BitmapDraw", texts[0] + " " + texts[1] + " " + texts[2]);
                addText(texts[0], textSize, bold, Paint.Align.LEFT, percents[0]);
                addText(texts[1], textSize, bold, Paint.Align.CENTER, percents[1]);
                addText(texts[2], textSize, bold, Paint.Align.RIGHT, percents[2]);
                newline();
                break;
            case 4:
                Log.d("BitmapDraw", texts[0] + " " + texts[1] + " " + texts[2] + " " + texts[3]);
                addText(texts[0], textSize, bold, Paint.Align.LEFT, percents[0]);
                addText(texts[1], textSize, bold, Paint.Align.CENTER, percents[1]);
                addText(texts[2], textSize, bold, Paint.Align.CENTER, percents[2]);
                addText(texts[3], textSize, bold, Paint.Align.RIGHT, percents[3]);
                newline();
                break;
            default:
                Log.e("BitmapDraw", "text count more than 4.");
                break;
        }
    }

    /**
     * add a centered image
     *
     * @param image image to be drawn.
     */
    public void image(Bitmap image) {
        if (image == null) {
            return;
        }
        Log.d("BitmapDraw", "[image]");
        addImage(image, Paint.Align.CENTER, 100);
        newline();
    }

    /**
     * Feed the bitmap
     *
     * @param height Feeding height in pixels.
     */
    public void feedPaper(float height) {
        Log.d("BitmapDraw", "[feedPaper] " + height);
        if (mCanvas == null) {
            init();
        }
        bottomY += height;
        resizeCanvas(bottomY);
        newline();
    }

    /**
     * Draw text
     *
     * @param text         the text to be drawn
     * @param textSzie     set the paint's text size in pixel units
     * @param bold         true to set the text to bold, false to normal.
     * @param align        set the ratio of text width to paper
     * @param widthPercent set the ratio of text width to paper,from 0-100,
     *                     0 to automatically adapt the occupied width according to the text width.
     *                     If this value exceeds the remaining width, the remaining width is automatically used
     */
    private void addText(String text, float textSzie, boolean bold, Paint.Align align, @IntRange(from = 0, to = 100) int widthPercent) {
        if (text == null) {
            return;
        }
        if (mCanvas == null) {
            init();
        }
        Paint paint = new Paint();
        paint.setFakeBoldText(bold);
        paint.setTextSize(textSzie);
        paint.setTextAlign(align);
        paint.setAntiAlias(true);
        //Calculate the width of the occupied by text
        float width;
        float measureTextWidth = paint.measureText(text);
        float remainWidth = PAPER_CANVAS_WIDTH - leftX;
        if (widthPercent == 0) {
            width = Math.min(remainWidth, measureTextWidth);
        } else {
            width = Math.min(remainWidth, PAPER_CANVAS_WIDTH * widthPercent / 100);
        }
        if (width == 0) {
            return;
        }
        // Draws text on the specified coordinates
        float y = drawText(leftX, topY, text, paint, width);
        // Reset bottomY and leftX
        bottomY = Math.max(bottomY, y);
        leftX += width;
        // Deal with the situation that the current line has ended
        if (leftX >= PAPER_CANVAS_WIDTH) {
            newline();
        }
    }

    /**
     * Draw image bitmap
     *
     * @param image        the image to be drawn
     * @param align        set the ratio of text width to paper
     * @param widthPercent set the ratio of image width to paper,from 0-100,
     *                     0 to automatically adapt the occupied width according to the image width.
     */
    private void addImage(Bitmap image, Paint.Align align, @IntRange(from = 0, to = 100) int widthPercent) {
        if (image == null) {
            return;
        }
        if (mCanvas == null) {
            init();
        }
        Paint paint = new Paint();
        paint.setTextAlign(align);
        float width;
        if (widthPercent == 0) {
            width = Math.min(PAPER_CANVAS_WIDTH, image.getWidth());
        } else {
            width = Math.max(image.getWidth(), PAPER_CANVAS_WIDTH * widthPercent / 100);
        }
        float y = drawImage(leftX, topY, image, paint, width);
        bottomY = Math.max(bottomY, y);
        leftX += width;
        if (leftX >= PAPER_CANVAS_WIDTH) {
            newline();
        }
    }


    /**
     * Move start position to next line
     */
    private void newline() {
        if (mCanvas == null) {
            init();
        }
        leftX = 0;
        topY = bottomY + LINE_SPACE;
        bottomY = topY;
    }


    /**
     * init canvas
     */
    private void init() {
        topY = 0;
        bottomY = 0;
        leftX = 0;
        resultBmp = Bitmap.createBitmap((int) PAPER_CANVAS_WIDTH, 40, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(resultBmp);
        mCanvas.drawColor(Color.WHITE);
    }

    /**
     * Resize canvas
     *
     * @param height the minimum canvas height
     */
    private void resizeCanvas(float height) {
        if (resultBmp.getHeight() > height) {
            return;
        }
        Bitmap temp = Bitmap.createBitmap((int) PAPER_CANVAS_WIDTH, (int) ((int) height * 1.2), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(temp);
        mCanvas.drawColor(Color.WHITE);
        mCanvas.drawBitmap(resultBmp, 0, 0, new Paint());
        resultBmp = temp;
    }

    /**
     * Delete redundant canvas
     */
    private void deleteExtraCanvas() {
        if (resultBmp.getHeight() == bottomY) {
            return;
        }
        Bitmap temp = Bitmap.createBitmap((int) PAPER_CANVAS_WIDTH, (int) bottomY, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(temp);
        mCanvas.drawColor(Color.WHITE);
        mCanvas.drawBitmap(resultBmp, 0, 0, new Paint());
        resultBmp = temp;
    }

    /**
     * Execute to draw text
     *
     * @param leftX     the X coordinate of the left side of the drawing
     * @param topY      the Y coordinate of the top of the drawing
     * @param value     text content
     * @param paint     text paint tool
     * @param rectWidth rect width
     * @return The Y coordinate of the bottom of the drawing
     */
    private float drawText(float leftX, float topY, String value, Paint paint, float rectWidth) {
        int subIndex = paint.breakText(value, 0, value.length(), true, rectWidth, null);
        if (subIndex == 0) {
            System.err.print(value + "'s width is too short.");
            return topY;
        }
        String line = value.substring(0, subIndex);
        String nextLine = value.substring(subIndex);
        float offsetX;
        switch (paint.getTextAlign()) {
            case CENTER:
                offsetX = rectWidth / 2;
                break;
            case RIGHT:
                offsetX = rectWidth;
                break;
            default:
                offsetX = 0;
                break;
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        resizeCanvas(topY + textHeight);
        mCanvas.drawText(line, leftX + offsetX, topY - fontMetrics.ascent, paint);
        mCanvas.save();
        if (!TextUtils.isEmpty(nextLine)) {
            return drawText(leftX, topY + textHeight + LINE_SPACE, nextLine, paint, rectWidth);
        }
        return topY + textHeight;
    }

    /**
     * Execute to draw image
     *
     * @param leftX     the X coordinate of the left side of the drawing
     * @param topY      the Y coordinate of the top of the drawing
     * @param bitmap    the image bitmap
     * @param paint     paint tool
     * @param rectWidth rect width
     * @return The Y coordinate of the bottom of the drawing
     */
    private float drawImage(float leftX, float topY, Bitmap bitmap, Paint paint, float rectWidth) {
        float offsetX = 0;
        switch (paint.getTextAlign()) {
            case CENTER:
                offsetX = (rectWidth - bitmap.getWidth()) / 2;
                break;
            case RIGHT:
                offsetX = rectWidth - bitmap.getWidth();
                break;
            default:
                break;
        }
        resizeCanvas(topY + bitmap.getHeight());
        mCanvas.drawBitmap(bitmap, leftX + offsetX, topY, paint);
        mCanvas.save();
        return topY + bitmap.getHeight();
    }
}

