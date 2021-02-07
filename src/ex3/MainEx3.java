package ex3;

import common.CategoryCountObject;
import common.Utils;

import java.util.List;

public class MainEx3 {
    public static void main(String[] args) {
        List<CategoryCountObject> categoryCountObjects = Utils.readDataFromCsvFile("src/hash_catid_count.csv");
    }
}
