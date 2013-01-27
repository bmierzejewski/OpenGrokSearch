package openGrokSearch.utils.openGrok;

import java.util.ArrayList;
import java.util.HashMap;

public class Result {
    private String file;
    private String phrase;
    private String path;
    private int line;

    public Result(String path) {
        path = path.replace("/source/xref", "");

        String[] pathAndLine = path.split("#");
        setLine(Integer.parseInt(pathAndLine[1]));

        String[] pathAndFileName = pathAndLine[1].split("/");
        setFile(
            pathAndLine[0].substring(
                pathAndLine[0].lastIndexOf("/") + 1,
                pathAndLine[0].length()
            )
        );

        setPath(
            pathAndLine[0].substring(
                0,
                pathAndLine[0].lastIndexOf("/")
            )
        );
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        phrase = phrase.replaceAll("<span[^>]{0,}>", "<line_start>");
        phrase = phrase.replaceAll("</span>", "</line_start>");

        phrase = phrase.replaceAll("<b>", "<phrase_start>");
        phrase = phrase.replaceAll("</b>", "</phrase_start>");

        this.phrase = phrase;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static HashMap<String, HashMap> groupResults(ArrayList<Result> results) {
        HashMap<String, HashMap> groupedResults = new HashMap<String, HashMap>();

        for(Result element : results) {
            if (!groupedResults.containsKey(element.getPath())) {
                groupedResults.put(element.getPath(), new HashMap<String, ArrayList>());
            }
            HashMap<String, ArrayList> path = groupedResults.get(element.getPath());

            if (!path.containsKey(element.getFile())) {
                path.put(element.getFile(), new ArrayList<Result>());
            }
            ArrayList<Result> file = path.get(element.getFile());

            file.add(element);
        }

        return groupedResults;
    }
}
