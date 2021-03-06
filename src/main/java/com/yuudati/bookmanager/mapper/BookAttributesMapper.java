package com.yuudati.bookmanager.mapper;

import com.yuudati.bookmanager.entity.BookAttributes;
import com.yuudati.bookmanager.entity.BookAttributesExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookAttributesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    long countByExample(BookAttributesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    int deleteByExample(BookAttributesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    @Delete({
        "delete from book_attributes",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    @Insert({
        "insert into book_attributes (book_id, `attribute`)",
        "values (#{bookId,jdbcType=VARCHAR}, #{attribute,jdbcType=VARCHAR})"
    })
    int insert(BookAttributes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    int insertSelective(BookAttributes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    List<BookAttributes> selectByExample(BookAttributesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, book_id, `attribute`",
        "from book_attributes",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.yuudati.bookmanager.mapper.BookAttributesMapper.BaseResultMap")
    BookAttributes selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BookAttributes record, @Param("example") BookAttributesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BookAttributes record, @Param("example") BookAttributesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BookAttributes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_attributes
     *
     * @mbg.generated
     */
    @Update({
        "update book_attributes",
        "set book_id = #{bookId,jdbcType=VARCHAR},",
          "`attribute` = #{attribute,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(BookAttributes record);
}