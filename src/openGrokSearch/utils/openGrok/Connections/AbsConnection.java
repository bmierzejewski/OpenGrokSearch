package openGrokSearch.utils.openGrok.Connections;

import openGrokSearch.utils.Configuration;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

abstract public class AbsConnection {
    private Configuration configuration;
    private org.jsoup.Connection connection;

    public AbsConnection(Configuration configuration) {
        this.configuration = configuration;
        connection = Jsoup.connect(configuration.getLink())
                .userAgent("Mozilla")
                .timeout(3000);

        if (configuration.isSelected()) {
            connection.header("Authorization", this.getAuthorizationHeaders());
        }
    }

    protected Document getPage() throws IOException {
        Iterator it = getParams().entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            connection.data((String)pairs.getKey(), (String)pairs.getValue());
        }

        return connection.post();
    }

    public String getAuthorizationHeaders() {
        String login = configuration.getLogin() + ":" + configuration.getPassword();
        String base64login = new String(Base64.encodeBase64(login.getBytes()));

        return "Basic " + base64login;
    }

    abstract protected HashMap<String, String> getParams();
}
