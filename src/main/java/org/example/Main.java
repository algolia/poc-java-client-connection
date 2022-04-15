package org.example;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchConfig;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;

import java.io.IOException;

public class Main {

    static {
        // Logging config: resources/logging.properties
        String path = Main.class.getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);
    }

    public static void main(String[] args) throws IOException {
        var config = new SearchConfig.Builder("latency", "afc3dd66dd1293e2e2736a5a51b05c0a")
                .setReadTimeOut(1) // timeout to 1ms
                .build();

        // Alternative way of creating search client:
        // var clientBuilder = HttpAsyncClientBuilder.create().setKeepAliveStrategy(new MyCustomKeepAliveStrategy()); // KeepAliveStrategy defaults to 'DefaultConnectionKeepAliveStrategy'
        // var searchClient = new SearchClient(config, new ApacheHttpRequester(config, HttpAsyncClientBuilder.create()));

        try (var searchClient = DefaultSearchClient.create(config)) {
            var index = searchClient.initIndex("instant_search", Object.class);
            var query = new Query("").setHitsPerPage(3);
            SearchResult<Object> result = index.search(query);
            System.out.println(result.getHits());
        }
    }
}
