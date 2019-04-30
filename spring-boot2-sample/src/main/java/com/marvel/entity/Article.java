package com.marvel.entity;

import java.util.Date;
import javax.persistence.*;

/**
 * 文章信息
 *
 * @author : Jarvis
 * @date : 2018/06/02
 */
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String subhead;

    private String author;

    @Column(name = "in_date")
    private Date inDate;

    @Column(name = "in_userid")
    private Integer inUserid;

    @Column(name = "edit_date")
    private Date editDate;

    @Column(name = "edit_userid")
    private Integer editUserid;

    private String content;

    public Article() {
    }

    public Article(String title, String subhead, String author, Date inDate, Integer inUserid, Date editDate, Integer editUserid, String content) {
        this.title = title;
        this.subhead = subhead;
        this.author = author;
        this.inDate = inDate;
        this.inUserid = inUserid;
        this.editDate = editDate;
        this.editUserid = editUserid;
        this.content = content;
    }


    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return subhead
     */
    public String getSubhead() {
        return subhead;
    }

    /**
     * @param subhead
     */
    public void setSubhead(String subhead) {
        this.subhead = subhead == null ? null : subhead.trim();
    }

    /**
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     * @return in_date
     */
    public Date getInDate() {
        return inDate;
    }

    /**
     * @param inDate
     */
    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    /**
     * @return in_userid
     */
    public Integer getInUserid() {
        return inUserid;
    }

    /**
     * @param inUserid
     */
    public void setInUserid(Integer inUserid) {
        this.inUserid = inUserid;
    }

    /**
     * @return edit_date
     */
    public Date getEditDate() {
        return editDate;
    }

    /**
     * @param editDate
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    /**
     * @return edit_userid
     */
    public Integer getEditUserid() {
        return editUserid;
    }

    /**
     * @param editUserid
     */
    public void setEditUserid(Integer editUserid) {
        this.editUserid = editUserid;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}