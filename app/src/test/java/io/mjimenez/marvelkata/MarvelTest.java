package io.mjimenez.marvelkata;

import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.mjimenez.marvelkata.Marvel.Comic;

public class MarvelTest {

    @NonNull private Marvel givenAMarvelClient() {
        return new Marvel("97f295907072a970c5df30d73d1f3816", "ed54a875c0dffad1fa6af84e66ff104434a1c6cc");
    }

    @Test
    public void urlIsBuiltCorrectly() {
        Marvel marvel = givenAMarvelClient();

        String url = marvel.buildUrl(987L);

        Assert.assertEquals("http://gateway.marvel.com:80/v1/public/comics?" +
                "dateDescriptor=nextWeek&ts=987&apikey=97f295907072a970c5df30d73d1f3816&" +
                "hash=abfa1c1d42a73a5eab042242335d805d", url);
    }

    @Test
    public void thumbnailIsCorrectlyParsed() {
        Thumbnail thumbnailResponse = new Thumbnail("http://foo.bar/foo", "jpg");

        Assert.assertEquals("http://foo.bar/foo.jpg", thumbnailResponse.getUrl());
    }

    @Test
    public void responseIsCorrectlyMapped() {
        ComicsResponse apiResponse = new ComicsResponse(
                new Data(
                        Arrays.asList(
                                new ComicResponse(
                                        "Titulo",
                                        Arrays.asList(new Price(10.0)),
                                        new Thumbnail("http://foo.bar/foo", "jpg")
                                )
                        )
                )
        );
        Comic expectedMappedComic = new Comic("Titulo", "10.0", "http://foo.bar/foo.jpg");

        List<Comic> comics = new ComicsMapper().map(apiResponse);

        Assert.assertEquals(comics.get(0), expectedMappedComic);
    }

    @Test
    public void testInProgress() throws Exception {
        //TODO: WIP
        Assert.assertEquals("", givenAMarvelClient().getFutureComics());
    }
}