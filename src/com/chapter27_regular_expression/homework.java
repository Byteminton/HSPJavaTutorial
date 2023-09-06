package com.chapter27_regular_expression;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class homework {
    @Test
    public void homework01() {
        String emailAddress = "corgi_98-06-09@tsinghua.org.cn";
        if (emailAddress.matches("^[\\w-]+@([a-zA-Z]+\\.)+[a-zA-Z]+$")) {
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }
     @Test
    public void homework02() {
        String result = "123";
        if (result.matches("[-+]?(0|([1-9]\\d*))(\\.\\d+)?")) {
            System.out.println("是整数或者小数");
        } else {
            System.out.println("不是");
        }
     }

     @Test
    public void homework03() {
         String result = "https://www.sohu.com:8080/abc/index.html";
         String regEx = "^(http(?:s))://(.*):(\\d+)(.*)/(.*)$";
         Pattern pattern = Pattern.compile(regEx);
         Matcher matcher = pattern.matcher(result);
         if (matcher.find()) {
             System.out.println(matcher.group(0));
             System.out.println("Protocol: " + matcher.group(1));
             System.out.println("Domain Name: " + matcher.group(2));
             System.out.println("Port: " + matcher.group(3));
             System.out.println("File Name: " + matcher.group(5));
         }
     }
}
