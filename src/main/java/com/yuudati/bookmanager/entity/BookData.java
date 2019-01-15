package com.yuudati.bookmanager.entity;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/15 10:56
 */
@Data
public class BookData implements Serializable {
    private static final long serialVersionUID = -2963726600752787748L;

    private static Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();

    private String md5;
    private String path;

    /**
     * 来源
     */
    private String exhibition;
    /**
     * 作者
     */
    private String artist;
    /**
     * 名
     */
    private String name;
    /**
     * 题材
     */
    private String parody;
    /**
     * 汉化组
     */
    private String translate;

    /**
     * 角色
     */
    private Set<String> characters;
    /**
     * 属性
     */
    private Set<String> attributes;

    /**
     * 分数
     */
    private int score;

    /**
     * 设置角色
     *
     * @param characterStr 角色列表  使用", "分割
     */
    public void setCharacters(String characterStr) {
        final Iterable<String> split = splitter.split(characterStr);
        this.characters = Sets.newHashSet(split);
    }

    /**
     * 设置角色
     *
     * @param characters 角色列表
     */
    public void setCharacters(Set<String> characters) {
        this.characters = characters;
    }

    /**
     * 添加角色
     *
     * @param character 角色
     */
    public void addCharacter(String character) {
        if (this.characters == null) {
            setCharacters(character);
        } else {
            this.characters.add(character);
        }
    }

    /**
     * 添加角色
     *
     * @param characters 角色列表,
     */
    public void addCharacters(Set<String> characters) {
        if (this.characters == null) {
            setCharacters(characters);
        } else {
            this.characters.addAll(characters);
        }
    }

    public void setAttributes(String attributeStr) {
        final Iterable<String> split = splitter.split(attributeStr);
        this.attributes = Sets.newHashSet(split);
    }

    public void setAttributes(Set<String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String attribute){
        if (this.attributes == null){
            setAttributes(attribute);
        }else {
            this.attributes.add(attribute);
        }
    }

    public void addAttributes(Set<String> attributes){
        if (this.attributes == null){
            setAttributes(attributes);
        }else {
            this.attributes.addAll(attributes);
        }
    }


}
