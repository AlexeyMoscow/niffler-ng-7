package guru.qa.niffler.utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class UserRandom {

    public static final Faker faker = new Faker();

    public static final Random rand = new Random();


    public static String getUserName() {
        return faker.name().firstName() + "_" + rand.nextInt(1000);
    }
    public static String getPassword() {
        return faker.internet().password(6,10);
    }
}
