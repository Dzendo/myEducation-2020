package KotlinAsFirst.Regex;

import java.util.List;
import java.util.Map;
// https://geekquestion.com/15088685-kak-poschitat-kolichestvo-vkhozhdenij-slov-v-tekst/
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Word1 {
    public static void main(String[] args) {
/*
        // map the words to their count
        Map<String, Integer> frequencyMap = words.stream()
                .collect(toMap(
                        s -> s, // key is the word
                        s -> 1, // value is 1
                        Integer::sum)); // merge function counts the identical words

// find the top 10
        List<String> top10 = words.stream()
                .sorted(comparing(frequencyMap::get).reversed()) // sort by descending frequency
                .distinct() // take only unique values
                .limit(10)   // take only the first 10
                .collect(toList()); // put it in a returned list

        System.out.println("top10 = " + top10);
*/
    }
}
