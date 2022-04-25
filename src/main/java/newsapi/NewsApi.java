package newsapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import newsapi.beans.NewsReponse;
import newsapi.enums.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsApi {

    public static final String DELIMITER = "&";

    public static final String NEWS_API_URL = "http://newsapi.org/v2/%s?q=%s&apiKey=%s";

    private Endpoint endpoint;
    private String q;
    private String qInTitle;
    private Country sourceCountry;
    private Category sourceCategory;
    private String domains;
    private String excludeDomains;
    private String from;
    private String to;
    private Language language;
    private SortBy sortBy;
    private String pageSize;
    private String page;
    private String apiKey;

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public String getQ() {
        return q;
    }

    public String getqInTitle() {
        return qInTitle;
    }

    public Country getSourceCountry() {
        return sourceCountry;
    }

    public Category getSourceCategory() {
        return sourceCategory;
    }

    public String getDomains() {
        return domains;
    }

    public String getExcludeDomains() {
        return excludeDomains;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Language getLanguage() {
        return language;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPage() {
        return page;
    }

    public String getApiKey() {
        return apiKey;
    }

    public NewsApi(String q, String qInTitle, Country sourceCountry, Category sourceCategory,
                   String domains, String excludeDomains, String from, String to, Language language, SortBy sortBy,
                   String pageSize, String page, String apiKey, Endpoint endpoint) {
        this.q = q;
        this.qInTitle = qInTitle;
        this.sourceCountry = sourceCountry;
        this.sourceCategory = sourceCategory;
        this.domains = domains;
        this.excludeDomains = excludeDomains;
        this.from = from;
        this.to = to;
        this.language = language;
        this.sortBy = sortBy;
        this.pageSize = pageSize;
        this.page = page;
        this.apiKey = apiKey;
        this.endpoint = endpoint;
    }

    protected String requestData() {
        String url = buildURL();
        System.out.println("URL: "+url);
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            // TOOO improve ErrorHandling
            e.printStackTrace();
        }
        HttpURLConnection con;
        StringBuilder response = new StringBuilder();
        try {
            con = (HttpURLConnection) obj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            // TOOO improve ErrorHandling
            System.out.println("Error "+e.getMessage());
        }
        return response.toString();
    }

    protected String buildURL() {
        // TODO ErrorHandling
        String urlbase = String.format(NEWS_API_URL,getEndpoint().getValue(),getQ(),getApiKey());
        StringBuilder sb = new StringBuilder(urlbase);

        try {
            urlbase = String.format(NEWS_API_URL, getEndpoint().getValue(),getApiKey());
            sb = new StringBuilder(urlbase);
        } catch (Exception e) {
            return "Error: URL is not working";
        }

        System.out.println(urlbase);

try {
    if (getFrom() != null) {
        sb.append(DELIMITER).append("from=").append(getFrom());
    } else {
        throw new NewsApiException("From is null");
    }
    if (getTo() != null) {
        sb.append(DELIMITER).append("to=").append(getTo());
    }
    if (getPage() != null) {
        sb.append(DELIMITER).append("page=").append(getPage());
    } else {
        throw new NewsApiException("To is null");
    }
    if (getPageSize() != null) {
        sb.append(DELIMITER).append("pageSize=").append(getPageSize());
    } else {
        throw new NewsApiException("getPageSize is null");
    }
    if (getLanguage() != null) {
        sb.append(DELIMITER).append("language=").append(getLanguage());
    } else {
        throw new NewsApiException("getLanguage is null");
    }
    if (getSourceCountry() != null) {
        sb.append(DELIMITER).append("country=").append(getSourceCountry());
    } else {
        throw new NewsApiException("getSourceCountry is null");
    }
    if (getSourceCategory() != null) {
        sb.append(DELIMITER).append("category=").append(getSourceCategory());
    } else {
        throw new NewsApiException("getSourceCategory is null");
    }
    if (getDomains() != null) {
        sb.append(DELIMITER).append("domains=").append(getDomains());
    } else {
        throw new NewsApiException("Domain is null");
    }
    if (getExcludeDomains() != null) {
        sb.append(DELIMITER).append("excludeDomains=").append(getExcludeDomains());
    } else {
        throw new NewsApiException("ExcludeDomain is null");
    }
    if (getqInTitle() != null) {
        sb.append(DELIMITER).append("qInTitle=").append(getqInTitle());
    } else {
        throw new NewsApiException("qInTitle is null");
    }
    if (getSortBy() != null) {
        sb.append(DELIMITER).append("sortBy=").append(getSortBy());
    } else {
        throw new NewsApiException("SortBy is null");
    }
} catch (NewsApiException e) {
    System.out.println(e.getMessage());
}
        return sb.toString();
    }

    public NewsReponse getNews() {
        NewsReponse newsReponse = null;
        String jsonResponse = requestData();
        if(jsonResponse != null && !jsonResponse.isEmpty()){

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                newsReponse = objectMapper.readValue(jsonResponse, NewsReponse.class);
                if(!"ok".equals(newsReponse.getStatus())){
                    System.out.println("Error: "+newsReponse.getStatus());
                }
            } catch (JsonProcessingException e) {
                System.out.println("Error: "+e.getMessage());
            }
        }
        //TODO improve Errorhandling
        return newsReponse;
    }
}

