package br.ufpr.mscadastros.utils;

import org.apache.commons.lang3.StringUtils;

public class StrUtils {
    private StrUtils(){}

    public static String removerPrefixoBase64(String strBase64) {
        return StringUtils.substringAfter(strBase64, "data:image/jpeg;base64,");
    }
}
