package it.unibo.sampleapp;

import com.diffplug.common.base.Errors;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.BooksRequestInitializer;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.tools.OmdbBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

public final class App {
    private static final String DEFAULT_AUTHOR = "Camilleri";
    private static final String GOOGLE_BOOKS_API_KEY = System.getenv("GOOGLE_BOOKS_API_KEY");
    private static final String OMDB_API_KEY = System.getenv("OMDB_API_KEY");
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private App() {
        // the constructor will never be called directly.
    }

    public static void main(final String[] args) throws IOException, GeneralSecurityException {
        final OmdbApi omdb = new OmdbApi(OMDB_API_KEY);
        final String author = Arrays.stream(args).findFirst().orElse(DEFAULT_AUTHOR);

        new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
            .setApplicationName("sample-gradle-app")
            .setGoogleClientRequestInitializer(new BooksRequestInitializer(GOOGLE_BOOKS_API_KEY))
            .build()
            // 0. Here we get a Books object (API façade)
            .volumes().list("inauthor: " + author)
            .execute().getItems().parallelStream()
            .filter(v -> v
                    .getVolumeInfo()
                    .getAuthors()
                    .stream()
                    .anyMatch(a -> a
                            .toLowerCase(Locale.getDefault())
                            .contains(author.toLowerCase(Locale.getDefault()))))
            .map(v -> v.getVolumeInfo().getTitle())
            // 1. Here we get a stream of book titles
            .flatMap(Errors
                    .rethrow()
                    .<String, Stream<Pair<String, String>>>wrap(title -> Optional.ofNullable(omdb
                            .search(new OmdbBuilder()
                                    .setSearchTerm(URLEncoder.encode(title, StandardCharsets.UTF_8))
                                    .build())
                            .getResults())
                    .map(r -> r.stream().map(m -> Pair.of(m.getTitle(), m.getYear()))
            ).orElseGet(Stream::empty)))
            // 2. Here we get a stream of movies as pairs (title, year)
            .distinct().sorted(Comparator.comparing(Pair::getRight))
            // 3. Here we process results by omitting duplicates and sorting them before printing them
            .forEach(x -> LOGGER.info(x.toString()));
    }
}
