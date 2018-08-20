package com.wiser.library.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERTextView {

    /**
     * 文本开始插入图片
     *
     * @param context
     * @param imageId //图片id
     * @param text
     * @param content //内容
     */
    public static void textStartInsertImage(Context context, int imageId, TextView text, String content) {
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), imageId);
        ImageSpan imgSpan = new ImageSpan(context, b);
        SpannableString spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spanString);
        text.append(content);
    }

    /**
     * 文本末尾插入图片
     *
     * @param context
     * @param imageId
     * @param text
     * @param content
     */
    public static void textEndInsertImage(Context context, int imageId, TextView text, String content) {
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), imageId);
        ImageSpan imgSpan = new ImageSpan(context, b);
        SpannableString spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(content);
        text.append(spanString);
    }

    /**
     * 另一种插入图片方法
     *
     * @param context
     * @param imageId
     * @param text
     * @param content
     */
    public static void textInsertImage(final Context context, int imageId, TextView text, String content) {
        String html = "<img src='" + imageId + "'/>";
        Html.ImageGetter imgGetter = new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {
                int id = Integer.parseInt(source);
                Drawable d = context.getResources().getDrawable(id);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };
        CharSequence charSequence = Html.fromHtml(html, imgGetter, null);
        text.setText(charSequence);
        text.append(content);
    }

    /**
     * 连续在文本开始处插入多张图片
     *
     * @param context
     * @param imageIds 图片ID 集合
     * @param text
     * @param content
     */
    public static void textStartInsertManyImage(Context context, List<Integer> imageIds, TextView text, String content) {
        text.setText("");
        if (imageIds != null && imageIds.size() > 0) {

            for (int i = 0; i < imageIds.size(); i++) {
                Bitmap b = BitmapFactory.decodeResource(context.getResources(), imageIds.get(i));
                ImageSpan imgSpan = new ImageSpan(context, b);
                SpannableString spanString = new SpannableString("icon");
                spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.append(spanString);
            }
            text.append(content);
        }
    }

    /**
     * 连续在文本末尾处插入多张图片
     *
     * @param context
     * @param imageIds 图片ID 集合
     * @param text
     * @param content
     */
    public static void textEndInsertManyImage(Context context, List<Integer> imageIds, TextView text, String content) {
        text.setText("");
        if (imageIds != null && imageIds.size() > 0) {

            text.append(content);

            for (int i = 0; i < imageIds.size(); i++) {
                Bitmap b = BitmapFactory.decodeResource(context.getResources(), imageIds.get(i));
                ImageSpan imgSpan = new ImageSpan(context, b);
                SpannableString spanString = new SpannableString("icon");
                spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.append(spanString);
            }
        }
    }

    /**
     * 格式化文本信息
     *
     * @param content     一个完整的文本内容
     * @param subContent1 一个文本中具体颜色变的内容一处
     * @param subContent2 一个文本中具体颜色变的内容二处
     * @param count       （1为就一处颜色变更，2为两处颜色变更）
     * @return
     */
    public static SpannableString textformatSpan(String content, String subContent1,
                                                 String subContent2, int color1, int color2, int count) {
        // 文本内容
        SpannableString ss;
        if (TextUtils.isEmpty(content)) {
            return new SpannableString("");
        }
        if (TextUtils.isEmpty(subContent1)) {
            return new SpannableString(content);
        }
        if (!content.contains(subContent1)) {
            return new SpannableString(content);
        }
        ss = new SpannableString(content);
        int index1 = content.indexOf(subContent1);
        int length1 = subContent1.length();
        // 设置下标处的字符颜色
        ss.setSpan(new ForegroundColorSpan(color1),
                index1, index1 + length1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        switch (count) {
            case 2:
                if (TextUtils.isEmpty(subContent2)) {
                    return ss;
                }
                if (!content.contains(subContent2)) {
                    return ss;
                }
                int index2 = content.indexOf(subContent2);
                int length2 = subContent2.length();
                ss.setSpan(new ForegroundColorSpan(color2),
                        index2, index2 + length2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }

        return ss;
    }

    /**
     * 连续开始插入多张图片并且之后文本可有部分颜色字体变色
     *
     * @param context
     * @param imageIds    插入图片集合
     * @param text        TextView
     * @param content     文本内容
     * @param subContent1 截取文本部分1
     * @param subContent2 截取文本部分2
     * @param color1      截取文本部分1颜色值
     * @param color2      截取文本部分2颜色值
     * @param count       （1为就一处颜色变更，2为两处颜色变更）
     */
    public static void textInsertImageAndTextColorChange(Context context, List<Integer> imageIds, TextView text, String content, String subContent1,
                                                         String subContent2, int color1, int color2, int count) {
        text.setText("");
        if (imageIds != null && imageIds.size() > 0) {

            for (int i = 0; i < imageIds.size(); i++) {
                Bitmap b = BitmapFactory.decodeResource(context.getResources(), imageIds.get(i));
                ImageSpan imgSpan = new ImageSpan(context, b);
                SpannableString spanString = new SpannableString("icon");
                spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.append(spanString);
            }
        }
        text.append(textformatSpan(content, subContent1, subContent2, color1, color2, count));
    }

    /**
     * 设置方正兰亭字体
     *
     * @param tv_content 文本对象
     * @param content    文本内容
     */
    public static void setTextTypeface(TextView tv_content, String content, Typeface typeface) {

        if (tv_content == null) return;
        tv_content.setText(content);
        if (typeface == null) return;
        tv_content.setTypeface(typeface);

    }

    //设置文本上面图片
    public static void setTextDrawable(Activity activity, TextView view, int drawable, String direction) {
        Drawable img = activity.getResources().getDrawable(drawable);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        view.setCompoundDrawablePadding(8);
        switch (direction) {
            case "left":
                view.setCompoundDrawables(img, null, null, null); //设置左图标
                break;
            case "top":
                view.setCompoundDrawables(null, img, null, null); //设置上图标
                break;
            case "right":
                view.setCompoundDrawables(null, null, img, null); //设置右图标
                break;
            case "bottom":
                view.setCompoundDrawables(null, null, null, img); //设置下图标
                break;
        }
    }

    //设置EditText Hint字体大小
    public static void setEditTextHintSize(EditText editText, String hintContent, int size) {
        SpannableString s = new SpannableString(hintContent);
        AbsoluteSizeSpan textSize = new AbsoluteSizeSpan(size, true);
        s.setSpan(textSize, 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(s);
        editText.setGravity(Gravity.BOTTOM);
    }

}
