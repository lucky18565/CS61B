package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    Map<String, TimeSeries> words = new HashMap<>();
    TimeSeries counts = new TimeSeries();
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In in = new In(wordsFilename);
        In in2 = new In(countsFilename);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] w = line.split("\t"); //把一行分为一个
            String word = w[0];
            int year = Integer.parseInt(w[1]);
            double count = Double.parseDouble(w[2]); //定义变量
            TimeSeries ts = words.get(word);
            if (ts == null) {
                ts = new TimeSeries();
                words.put(word, ts);
            }
            ts.put(year, count);
        }
        while (in2.hasNextLine()) {
            String line = in2.readLine();
            String[] c = line.split(",");
            int year = Integer.parseInt(c[0]);
            Double count = Double.parseDouble(c[1]);
            counts.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!words.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(words.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if (!words.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(words.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return new TimeSeries(counts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!words.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries ts = words.get(word).dividedBy(counts);
        return new TimeSeries(ts, startYear, endYear);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        if (!words.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(words.get(word).dividedBy(counts), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all inputWords in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> inputWords,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for (String word : inputWords) {
            TimeSeries ts = weightHistory(word, startYear, endYear);
            result = result.plus(ts);
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all inputWords in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> inputWords) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();
        for (String word : inputWords) {
            TimeSeries ts = weightHistory(word);
            result = result.plus(ts);
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
