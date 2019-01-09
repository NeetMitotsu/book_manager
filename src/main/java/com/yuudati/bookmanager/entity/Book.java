package com.yuudati.bookmanager.entity;

import javafx.scene.control.Button;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/9 13:41
 */
@Data
@Slf4j
public class Book {

    /**
     * 来源匹配
     */
    private static Pattern exhibitionPattern = Pattern.compile("\\((\\w*)+\\)");
    /**
     * 作者匹配
     */
    private static Pattern authorPattern = Pattern.compile("\\[.+]\\s+");
    /**
     * 本名匹配
     */
    private static Pattern bookNamePattern = Pattern.compile("].+\\[");
    /**
     * 题材匹配
     */
    private static Pattern themePattern = Pattern.compile("\\( *(\\S|\\w)* *(\\S|\\w) *\\)");
    /**
     * 汉化组匹配
     */
    private static Pattern translatePattern = Pattern.compile("\\[\\S+]");

    /**
     * 匹配组
     */
    private static Pattern[] patterns = {
            exhibitionPattern,
            authorPattern,
            bookNamePattern,
            themePattern,
            translatePattern,
    };

    /**
     * 原路径
     */
    private String fromPath;
    /**
     * 原名
     */
    private String oldName;
    /**
     * 新路径
     */
    private String toPath;
    /**
     * 新名
     */
    private String newName;

    /**
     * 来源展会
     */
    private String exhibition;
    /**
     * 作者
     */
    private String author;
    /**
     * 本名
     */
    private String bookName;
    /**
     * 汉化组
     */
    private String translate;
    /**
     * 题材
     */
    private String theme;
    private String[] newInfo = new String[5];


    /**
     * 预览按钮
     */
    private Button previewButton;

    public Book(String fromPath, String oldName) {
        this.fromPath = fromPath;
        this.oldName = oldName;
        setBaseInfo(oldName);
    }

    public void setBaseInfo(String oldName){
        Matcher matcher;
        for (int i = 0; i < patterns.length; i++) {
            matcher = patterns[i].matcher(oldName);
            if (matcher.find()){
                newInfo[i] = matcher.group(matcher.groupCount());
            }else {
                newInfo[i] = "";
            }
        }
    }

    public static void main(String[] args) {
        String name = "(C87) [AYUSET (あゆや)] 愛と欲望のMMTWTFF ( 艦隊これくしょん-艦これ-) [屏幕髒了漢化組].rar";
        final Matcher matcher1 = exhibitionPattern.matcher(name);
        if (matcher1.find()){
            System.out.println(matcher1.groupCount() == 0 ? matcher1.group(0) : matcher1.group(matcher1.groupCount() - 1));
        }
        final Matcher matcher2 = authorPattern.matcher(name);
        if (matcher2.find()){
            System.out.println(matcher2.groupCount() == 0 ? matcher2.group(0) : matcher2.group(matcher2.groupCount() - 1));
        }
        final Matcher matcher3 = bookNamePattern.matcher(name);
        if (matcher3.find()){
            System.out.println(matcher3.groupCount() == 0 ? matcher3.group(0) : matcher3.group(matcher3.groupCount() - 1));
        }
        final Matcher matcher4 = themePattern.matcher(name);
        while (matcher4.find()){

            final String group = matcher4.group();
            final String group3 = matcher4.group(0);
            final String group1 = matcher4.group(matcher4.groupCount());
            final String group2 = matcher4.group(matcher4.groupCount() - 1);
            final int i = matcher4.groupCount();
            System.out.println(matcher4.groupCount() == 0 ? matcher4.group(0) : matcher4.group(matcher4.groupCount() - 1));
        }
        final Matcher matcher5 = translatePattern.matcher(name);
        if (matcher5.find()){
            System.out.println(matcher5.groupCount() == 0 ? matcher5.group(0) : matcher5.group(matcher5.groupCount() - 1));
        }
    }


}
