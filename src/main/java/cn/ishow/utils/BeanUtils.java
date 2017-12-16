package cn.ishow.utils;

public abstract class BeanUtils {

    public static boolean strIsNUll(String str) {
        if (str == null || str.equals(""))
            return true;
        return false;
    }
}
