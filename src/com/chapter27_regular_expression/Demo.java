package com.chapter27_regular_expression;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    public static void main(String[] args) {
        String content = "我是王敬松 My English name is Corgi My birthday is 1998.06.09";
        // 创建一个Pattern对象
        //Pattern compile = Pattern.compile("[a-zA-Z]+");
        //Pattern compile = Pattern.compile("[0-9]+");
        String regEx = "([0-9]+)|([a-zA-Z]+)";
        Pattern compile = Pattern.compile(regEx);
        // 创建一个匹配器对象
        Matcher matcher = compile.matcher(content);
        while (matcher.find()) {
            // 匹配到的内容通过matcher.group(0)取得
            System.out.println("==========");
            System.out.println(matcher.group(0));
            System.out.println(matcher.start());
            System.out.println(matcher.end());
        }
        // content与regEx是否整体匹配，也就无需^和$了
        boolean matches = Pattern.matches(regEx, content);
        System.out.println(matches);
        System.out.println(matcher.matches());
        String newContent = matcher.replaceAll("NUS");
        System.out.println(newContent);
    }
    @Test
    public void practice01() {
        // 验证汉字
        String content = "王敬松";
        String regEx = "^[\u0391-\uffe5]+$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        //邮政编码，要求1-9开头的一个六位数
        content = "123456";
        regEx = "^[1-9]\\d{5}$";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        //QQ号，要求1-9开头的一个5位数-10位数
        content = "12345678";
        regEx = "^[1-9]\\d{4,9}$";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        //手机号，要求13, 14, 15, 18开头的11位数
        content = "13019122016";
        regEx = "^1[3458]\\d{9}$";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        //5个一样的数字
        content = "11111";
        regEx = "(\\d)\\1{4}";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        //千位和个位相同，十位和百位相同
        content = "1331";
        regEx = "(\\d)(\\d)\\2\\1";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        content = "我....我我要...学学.....学编程JJJava";
        regEx = "\\.";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(content);
        content = matcher.replaceAll("");// 去掉.
        System.out.println(content);
        regEx = "(.)\\1+";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(content);
        content = matcher.replaceAll("$1");// 匹配到重复的，则用group1代替
        System.out.println(content);
        // Pattern.compile("(.)\\1+").matcher(content).replaceAll("$1");

        // String类可以直接操作
        content = "JDK1.3 JDK1.4";
        System.out.println(content.replaceAll("JDK1\\.3|JDK1\\.4", "JDK"));
        content = "13019122016";
        System.out.println(content.matches("130\\d{8}"));
        // 要求用#,-,~,数字来分割
        content = "helLo#abc-jack12smith~北京";
        String[] split = content.split("#|-|~|\\d+");
        for (String s : split) {
            System.out.println(s);
        }
    }

    @Test
    public void practice02() {
        //复杂URL
        String content = "https://www.bilibili.com/video/BV1fh411y7R8?p=894&spm_id_from=pageDriver&vd_source=4a7e346e7cf2690016f8bd8b4b175bd6";
        // [\w-]+\\.代表了 www. or bilibili. 可以有多个, -代表域名中可能有-
        // ((http|https)://)([\w-]+\.)+[\w-]+ 代表了 https://bilibili.com
        // []中的-?=&/%.不需要转义符
        String regEx = "^((http|https)://)?([\\w-]+\\.)+[\\w-]+ (\\/[\\w-?=&/%.#]*)?$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

    }

}
