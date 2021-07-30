package com.xdcplus.auto;

import com.xdcplus.tool.constants.FileConstant;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;


/**
 * 文件上传配置
 *
 * @author Rong.Jia
 * @date 2021/07/07
 */
@Configuration
public class FileUploadConfig {

    private static final Integer FILE_SIZE = 1024;
    private static final Integer FILE_SIZE_THRESHOLD = 20;

    @Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();

        //路径有可能限制
        String location = FileConstant.TMP_DIR;
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }

        factory.setLocation(location);

        // 当上传文件达到10MB的时候进行磁盘写入
        factory.setFileSizeThreshold(DataSize.ofMegabytes(FILE_SIZE_THRESHOLD));

        /**
         * 设置文件大小限制
         * 大小：KB,MB
         */
        factory.setMaxFileSize(DataSize.ofMegabytes(FILE_SIZE));

        //设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(FILE_SIZE));

        return factory.createMultipartConfig();
    }

}
