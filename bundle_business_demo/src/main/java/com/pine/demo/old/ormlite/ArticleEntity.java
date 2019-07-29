package com.pine.demo.old.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

@DatabaseTable(tableName = "tb_article")
public class ArticleEntity {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField(columnName = "author_id", foreign = true)
    private PersonEntity author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PersonEntity getAuthor() {
        return author;
    }

    public void setAuthor(PersonEntity author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "article [id=" + id + ", title=" + title + ", author=" + author + "]";
    }
}
