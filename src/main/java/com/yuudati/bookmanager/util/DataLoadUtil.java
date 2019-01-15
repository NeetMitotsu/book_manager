package com.yuudati.bookmanager.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yuudati.bookmanager.entity.BookData;
import lombok.Cleanup;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/15 14:33
 */
public class DataLoadUtil {

    private static SAXReader saxReader = new SAXReader();

    /**
     * 解析xml, 结构example:
     * <root>
     * <module>
     * <key>xxxx</key>
     * <values>
     * <value>xxxxx</value>
     * <value>xxxxx</value>
     * </values>
     * </module>
     * </root>
     *
     * @param dir
     * @param fileName
     * @return Map
     */
    public static Map<String, HashSet<String>> getXMLMap(File dir, String fileName) {
        Map<String, HashSet<String>> xmlMap = Maps.newHashMap();
        try {
            @Cleanup
            InputStream inputStream = new FileInputStream(new File(dir, fileName));
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            Iterator moduleIterator = rootElement.elements("module").iterator();
            while (moduleIterator.hasNext()) {
                Element moduleElement = (Element) moduleIterator.next();
                String key = moduleElement.element("key").getText();
                List<Element> valueElementList = moduleElement.element("values").elements();
                List<String> valuesList = valueElementList.stream().map(Element::getText).collect(Collectors.toList());
                final HashSet<String> value = Sets.newHashSet(valuesList);
                xmlMap.put(key, value);
            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
        return xmlMap;
    }

    /**
     * 获取XML映射
     * key: md5
     * value: BookData
     *
     * @param dir
     * @param fileName
     * @return
     */
    public static Map<String, BookData> getXMLStringMap(File dir, String fileName) {
        try {
            @Cleanup
            InputStream inputStream = new FileInputStream(new File(dir, fileName));
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            Iterator moduleIterator = rootElement.elements("book").iterator();
            while (moduleIterator.hasNext()) {
                Element moduleElement = (Element) moduleIterator.next();
                String md5 = moduleElement.element("md5").getText();
                final String path = moduleElement.element("path").getText();
                final String exhibition = moduleElement.element("exhibition").getText();
                final String artist = moduleElement.element("artist").getText();
                final String name = moduleElement.element("name").getText();
                final String parody = moduleElement.element("parody").getText();
                final String translate = moduleElement.element("translate").getText();
            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }




}
