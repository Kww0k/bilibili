package com.bilibili.commons.utils;

import com.bilibili.commons.exctption.file.MinFileSIzeException;
import com.bilibili.commons.exctption.file.PictureMaxException;
import com.bilibili.commons.exctption.file.VideoMaxException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Silvery
 * @since 2023/8/21 11:46
 */
@Component
public class TypeUtils {

    private static final String JPG = ".jpg";
    private static final String JPEG = ".jpeg";
    private static final String PNG = ".png";
    private static final String GIF = ".gif";
    private static final String BMP = ".bmp";
    private static final String SVG = ".svg";
    private static final String WEBP = ".webp";

    private static final String AVI = ".avi";
    private static final String MP4 = ".mp4";
    private static final String MOV = ".mov";
    private static final String WMV = ".wmv";
    private static final String MKV = ".mkv";
    private static final String FLV = ".flv";
    private static final String MPEG = ".mpeg";
    private static final String _3GP = ".3gp";
    private static final String WEBM = ".webm";


    public boolean isPicture(MultipartFile file) {
        if (file.getSize() < 1024 * 5)
            // 文件小于5KB，抛出异常或返回错误信息
            throw new MinFileSIzeException();
         else if (file.getSize() > 1024 * 1024 * 10)
            // 文件大于10MB，抛出异常或返回错误信息
            throw new PictureMaxException();
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        return fileName.endsWith(JPEG) ||
                fileName.endsWith(JPG) ||
                fileName.endsWith(PNG) ||
                fileName.endsWith(GIF) ||
                fileName.endsWith(BMP) ||
                fileName.endsWith(SVG) ||
                fileName.endsWith(WEBP);
    }

        public boolean isVideo(MultipartFile file) {
            if (file.getSize() < 1024)
                // 文件小于1MB，抛出异常或返回错误信息
                throw new MinFileSIzeException();
            else if (file.getSize() > 1024 * 1024 * 1024)
                // 文件大于1GB，抛出异常或返回错误信息
                throw new VideoMaxException();
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            return fileName.endsWith(AVI) ||
                fileName.endsWith(MP4) ||
                fileName.endsWith(MOV) ||
                fileName.endsWith(WMV) ||
                fileName.endsWith(MKV) ||
                fileName.endsWith(FLV) ||
                fileName.endsWith(MPEG) ||
                fileName.endsWith(_3GP) ||
                fileName.endsWith(WEBM);
    }
}
