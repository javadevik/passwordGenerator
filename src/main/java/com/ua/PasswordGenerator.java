package com.ua;

import java.util.Random;

public class PasswordGenerator {
    private static PasswordGenerator instance;
    private char[] password;
    private final int capacity = 8;
    private Object[] passwords = new Object[capacity];
    private int OCCUPANCY_INDEX;

    private PasswordGenerator() {
    }

    public synchronized static PasswordGenerator getInstance() {
        if (instance == null)
            instance = new PasswordGenerator();
        return instance;
    }

    public char[] generate() {
        Random r = new Random();

        int size = capacity + r.nextInt(8);
        password = new char[size];

        for (int i = 0; i < password.length; i++) {

            if (r.nextInt(3) >= 2)
                password[i] = (r.nextInt(2) == 1)
                        ? Character.toUpperCase((char) (r.nextInt(26) + 'a'))
                        : (char) (r.nextInt(26) + 'a');
            else
                password[i] = (char) (r.nextInt(9) + 48);

        }

        return password;
    }

    public char[] generateAndSave() {
        generate();
        saveGeneratedPassword();
        return password;
    }

    private void saveGeneratedPassword() {
        if (passwords[passwords.length - 2] != null)
            extendBoundsOfArray();
        passwords[OCCUPANCY_INDEX++] = password;
    }

    private void extendBoundsOfArray() {
        Object[] temp = new Object[passwords.length + capacity];
        System.arraycopy(passwords, 0, temp, 0, OCCUPANCY_INDEX);
        passwords = temp;
    }

    public Object[] getGeneratedPasswords() {
        Object[] history = new Object[OCCUPANCY_INDEX];
        System.arraycopy(passwords, 0, history, 0, OCCUPANCY_INDEX);
        return history;
    }

    public void cleanHistoryGenerated() {
        OCCUPANCY_INDEX = 0;
        passwords = new Object[capacity];
    }

    public char[] getLastGenerated() {
        return password;
    }
}
