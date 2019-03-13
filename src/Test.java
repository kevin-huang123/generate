public class Test {

    public static void main(String[] args) {
        String str1 = "!{mapperPackageName}";
        String str2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String reg = "[!\\{].+[\\}]";
        String reg2 = "[!\\{]\\w+[\\}]";
        System.out.println(str1.matches(reg));
        System.out.println(str2.matches(reg));
        System.out.println(str1.matches(reg2));
        System.out.println(str2.matches(reg2));
    }
}
