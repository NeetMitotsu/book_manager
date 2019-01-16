package com.yuudati.bookmanager.entity;

import com.yuudati.bookmanager.util.MD5CaculateUtil;

import java.io.File;

public class BookInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.exhibition
     *
     * @mbg.generated
     */
    private String exhibition;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.artist
     *
     * @mbg.generated
     */
    private String artist;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.parody
     *
     * @mbg.generated
     */
    private String parody;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.translate
     *
     * @mbg.generated
     */
    private String translate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column book_info.path
     *
     * @mbg.generated
     */
    private String path;

    public BookInfo(Book book){
        this.id = MD5CaculateUtil.getMD5(book.getNewFile());
        this.exhibition = book.getExhibition();
        this.artist = book.getArtist();
        this.name = book.getNewName();
        this.parody = book.getParody();
        this.translate = book.getTranslate();
        this.path = book.getNewFile().getPath();
    }

    public BookInfo(File file,String exhibition, String artist, String name, String parody, String translate, String path ){
        this.id = MD5CaculateUtil.getMD5(file);
        this.exhibition = exhibition;
        this.artist = artist;
        this.name = name;
        this.parody = parody;
        this.translate = translate;
        this.path = path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public BookInfo(String id, String exhibition, String artist, String name, String parody, String translate, String path) {
        this.id = id;
        this.exhibition = exhibition;
        this.artist = artist;
        this.name = name;
        this.parody = parody;
        this.translate = translate;
        this.path = path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public BookInfo() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.id
     *
     * @return the value of book_info.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.id
     *
     * @param id the value for book_info.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.exhibition
     *
     * @return the value of book_info.exhibition
     *
     * @mbg.generated
     */
    public String getExhibition() {
        return exhibition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.exhibition
     *
     * @param exhibition the value for book_info.exhibition
     *
     * @mbg.generated
     */
    public void setExhibition(String exhibition) {
        this.exhibition = exhibition == null ? null : exhibition.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.artist
     *
     * @return the value of book_info.artist
     *
     * @mbg.generated
     */
    public String getArtist() {
        return artist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.artist
     *
     * @param artist the value for book_info.artist
     *
     * @mbg.generated
     */
    public void setArtist(String artist) {
        this.artist = artist == null ? null : artist.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.name
     *
     * @return the value of book_info.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.name
     *
     * @param name the value for book_info.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.parody
     *
     * @return the value of book_info.parody
     *
     * @mbg.generated
     */
    public String getParody() {
        return parody;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.parody
     *
     * @param parody the value for book_info.parody
     *
     * @mbg.generated
     */
    public void setParody(String parody) {
        this.parody = parody == null ? null : parody.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.translate
     *
     * @return the value of book_info.translate
     *
     * @mbg.generated
     */
    public String getTranslate() {
        return translate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.translate
     *
     * @param translate the value for book_info.translate
     *
     * @mbg.generated
     */
    public void setTranslate(String translate) {
        this.translate = translate == null ? null : translate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column book_info.path
     *
     * @return the value of book_info.path
     *
     * @mbg.generated
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column book_info.path
     *
     * @param path the value for book_info.path
     *
     * @mbg.generated
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }
}