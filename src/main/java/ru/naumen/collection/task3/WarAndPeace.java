package ru.naumen.collection.task3;

import java.lang.constant.Constable;
import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace
{

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        // сначала нужно посчитать сколько раз встречается каждое слово в документе, для этого надо
        // 1) хранить слово и его количество
        // 2) быстро находить слово в коллекции
        // 3) для сортировки, быстро итерироваться по коллекции
        // под оба условия подходит LinkedHashMap
        Map<String, Integer> repetitionsWords = new LinkedHashMap<>();
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    //добавление за O(1)
                    repetitionsWords.put(word, repetitionsWords.getOrDefault(word, 0) + 1);
                });//O(countWordsInText)
        //repetitionsWords - m элементов

        // Отсортированная очередь.
        // Позволяет удалять наибольшие/наименьший элемент за O(log(n))
        // peek, size — O(1)
        // add за O(log(n) //mostUsedTop10
        Queue<Map.Entry<String, Integer>> mostUsedTop10 = new PriorityQueue<>(
                Map.Entry.comparingByValue());
        Queue<Map.Entry<String, Integer>> leastUsedTop10 = new PriorityQueue<>(
                Map.Entry.<String, Integer>comparingByValue().reversed());

        for (Map.Entry<String, Integer> entry : repetitionsWords.entrySet()) { //O(m)
            if (mostUsedTop10.size() < 10) {//O(1)
                mostUsedTop10.add(entry); //O(log(n))
                leastUsedTop10.add(entry);
                continue;
            }
            if (mostUsedTop10.peek().getValue() < entry.getValue()) {
                mostUsedTop10.remove(); //O(log(n))
                mostUsedTop10.add(entry); //O(log(n))
            }

            if (leastUsedTop10.peek().getValue() > entry.getValue()) {
                leastUsedTop10.remove(); //O(log(n))
                leastUsedTop10.add(entry);//O(log(n))
            }
        } // m * O(log(n))              ( 10*O(log(n)) + 4*O(log(n)) * (m-10) )

    //вывод в консоль
        System.out.println("TOP 10 наиболее используемых слов:");
        List<Map.Entry<String, Integer>> sortTop10Words = new ArrayList<>();

        while (!mostUsedTop10.isEmpty()) { //O(n)
            sortTop10Words.add(mostUsedTop10.poll());//O(1) O(log(n))
        }// O(n*log(n))
        for(int i = sortTop10Words.size() - 1; i >= 0; i--){ //O(n)
            Map.Entry<String, Integer> wordCount = sortTop10Words.get(i);//O(1)
            System.out.println(wordCount.getKey() + "-" + wordCount.getValue() + "раз(а)");
        } //O(n)
        System.out.println();
        System.out.println("LAST 10 наименее используемых:");
        while (!leastUsedTop10.isEmpty()) {//O(n)
            Map.Entry<String, Integer> wordCount = leastUsedTop10.poll(); // O(log(n))
            System.out.println(wordCount.getKey() + "-" + wordCount.getValue() + "раз(а)");
        } //O(n*log(n))
    //  3 * O(n*log(n))                  (2*O(n*log(n)) + O(n))

    }//O(countWordsInText) + m * O(log(n)) + 3 * O(n*log(n))
     // repetitionsWords - m
     // n = 10
}

