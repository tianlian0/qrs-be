package com.bjtu.utils;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

public class ImageUtil {

    public static String toBase64(Frame image) throws IOException {

        Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
        BufferedImage bufferedImage = java2dFrameConverter.convert(image);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", out);
        byte[] bytes = out.toByteArray();

        //对字节数组Base64编码
        // 返回Base64编码过的字节数组字符串
        return Base64.encodeBase64String(bytes);
        //BASE64Encoder encoder = new BASE64Encoder();
        //return encoder.encode(bytes);
    }


}
