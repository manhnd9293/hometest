package common;

import java.util.List;

public class CategoryCountObject {
    private String objectId;
    private List<Long> categoryId;
    private List<Long> categoryCont;

    public CategoryCountObject(String objectId, List<Long> categoryId, List<Long> categoryCont) {
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

    public String getCsvString() {
        return objectId + "\t" + getIdStringFromList(categoryId) + "\t" + getIdStringFromList(categoryCont);
    }

    private String getIdStringFromList(List<Long> idList) {
        if (idList == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        idList.forEach(id -> sb.append(id + ","));
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        CategoryCountObject test = new CategoryCountObject("1", List.of(1L, 2L, 3L), null);
        String csvString = test.getCsvString();
        System.out.println(csvString);

    }

}
