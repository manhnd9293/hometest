package common;

import java.util.List;

public class CategoryCountObject implements Comparable<CategoryCountObject> {
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



    @Override
    public int compareTo(CategoryCountObject compareObject) {
        if (this.getObjectId().length() != compareObject.getObjectId().length()) {
            return this.getObjectId().length() > compareObject.getObjectId().length() ? 1 : -1;
        }
        for (int i = 0; i < this.objectId.length(); i++) {
            int currentDigit = Integer.parseInt(String.valueOf(this.objectId.charAt(i)));
            int compareDigit = Integer.parseInt(String.valueOf(compareObject.getObjectId().charAt(i)));
            if (currentDigit != compareDigit) {
                return currentDigit > compareDigit ? 1 : -1;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        CategoryCountObject test = new CategoryCountObject("1", List.of(1L, 2L, 3L), null);
        CategoryCountObject test2 = new CategoryCountObject("10", List.of(1L, 2L, 3L), null);
        CategoryCountObject test3 = new CategoryCountObject("1", List.of(1L, 2L, 3L), null);
        CategoryCountObject test4 = new CategoryCountObject("2", List.of(1L, 2L, 3L), null);
        System.out.println(test.compareTo(test2));
        System.out.println(test.compareTo(test3));
        System.out.println(test.compareTo(test4));
    }


}
