package com.company.hit.dm;

import java.util.Objects;

/**
 * store information items
 * id : id of an item
 * content : contents stored in the item
 *
 * @param <T> : type of item content
 */
public class DataModel<T>
        extends java.lang.Object
        implements java.io.Serializable {

    private java.lang.Long id;
    private T content;

    /**
     * constructor - initialize variables
     */
    public DataModel(java.lang.Long id, T content) {
        this.id = id;
        this.content = content;
    }

    /**
     * compare to another data object
     *
     * @param o : the other object
     * @return true if equal and false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModel<?> dataModel = (DataModel<?>) o;
        return id.equals(dataModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return string contains data of current item
     */
    @Override
    public String toString() {
        return "DataModel {" +
                "id=" + id +
                ", content = " + content +
                '}';
    }

    /**
     * @return id of data model
     */
    public Long getDataModelId() {
        return id;
    }

    /**
     * set id of data model to parameter id
     */
    public void setDataModelId(Long id) {
        this.id = id;
    }

    /**
     * @return content of data object
     */
    public T getContent() {
        return content;
    }

    /**
     * set content of data object to parameter content
     */
    public void setContent(T content) {
        this.content = content;
    }
}
