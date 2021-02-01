package common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    public static List<CatCountObject> readDataFromCsvFile() {
        List<CatCountObject> records = new LinkedList<>();
        final String FILE_PATH = "src/hash_catid_count.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                String objectId = values[0];
                List<Long> catIdList = getLongList(values[1]);
                List<Long> catCountList = getLongList(values[2]);
                records.add(new CatCountObject(objectId, catIdList, catCountList));
            }
            return records;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            ;
            e.printStackTrace();
        }
        return null;
    }

    public static List<Long> getLongList(String idStringList) {
        LinkedList<Long> res = new LinkedList<>();
        String substring = idStringList.substring(1, idStringList.length() - 1);
        if (substring.length() == 0) {
            return res;
        }
        String[] split = substring.split(",");
        for (String id : split) {
            res.add(Long.parseLong(id));
        }
        return res;

    }

}
