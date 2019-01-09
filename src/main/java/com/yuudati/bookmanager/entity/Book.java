package com.yuudati.bookmanager.entity;

import com.google.common.collect.Lists;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
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
     * 源文件
     */
    private File oldFile;

    /**
     * 原路径
     */
    private StringProperty fromPath;
    /**
     * 原名
     */
    private StringProperty oldName;
    /**
     * 新路径
     */
    private StringProperty toPath;
    /**
     * 新名
     */
    private StringProperty newName;

    /**
     * 来源展会
     */
    private StringProperty exhibition;
    /**
     * 作者
     */
    private StringProperty author;
    /**
     * 本名
     */
    private StringProperty bookName;
    /**
     * 汉化组
     */
    private StringProperty translate;
    /**
     * 题材
     */
    private StringProperty theme;

    private String[] newInfo = new String[5];


    /**
     * 预览按钮
     */
    private ObjectProperty<Button> previewButton;

    public Book(File oldFile, String fromPath, String oldName) {
        this.oldFile = oldFile;
        this.fromPath = new SimpleStringProperty(fromPath);
        this.oldName = new SimpleStringProperty(oldName);
        this.toPath = new SimpleStringProperty("");
        this.newName = new SimpleStringProperty("");
        this.setBaseInfo(oldName);
        String exhibition = newInfo[0];
        this.exhibition = new SimpleStringProperty(exhibition.substring(
                exhibition.startsWith("(") ? 1 : 0,
                exhibition.endsWith(")") ? exhibition.length() - 1 : exhibition.length())
        );
        String author = newInfo[1];
        this.author = new SimpleStringProperty(author.substring(
                author.startsWith("[") ? 1 : 0,
                author.endsWith("]") ? author.length() - 1 : author.length())
        );
        String bookName = newInfo[2];
        this.bookName = new SimpleStringProperty(bookName.substring(
                bookName.startsWith("]") ? 1 : 0,
                bookName.endsWith("[") ? bookName.length() - 1 : bookName.length())
        );
        String theme = newInfo[3];
        this.theme = new SimpleStringProperty(theme.substring(
                theme.startsWith("(") ? 1 : 0,
                theme.endsWith(")") ? theme.length() - 1 : theme.length())
        );
        String translate = newInfo[4];
        this.translate = new SimpleStringProperty(translate.substring(
                translate.startsWith("[") ? 1 : 0,
                translate.endsWith("]") ? translate.length() - 1 : translate.length()
        ));
    }

    public void updateNewName(){
        this.newName.set(String.format("(%s)_[%s]_%s_(%s)_[%s]",
                this.getExhibition(), this.getAuthor(), this.getBookName(), this.getTheme(), this.getTranslate()));
    }


    public void setBaseInfo(String oldName) {
        Matcher matcher;
        for (int i = 0; i < patterns.length; i++) {
            matcher = patterns[i].matcher(oldName);
            if (i == 3) {
                List<String> matcherList = Lists.newArrayList();
                while (matcher.find()) {
                    matcherList.add(matcher.group());
                }
                newInfo[i] = matcherList.get(matcherList.size() - 1);
            } else {
                if (matcher.find()) {
                    newInfo[i] = matcher.group();
                } else {
                    newInfo[i] = "";
                }
            }
        }
    }

    public File getOldFile() {
        return oldFile;
    }

    public void setOldFile(File oldFile) {
        this.oldFile = oldFile;
    }

    public String getFromPath() {
        return fromPath.get();
    }

    public StringProperty fromPathProperty() {
        return fromPath;
    }

    public void setFromPath(String fromPath) {
        this.fromPath.set(fromPath);
    }

    public String getOldName() {
        return oldName.get();
    }

    public StringProperty oldNameProperty() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName.set(oldName);
    }

    public String getToPath() {
        return toPath.get();
    }

    public StringProperty toPathProperty() {
        return toPath;
    }

    public void setToPath(String toPath) {
        this.toPath.set(toPath);
    }

    public String getNewName() {
        return newName.get();
    }

    public StringProperty newNameProperty() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName.set(newName);
    }

    public String getExhibition() {
        return exhibition.get();
    }

    public StringProperty exhibitionProperty() {
        return exhibition;
    }

    public void setExhibition(String exhibition) {
        this.exhibition.set(exhibition);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getBookName() {
        return bookName.get();
    }

    public StringProperty bookNameProperty() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public String getTranslate() {
        return translate.get();
    }

    public StringProperty translateProperty() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate.set(translate);
    }

    public String getTheme() {
        return theme.get();
    }

    public StringProperty themeProperty() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme.set(theme);
    }

    public ObjectProperty<Button> getPreviewButton() {
        return previewButton;
    }

    public void setPreviewButton(ObjectProperty<Button> previewButton) {
        this.previewButton = previewButton;
    }

    public String[] getNewInfo() {
        return newInfo;
    }

    public void setNewInfo(String[] newInfo) {
        this.newInfo = newInfo;
    }
}
