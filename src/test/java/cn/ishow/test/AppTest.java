package cn.ishow.test;

public class AppTest {

    public static void main(String[] args) {
        String str = "A";
        String result = test(str);
        System.out.println(result);
    }

    public static String test(String str) {
        try {
            System.out.println(str);
            str = "C";
            return "B";
        } finally {
            System.out.println(str);
            return "D";
        }
    }
}
