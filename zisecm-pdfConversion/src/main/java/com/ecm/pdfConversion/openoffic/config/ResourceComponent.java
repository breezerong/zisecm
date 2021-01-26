package com.ecm.pdfConversion.openoffic.config;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.regex.Pattern;


@Component
@Data
public class ResourceComponent {


    public static String fileUploadPath;

    @Value("${storage.winLocation}")
    private String winLocation;
    @Value("${storage.linuxLocation}")
    private String linuxLocation;

    @PostConstruct
    public void init() {
        if (isWindowsSystem()) {
            fileUploadPath = winLocation;
        } else {
            fileUploadPath = linuxLocation;
        }
        File file = new File(fileUploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 从web访问路径转换成本地路径
     *
     * @param webPath
     * @return
     */
    public static String getLocation(String webPath) {
        //webPath = StringUtils.substringAfter(webPath, ResourcesConfig.PREVIEW);
        return fileUploadPath + webPath;
    }

    /**
     * 从本地路径转换成web访问路径
     *
     * @param location
     * @return
     */
    public static String getWebPath(String location) {
        location = StringUtils.substringAfter(location, fileUploadPath);
        return  location;
    }

    /**
     * 判断系统是win linux
     *
     * @return
     */
    public static boolean isWindowsSystem() {
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Windows.*", osName)) {
            return true;
        }
        return false;
    }

}
