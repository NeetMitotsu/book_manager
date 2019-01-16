package com.yuudati.bookmanager.mapper;

import com.yuudati.bookmanager.entity.BookCharacters;
import com.yuudati.bookmanager.entity.BookCharactersExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookCharactersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    long countByExample(BookCharactersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    int deleteByExample(BookCharactersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    @Delete({
        "delete from book_characters",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    @Insert({
        "insert into book_characters (book_id, `character`)",
        "values (#{bookId,jdbcType=VARCHAR}, #{character,jdbcType=VARCHAR})"
    })
    int insert(BookCharacters record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    int insertSelective(BookCharacters record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    List<BookCharacters> selectByExample(BookCharactersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, book_id, `character`",
        "from book_characters",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.yuudati.bookmanager.mapper.BookCharactersMapper.BaseResultMap")
    BookCharacters selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BookCharacters record, @Param("example") BookCharactersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BookCharacters record, @Param("example") BookCharactersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BookCharacters record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_characters
     *
     * @mbg.generated
     */
    @Update({
        "update book_characters",
        "set book_id = #{bookId,jdbcType=VARCHAR},",
          "`character` = #{character,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(BookCharacters record);
}