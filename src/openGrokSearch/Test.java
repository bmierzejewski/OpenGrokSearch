package openGrokSearch;

import com.google.gson.Gson;
import openGrokSearch.utils.openGrok.Connections.SearchResult;
import openGrokSearch.utils.openGrok.Result;

import java.util.ArrayList;

public class Test {
    public static void main(String [ ] args)
    {
//        try {
//            SearchResult ogp = new SearchResult(null);
//            ogp.setFullSearchPhrase("asd");
//            ArrayList<Result> results = ogp.search();
//            for (Result res : results) {
//                System.out.println(res.getPhrase());
//            }
//        } catch (Exception e) {
//            System.out.println("error");
//        }

//        ArrayList<ArrayList> arr = new ArrayList<ArrayList>();
//        ArrayList<String> elem = new ArrayList<String>();
//        elem.add("test 1");
//        elem.add("test 2");
//        elem.add("test 3");
//        elem.add("test 4");
//        arr.add(elem);
//        arr.add(elem);
//
//        Gson gson = new Gson();
//        String to = gson.toJson(arr);
//
//        System.out.println(arr);
//        ArrayList<ArrayList> arr2 = gson.fromJson(to, ArrayList.class);
//        arr2.add(elem);
//        System.out.println(arr2);

//        String oko = "/core/oovbaapi/ooo/vba/excel/test";
//        System.out.println(oko.replaceAll("/core/oovbaapi/ooo/vba/excel/test", "DUPA"));

        String a = "50";
        String b = "100";

        System.out.println(Math.max(Integer.parseInt(a), Integer.parseInt(b)));
        System.out.println(Math.max(Integer.parseInt(a), Integer.parseInt(b)) / 25);
    }
}
