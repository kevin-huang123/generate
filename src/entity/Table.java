package entity;

import java.util.List;

/**
 * 表
 */
public class Table {

    private String name;//表名
    private String entityName;//实体类名
    private String entityNameHumpCase;//实体类名（驼峰命名）
    private String comment;//表注释
    private List<Column> columns;//表所含的列

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityNameHumpCase() {
        String firstLetter = entityName.substring(0, 1);
        return firstLetter.toLowerCase() + entityName.substring(1);
    }

    public void setEntityNameHumpCase(String entityNameHumpCase) {
        this.entityNameHumpCase = entityNameHumpCase;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
