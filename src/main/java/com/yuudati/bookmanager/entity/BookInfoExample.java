package com.yuudati.bookmanager.entity;

import java.util.ArrayList;
import java.util.List;

public class BookInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table book_info
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table book_info
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table book_info
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public BookInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table book_info
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andExhibitionIsNull() {
            addCriterion("exhibition is null");
            return (Criteria) this;
        }

        public Criteria andExhibitionIsNotNull() {
            addCriterion("exhibition is not null");
            return (Criteria) this;
        }

        public Criteria andExhibitionEqualTo(String value) {
            addCriterion("exhibition =", value, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionNotEqualTo(String value) {
            addCriterion("exhibition <>", value, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionGreaterThan(String value) {
            addCriterion("exhibition >", value, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionGreaterThanOrEqualTo(String value) {
            addCriterion("exhibition >=", value, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionLessThan(String value) {
            addCriterion("exhibition <", value, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionLessThanOrEqualTo(String value) {
            addCriterion("exhibition <=", value, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionLike(String value) {
            addCriterion("exhibition like", value, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionNotLike(String value) {
            addCriterion("exhibition not like", value, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionIn(List<String> values) {
            addCriterion("exhibition in", values, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionNotIn(List<String> values) {
            addCriterion("exhibition not in", values, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionBetween(String value1, String value2) {
            addCriterion("exhibition between", value1, value2, "exhibition");
            return (Criteria) this;
        }

        public Criteria andExhibitionNotBetween(String value1, String value2) {
            addCriterion("exhibition not between", value1, value2, "exhibition");
            return (Criteria) this;
        }

        public Criteria andArtistIsNull() {
            addCriterion("artist is null");
            return (Criteria) this;
        }

        public Criteria andArtistIsNotNull() {
            addCriterion("artist is not null");
            return (Criteria) this;
        }

        public Criteria andArtistEqualTo(String value) {
            addCriterion("artist =", value, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistNotEqualTo(String value) {
            addCriterion("artist <>", value, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistGreaterThan(String value) {
            addCriterion("artist >", value, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistGreaterThanOrEqualTo(String value) {
            addCriterion("artist >=", value, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistLessThan(String value) {
            addCriterion("artist <", value, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistLessThanOrEqualTo(String value) {
            addCriterion("artist <=", value, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistLike(String value) {
            addCriterion("artist like", value, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistNotLike(String value) {
            addCriterion("artist not like", value, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistIn(List<String> values) {
            addCriterion("artist in", values, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistNotIn(List<String> values) {
            addCriterion("artist not in", values, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistBetween(String value1, String value2) {
            addCriterion("artist between", value1, value2, "artist");
            return (Criteria) this;
        }

        public Criteria andArtistNotBetween(String value1, String value2) {
            addCriterion("artist not between", value1, value2, "artist");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andParodyIsNull() {
            addCriterion("parody is null");
            return (Criteria) this;
        }

        public Criteria andParodyIsNotNull() {
            addCriterion("parody is not null");
            return (Criteria) this;
        }

        public Criteria andParodyEqualTo(String value) {
            addCriterion("parody =", value, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyNotEqualTo(String value) {
            addCriterion("parody <>", value, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyGreaterThan(String value) {
            addCriterion("parody >", value, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyGreaterThanOrEqualTo(String value) {
            addCriterion("parody >=", value, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyLessThan(String value) {
            addCriterion("parody <", value, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyLessThanOrEqualTo(String value) {
            addCriterion("parody <=", value, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyLike(String value) {
            addCriterion("parody like", value, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyNotLike(String value) {
            addCriterion("parody not like", value, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyIn(List<String> values) {
            addCriterion("parody in", values, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyNotIn(List<String> values) {
            addCriterion("parody not in", values, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyBetween(String value1, String value2) {
            addCriterion("parody between", value1, value2, "parody");
            return (Criteria) this;
        }

        public Criteria andParodyNotBetween(String value1, String value2) {
            addCriterion("parody not between", value1, value2, "parody");
            return (Criteria) this;
        }

        public Criteria andTranslateIsNull() {
            addCriterion("`translate` is null");
            return (Criteria) this;
        }

        public Criteria andTranslateIsNotNull() {
            addCriterion("`translate` is not null");
            return (Criteria) this;
        }

        public Criteria andTranslateEqualTo(String value) {
            addCriterion("`translate` =", value, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateNotEqualTo(String value) {
            addCriterion("`translate` <>", value, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateGreaterThan(String value) {
            addCriterion("`translate` >", value, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateGreaterThanOrEqualTo(String value) {
            addCriterion("`translate` >=", value, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateLessThan(String value) {
            addCriterion("`translate` <", value, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateLessThanOrEqualTo(String value) {
            addCriterion("`translate` <=", value, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateLike(String value) {
            addCriterion("`translate` like", value, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateNotLike(String value) {
            addCriterion("`translate` not like", value, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateIn(List<String> values) {
            addCriterion("`translate` in", values, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateNotIn(List<String> values) {
            addCriterion("`translate` not in", values, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateBetween(String value1, String value2) {
            addCriterion("`translate` between", value1, value2, "translate");
            return (Criteria) this;
        }

        public Criteria andTranslateNotBetween(String value1, String value2) {
            addCriterion("`translate` not between", value1, value2, "translate");
            return (Criteria) this;
        }

        public Criteria andPathIsNull() {
            addCriterion("`path` is null");
            return (Criteria) this;
        }

        public Criteria andPathIsNotNull() {
            addCriterion("`path` is not null");
            return (Criteria) this;
        }

        public Criteria andPathEqualTo(String value) {
            addCriterion("`path` =", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotEqualTo(String value) {
            addCriterion("`path` <>", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThan(String value) {
            addCriterion("`path` >", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThanOrEqualTo(String value) {
            addCriterion("`path` >=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThan(String value) {
            addCriterion("`path` <", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThanOrEqualTo(String value) {
            addCriterion("`path` <=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLike(String value) {
            addCriterion("`path` like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotLike(String value) {
            addCriterion("`path` not like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathIn(List<String> values) {
            addCriterion("`path` in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotIn(List<String> values) {
            addCriterion("`path` not in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathBetween(String value1, String value2) {
            addCriterion("`path` between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotBetween(String value1, String value2) {
            addCriterion("`path` not between", value1, value2, "path");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table book_info
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table book_info
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}