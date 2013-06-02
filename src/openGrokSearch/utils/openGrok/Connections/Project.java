package openGrokSearch.utils.openGrok.Connections;

import openGrokSearch.utils.Configuration;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class Project extends AbsConnection {
    private Document doc;

    public Project(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected HashMap<String, String> getParams() {
        return new HashMap<String, String>();
    }

    public String[] getProjects() throws IOException {
        String[] results;
        Document doc = getPage();
        Elements projects = doc.select("select#project.q option");

        results = new String[projects.size()];
        int i = 0;
        for (Element src : projects) {
            results[i] = src.attr("value");
            i++;
        }

        return results;
    }

    public String getDefaultProject() {
        String defaultProject;
        try {
            Document doc = getPage();
            Element project = doc.select("select#project.q option[selected=selected]").first();
            if (project == null) {
                project = doc.select("select#project.q option").first();
            }
            defaultProject = project.attr("value");
        } catch (IOException e) {
            defaultProject = null;
        }
        return defaultProject;
    }

    @Override
    public Document getPage() throws IOException {
        if (doc == null) {
            doc = super.getPage();
        }

        return doc;
    }
}
