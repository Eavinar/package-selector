package com.mobiquity.helper;

/**
 * Utility class for manipulating with String
 */
public class Utility {

    public static String removeTrailingSpaces(final String param)
    {
        if (param == null)
            return null;
        int len = param.length();
        for (; len > 0; len--) {
            if (!Character.isWhitespace(param.charAt(len - 1)))
                break;
        }
        return param.substring(0, len);
    }
}
