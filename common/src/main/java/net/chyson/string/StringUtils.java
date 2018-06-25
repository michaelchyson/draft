package net.chyson.string;

public class StringUtils {

    private static int nth(String string, String separator, int num) {
        int i = 0;
        while (num > 0) {
            i = string.indexOf(separator, i + 1);
            num -= 1;
        }
        return i;
    }

    private static String nthString(String string, String separator, int num) {
        int i = nth(string, separator, num - 1);
        int j = nth(string, separator, num);
        return string.substring(i + 1, j);
    }

    private static String nthLeft(String string, String separator, int num) {
        int i = nth(string, separator, num - 1);
        return string.substring(0, i);
    }


    private static String nthRight(String string, String separator, int num) {
        int i = nth(string, separator, num);
        return string.substring(i + 1, string.length());
    }

    public static void main(String[] args) {
        String real = "2290,010,11,00017c10648bb7362e02,6,EE922A1FD69FD2FE29F0E5543E63C19C,6DBEF77BB602AF173BA2E2DE651F5DF0,526F6D8979EC2B33E23649855E6551C3,1,100.114.189.132,100.114.163.31,2152,2152,45653,1081601248,4279,24355073,CMNET.mnc000.mcc460.gprs,103,1527842870425,1527842872610,1,1,,255,0,100.67.131.115,,60128,0,111.62.78.232,,80,1044,6304,10,9,0,0,0,0,11,13,0,0,24,11,65535,1394,1,0,1,3,6,200,12,12,39,shmmsns.qpic.cn,http://shmmsns.qpic.cn/mmsns/a1AJSSbmibgkCeT0WkeTeSZ5JjoEfAoYNYiaibqyz9Uhcmw06jeQOeIqp90pwSUlhEbibPl7c6LrRibU/150?tp=wxpc&length=228&width=1242&idx=1&token=WSEN6qDsKwV8A02w3onOGQYfxnkibdqSOkmHhZGNB4DEibT6eia8YxZfRjonhnz1U3xdyh7vT8Bsl2IialjbC7x5vg,,WeChat/6.6.7.32 CFNetwork/811.5.4 Darwin/16.7.0,image/wxpc,http://weixin.qq.com/?version=369493792&uin=170615935&nettype=0,,5417,0,0,0,,,1,3,12,0,1,,,,,\n";
                String separator = ",";

        String sl = nthLeft(real, separator, 6);
        String s6 = nthString(real, separator, 6);
        String s7 = nthString(real, separator, 7);
        String s8 = nthString(real, separator, 8);
        String sr = nthRight(real, separator, 8 + 1);
        System.out.println(sl);
        System.out.println(s6);
        System.out.println(s7);
        System.out.println(s8);
        System.out.println(sr);
    }
}
