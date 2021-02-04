package ex2;

import common.CategoryCountObject;
import common.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainEx2 {
    public static Map<Long, Long> getFrequencyData(List<CategoryCountObject> list) {
        HashMap<Long, Long> categoryIdToFrequency = new HashMap<>();
        for (CategoryCountObject categoryCountObject : list) {
            for (int i = 0; i < categoryCountObject.getCategoryId().size(); i++) {
                Long catId = categoryCountObject.getCategoryId().get(i);
                Long addFrequency = categoryCountObject.getCategoryId().size() > 1 ? categoryCountObject.getCategoryCont().get(i)  : 1L;
                if (!categoryIdToFrequency.containsKey(catId)) {
                    categoryIdToFrequency.put(catId, addFrequency);
                } else {
                    categoryIdToFrequency.put(catId, categoryIdToFrequency.get(catId) + addFrequency);
                }
            }
        }
        return categoryIdToFrequency;

    }

    public static void main(String[] args) {
        List<CategoryCountObject> categoryCountObjects = Utils.readDataFromCsvFile("src/hash_catid_count.csv");
        getFrequencyData(categoryCountObjects);
    }
}
