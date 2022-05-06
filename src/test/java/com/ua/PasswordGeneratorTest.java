package com.ua;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PasswordGeneratorTest {

    @Test
    public void generate() {
        PasswordGenerator generator = PasswordGenerator.getInstance();
        char[] password = generator.generate();
        assertNotNull(password);
        System.out.println(Arrays.toString(password));
    }

    @Test
    public void getGeneratedPasswords() {
        PasswordGenerator generator = PasswordGenerator.getInstance();

        int countPasswordsToGenerate = 10;
        Object[] generatedPasswords = new Object[countPasswordsToGenerate];

        for (int i = 0; i < countPasswordsToGenerate; i++) {
            generatedPasswords[i] = generator.generateAndSave();
        }

        Object[] historyPasswords = generator.getGeneratedPasswords();

        assertEquals(generatedPasswords, historyPasswords);

        System.out.println("-----Generated pass:-----");
        Arrays.stream(generatedPasswords)
                        .forEach(o -> System.out.println(Arrays.toString( (char[])o) ));

        System.out.println("-----History pass:-----");
        Arrays.stream(historyPasswords)
                .forEach(o -> System.out.println(Arrays.toString( (char[])o) ));

        generator.cleanHistoryGenerated();
    }

    @Test(timeout = 100)
    public void timeLimitWithoutSavingGenerated() {
        PasswordGenerator generator = PasswordGenerator.getInstance();

        int countPasswordsToGenerate = 20_000;

        for (int i = 0; i < countPasswordsToGenerate; i++)
            generator.generate();
    }

    @Test(timeout = 200)
    public void timeLimitWithSavingGenerated() {
        PasswordGenerator generator = PasswordGenerator.getInstance();

        int countPasswordsToGenerate = 20_000;

        for (int i = 0; i < countPasswordsToGenerate; i++)
            generator.generateAndSave();

        generator.cleanHistoryGenerated();
    }

    @Test
    public void getLastGenerated() {
        PasswordGenerator generator = PasswordGenerator.getInstance();
        char[] generatedPass = generator.generate();
        char[] lastPass = generator.getLastGenerated();
        assertEquals(generatedPass, lastPass);
    }
}