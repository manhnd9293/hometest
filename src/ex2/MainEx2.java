package ex2;

import common.CatCountObject;
import common.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainEx2 {
    public static Map<Long, Long> getFrequencyData(List<CatCountObject> list) {
        HashMap<Long, Long> categoryIdToFrequency = new HashMap<>();
        for (CatCountObject catCountObject : list) {
            for (int i = 0; i < catCountObject.getCategoryId().size(); i++) {
                Long catId = catCountObject.getCategoryId().get(i);
                Long addFrequency = catCountObject.getCategoryId().size() > 1 ? catCountObject.getCategoryCont().get(i)  : 1L;
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
        List<CatCountObject> catCountObjects = Utils.readDataFromCsvFile("src/hash_catid_count.csv");
        getFrequencyData(catCountObjects);
    }
}
