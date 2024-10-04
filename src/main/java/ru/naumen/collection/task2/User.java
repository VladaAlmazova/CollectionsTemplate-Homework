package ru.naumen.collection.task2;

import java.util.Arrays;

/**
 * Пользователь
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class User {
    private String username;
    private String email;
    private byte[] passwordHash;

    //переопределение методов чтобы можно было сравнивать экземпляры класса и хранить их по хэшу
    @Override
    public int hashCode() {
        return username.hashCode() + email.hashCode() + Arrays.hashCode(passwordHash);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(obj instanceof User){
            User that = (User) obj;
            return username.equals(that.username) && email.equals(that.email) && Arrays.equals(passwordHash, that.passwordHash);
        }
        return false;
    }
}
