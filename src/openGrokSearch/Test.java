package openGrokSearch;

import openGrokSearch.utils.openGrok.Connections.SearchResult;
import openGrokSearch.utils.openGrok.Result;

import java.util.ArrayList;

public class Test {
    public static void main(String [ ] args)
    {
        try {
            SearchResult ogp = new SearchResult(null);
            ogp.setFullSearchPhrase("asd");
            ArrayList<Result> results = ogp.search();
            for (Result res : results) {
                System.out.println(res.getPhrase());
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
