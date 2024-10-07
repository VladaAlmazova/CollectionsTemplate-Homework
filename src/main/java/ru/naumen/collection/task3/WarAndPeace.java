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
        Map<String, Integer> repetitionsWords = new LinkedHashMap<>();
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    //добавление за O(1)
                    repetitionsWords.put(word, repetitionsWords.getOrDefault(word, 0) + 1);
                });//O(countWordsInText)
        //repetitionsWords - m элементов
        //красно-черное дерево
        Map<Integer, ArrayList<String>> sortWordsQuantity= new TreeMap<>(Collections.reverseOrder());
        //sortWordsQuantity - n элементов (n <= m)
        for(Map.Entry<String, Integer> entry:repetitionsWords.entrySet()){//.entrySet() - O(m)
            int quantity = entry.getValue();

            ArrayList<String> listWords = sortWordsQuantity.getOrDefault(quantity, new ArrayList<>());//O(log(n))
            listWords.add(entry.getKey());//O(1)
            sortWordsQuantity.put(quantity, listWords);//O(log(n))
        }//O(m*2*log(n))
        System.out.println("TOP 10 наиболее используемых слов:");
        PrintWordsWithQuantity(sortWordsQuantity, 10);//O(n)
        Map<Integer, ArrayList<String>> reversSortWordsQuantity = new TreeMap<>(sortWordsQuantity);// O(n*log(n))
        System.out.println("LAST 10 наименее используемых:");
        PrintWordsWithQuantity(reversSortWordsQuantity, 10);//O(n)
    } //O(countWordsInText + m*2*log(n) + 2n + n*log(n)) = O(countWordsInText + (2m+n)*log(n) + 2n)

    public static void PrintWordsWithQuantity(Map<Integer, ArrayList<String>> sortWordsQuantity, int count){
        int countOutputWords = 0;
        //итераций цикла будет не больше count
        for(Map.Entry<Integer, ArrayList<String>> entry:sortWordsQuantity.entrySet()){ //.entrySet() - O(n)
            List<String> words = entry.getValue();
            int quantity = entry.getKey();
            int indexWord = 0;
            //итераций цикла будет не больше count
            while(countOutputWords < count && indexWord < words.size()){
                System.out.println(words.get(indexWord) + " - " + quantity + " раз(а)");
                indexWord++;
                countOutputWords++;
            }
            if(countOutputWords == count)
                break;
        }
        // так как count по условию небольшое число, им можно принебречь при вычислении временной сложности
    }
}
