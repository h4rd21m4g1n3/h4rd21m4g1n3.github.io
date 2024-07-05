package cz.cvut.fel.nss.sem.utils;

import cz.cvut.fel.nss.sem.model.Role;
import cz.cvut.fel.nss.sem.model.User;

import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    public static User generateUser(int num, int userNum){
        final User user = new User();
        user.setId(getRandomLong(100L));
        user.setRole(Role.USER);
        user.setEmail(generateEmail(num));
        user.setUsername(generateUsername(userNum));
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword("pass");

        return user;
    }

    public static String generateEmail(int n) {
        String emailPrefix = String.format("%03d", n); // Pad the number with leading zeros if necessary
        String emailDomain = "example.com"; // Replace with your desired domain

        return emailPrefix + "@" + emailDomain;
    }

    public static String generateUsername(int number) {
        String prefix = "user";
        return prefix + number;
    }

    public static int getRandomInt(int bound){
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static Long getRandomLong(Long bound){
        Long res = ThreadLocalRandom.current().nextLong(bound);

        while (res == 0){
            res = ThreadLocalRandom.current().nextLong(bound);
        }


        return res;
    }

    public static char getRandomChar(){
        int randomInt = ThreadLocalRandom.current().nextInt(65, 91); // Generates random int between 65 (inclusive) and 91 (exclusive)
        return (char) randomInt;
    }

}
