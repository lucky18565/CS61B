import browser.NgordnetQuery;
import main.HistoryTextHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static utils.Utils.SHORT_WORDS_FILE;
import static utils.Utils.TOTAL_COUNTS_FILE;

/**
 * Edge-case tests across core proj2a components.
 */
public class ProjectEdgeCasesTest {
    @Test
    public void testTimeSeriesDividedByWithEmptyNumerator() {
        TimeSeries numerator = new TimeSeries();
        TimeSeries denominator = new TimeSeries();
        denominator.put(2000, 10.0);

        TimeSeries result = numerator.dividedBy(denominator);
        assertThat(result.years()).isEmpty();
        assertThat(result.data()).isEmpty();
    }

    @Test
    public void testTimeSeriesPlusWithDisjointYears() {
        TimeSeries a = new TimeSeries();
        a.put(1999, 1.0);

        TimeSeries b = new TimeSeries();
        b.put(2001, 2.5);

        TimeSeries sum = a.plus(b);
        assertThat(sum.get(1999)).isWithin(1E-10).of(1.0);
        assertThat(sum.get(2001)).isWithin(1E-10).of(2.5);
        assertThat(sum.get(2000)).isNull();
    }

    @Test
    public void testNGramMapDefensiveCopy() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);

        TimeSeries copy = ngm.countHistory("request");
        copy.put(9999, 1.0);

        TimeSeries reread = ngm.countHistory("request");
        assertThat(reread.get(9999)).isNull();
    }

    @Test
    public void testNGramMapSummedWeightIgnoresInvalidWords() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);

        List<String> mixedWords = new ArrayList<>();
        mixedWords.add("request");
        mixedWords.add("notaword");

        TimeSeries mixed = ngm.summedWeightHistory(mixedWords, 2006, 2007);
        TimeSeries requestOnly = ngm.weightHistory("request", 2006, 2007);

        assertThat(mixed.years()).isEqualTo(requestOnly.years());
        assertThat(mixed.data()).isEqualTo(requestOnly.data());
    }

    @Test
    public void testHistoryTextHandlerFormattingAndInvalidWord() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);
        HistoryTextHandler handler = new HistoryTextHandler(ngm);

        NgordnetQuery query = new NgordnetQuery(List.of("notaword", "request"), 2006, 2007, 0);
        String actual = handler.handle(query);

        String expected = "notaword: {}\n"
                + "request: " + ngm.weightHistory("request", 2006, 2007) + "\n";

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testHistoryTextHandlerWithEmptyWordList() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);
        HistoryTextHandler handler = new HistoryTextHandler(ngm);

        NgordnetQuery query = new NgordnetQuery(List.of(), 2000, 2020, 0);
        assertThat(handler.handle(query)).isEqualTo("");
    }
}
