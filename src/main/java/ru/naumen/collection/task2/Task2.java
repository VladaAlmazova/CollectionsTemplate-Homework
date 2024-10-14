package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2
{

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        Set<User> setCollA = new HashSet<>(collA); //хранит по хэшу без дубликатов. сложность копирования всех элементов O(n)
        List<User> result = new ArrayList<>(); //хранит дубликаты пользователей. Добавление в конец быстрее чем у LinkedList
        for(User user:collB){ // цикл O(n)
            if(setCollA.contains(user)){ // поиск элемента по хэшу O(1)
                result.add(user); //добавление в конец O(1)
            }
        }
        //в итоге сложность O(n) (если точнее O(2n))
        return result;
    }
}
