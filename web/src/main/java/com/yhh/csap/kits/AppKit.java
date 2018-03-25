package com.yhh.csap.kits;

import com.jfinal.kit.Base64Kit;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.ImageUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yhh.csap.Consts;

import java.io.*;

/**
 * 简介
 * <p>
 * 项目名称:   [whbx]
 * 包:        [com.yhh.csap.kits]
 * 类名称:    [AppKit]
 * 类描述:    []
 * 创建人:    [于海慧]
 * 创建时间:  [2018/1/27]
 * 修改人:    []
 * 修改时间:  []
 * 修改备注:  []
 * 版本:     [v1.0]
 */
public class AppKit {

    public static String getBase64ImgType(String b64){
        if(StrUtil.isBlank(b64))
            return "";
        String[] strings=b64.split(";");
        strings=strings[0].split("/");
        return strings[1];
    }

    public static boolean base64ToImg(String b64,File file){
        if (b64 == null)
            return false;
        try {
            // 解密
            byte[] b = Base64Kit.decode(b64);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String imageToBase64Str(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密

        return Base64Kit.encode(data);
    }


    public static void main(String[] args) {
        String b=imageToBase64Str("/Users/yuhaihui/Pictures/yb.png");
        File file=FileUtil.file("/Users/yuhaihui/Pictures/yb-1.png");
        base64ToImg(b,file);
    }

}
