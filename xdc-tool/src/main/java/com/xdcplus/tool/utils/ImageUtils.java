package com.xdcplus.tool.utils;

import cn.hutool.core.img.ImgUtil;
import com.alibaba.fastjson.util.IOUtils;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * 图片操作工具类
 * @date 2020/03/12 15:47
 * @author Rong.Jia
 */
@Data
public class ImageUtils {

    /**
     * 截取图片
     * @param oldFile 原图
     * @param destImgFile 目标图
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void cut(File oldFile, File destImgFile, Integer x, Integer y, Integer width, Integer height) throws Exception {
        FileInputStream inputStream = new FileInputStream(oldFile);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        // 若小图太小，可x，y 减25，height，width 加25
        x = x - 25;
        y = y - 25;
        width = width - x  + 25;
        height =height - y + 25;
        x = x <= -1 ? 0 : x;
        y = y <= -1 ? 0 : y;
        width = width >= bufferedImage.getWidth() ? bufferedImage.getWidth() -1 : width;
        height = height >= bufferedImage.getHeight() ? bufferedImage.getHeight() -1 : height;
        ImgUtil.cut(oldFile, destImgFile, new Rectangle(x, y, width, height));
        IOUtils.close(inputStream);
    }

}
