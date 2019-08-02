package com.huatec.edu.mobileshop.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/24.
 */

public class FormatUtils {
    public static void checkEmail(Context context, String email) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            Toast.makeText(context, "邮箱格式不正确！", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
