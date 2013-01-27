package openGrokSearch;

import openGrokSearch.utils.openGrok.Connections.SearchResult;
import openGrokSearch.utils.openGrok.Result;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bartosz.mierzejewski
 * Date: 20.01.2013
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String [ ] args)
    {
//        Configuration configuration = Configuration.getInstance();
//        configuration.setLink("http://src.opensolaris.org/source/");

        SearchResult ogp = new SearchResult(null);
        ogp.setFullSearchPhrase("asd");
        ArrayList<Result> results = ogp.search();
        for (Result res : results) {
            System.out.println(res.getPhrase());
        }
    }
}
