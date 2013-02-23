package openGrokSearch.utils.openGrok.Connections;

import openGrokSearch.utils.Configuration;
import openGrokSearch.utils.openGrok.Result;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchResult extends AbsConnection {
    private HashMap<String, String> params;
    private Document lastDoc;

    public SearchResult(Configuration configuration) {
        super(configuration);

        params = new HashMap<String, String>();
    }

    public void setFullSearchPhrase(String value) {
        params.put("q", value);
    }

    public void setDefinition(String value) {
        params.put("defs", value);
    }

    public void setSymbol(String value) {
        params.put("refs", value);
    }

    public void setFilePath(String value) {
        params.put("path", value);
    }

    public void setHistory(String value) {
        params.put("hist", value);
    }

    public void setProject(String value) {
        params.put("project", value);
    }

    @Override
    protected HashMap<String, String> getParams() {
        return params;
    }

    public ArrayList<Result> search() throws IOException {
        ArrayList<Result> results = new ArrayList<Result>();
        int numberOfPages = getNumberOfPages();

        if (numberOfPages == 1) {
            results = getResultFromPage(lastDoc);
        } else {
            results = getResultFromPages(numberOfPages);
        }

        return results;
    }

    private int getNumberOfPages() throws IOException {
        int numberOfPages = 0;
//        try {
            lastDoc = getPage();
            Elements elements = lastDoc.select("div#results b");

            if (elements.size() >= 3) {
                numberOfPages = (int)Math.ceil(Float.parseFloat(elements.get(2).html()) / 25);
            }
//        } catch (IOException e) {
//            numberOfPages = 0;
//        } catch (NumberFormatException e) {
//            numberOfPages = 0;
//        }

        return numberOfPages;
    }

    private ArrayList<Result> getResultFromPage(Document doc) {
        Elements lines = doc.select("div#results table tbody tr td tt.con a.s");

        ArrayList<Result> results = new ArrayList<Result>();
        for (Element element : lines) {
            Result result = new Result(element.attr("href"));
            result.setPhrase(element.html());

            results.add(result);
        }
        return results;
    }

    private ArrayList<Result> getResultFromPages(int page) {
        ArrayList<Result> results = new ArrayList<Result>();
        for (int i = 0; i < page; i++) {
            if (params.containsKey("start")) {
                params.remove("start");
            }
            params.put("start", String.valueOf(page * 25));

            try {
                lastDoc = getPage();
                results.addAll(getResultFromPage(lastDoc));
            } catch (IOException e) {

            }
        }
        return results;
    }
}
