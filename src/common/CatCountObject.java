package common;

import java.util.List;

public class CatCountObject {
    private String objectId;
    private List<Long> categoryId;
    private List<Long> categoryCont;

    public CatCountObject(String objectId, List<Long> categoryId, List<Long> categoryCont) {
        this.objectId = objectId;
        this.categoryId = categoryId;
        this.categoryCont = categoryCont;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public List<Long> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<Long> categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getCategoryCont() {
        return categoryCont;
    }

    public void setCategoryCont(List<Long> categoryCont) {
        this.categoryCont = categoryCont;
    }

    @Override
    public String toString() {
        return "CatCountObject{" +
                "objectId=" + objectId +
                ", categoryId=" + categoryId +
                ", categoryCont=" + categoryCont +
                '}';
    }
}
