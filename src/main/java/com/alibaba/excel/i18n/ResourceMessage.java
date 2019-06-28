package com.alibaba.excel.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author hwcao
 * @title: ResourceMessage
 * @description:
 */
public class ResourceMessage {

    private Locale locale = new Locale("en", "US");
    private static String lang = "en_US";
    private static String resourcePath = "";
    private static ResourceMessage INSTANCE;
    private ResourceBundle resourceBundle;

    public ResourceMessage(String resourcePath) {
        this.locale = locale;
        ResourceMessage.resourcePath = resourcePath;
    }

    public ResourceMessage(String lang, String resourcePath) throws Exception {
        String[] lang_ = lang.split("_");
        if (lang_.length != 2) {
            throw new Exception("输入的语言类型不合法，示例：en_US,zh_CN");
        }
        locale = new Locale(lang_[0], lang_[1]);
        ResourceMessage.resourcePath = resourcePath;
        resourceBundle = getMessageResource(resourcePath, lang);
    }

    private ResourceBundle getMessageResource(String path, String lang) throws Exception {
        String[] lang_ = lang.split("_");
        if (lang_.length != 2) {
            throw new Exception("输入的语言类型不合法，示例：en_US,zh_CN");
        }
        Locale locale = new Locale(lang_[0], lang_[1]);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(path + ".i18n_" + path, locale);
        return resourceBundle;
    }

    public String getMessage(String key) throws Exception {
        String value = INSTANCE.resourceBundle.getString(key);
        if (value == null) {
            throw new Exception("i18n_" + lang + "中没有" + key + " 字段");
        }
        return value;
    }

    public static ResourceMessage getResourceMessage(String lang, String resourcePath) throws Exception {
        if (INSTANCE == null) {
            synchronized (INSTANCE) {
                if (INSTANCE == null) {
                    INSTANCE = new ResourceMessage(lang, resourcePath);
                }
            }
        }
        if (ResourceMessage.lang.equals(lang) && ResourceMessage.resourcePath.equals(resourcePath)) {
            return INSTANCE;
        } else {
            INSTANCE = new ResourceMessage(lang, resourcePath);
        }
        return INSTANCE;
    }
}
