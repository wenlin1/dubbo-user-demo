package wl.common;

import java.util.Set;

public class Test {
    public static void main(String [] args){
        long beginTime = System.currentTimeMillis();
        SensitiveWordUtil.getInstance();
        long time1 = System.currentTimeMillis();
        System.out.println("初始化词库(ms)：" + (time1 - beginTime));
        String  text= "@*小^*日*#本";
        long time5=System.currentTimeMillis();
        Set<String> set1 = SensitiveWordUtil.getSensitiveWord(text, 1);
        long time6=System.currentTimeMillis();
        System.out.println("2检索敏感词 ["+text+"] 消耗时间为："+(time6 - time5)+"(ms)  语句中包含敏感词的个数为：" + (set1.size()) +"   匹配敏感字为："+ set1);

        String  tex1t= "小日本";
        long time10=System.currentTimeMillis();
        Set<String> set2 = SensitiveWordUtil.getSensitiveWord(tex1t, 1);
        long time11=System.currentTimeMillis();
        System.out.println("1检索敏感词 ["+tex1t+"] 消耗时间为："+(time11 - time10)+"(ms)  语句中包含敏感词的个数为：" + (set2.size()) +"   匹配敏感字为："+ set2);
        String  s1= "大人事变动";
        long time8=System.currentTimeMillis();
        Set<String> set3= SensitiveWordUtil.getSensitiveWord(s1, 1);
        long time9=System.currentTimeMillis();
        System.out.println("3检索敏感词 ["+s1+"] 消耗时间为："+(time9 - time8)+"(ms)   语句中包含敏感词的个数为：" + (set3.size()) +"  匹配敏感字为："+ set3);
        long endTime = System.currentTimeMillis();
        System.out.println("总共消耗时间为(ms)：" + (endTime - beginTime));

    }

}
