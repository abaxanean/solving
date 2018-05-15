package com.bax.codefights;

import java.util.function.IntPredicate;

/**
 * <pre>
 * Setting a strong password is very important. You want to protect your account from being stolen, hacked, or otherwise abused, don't you? If so, check out this wiki page about password strength.
 *
 * There are plenty of algorithms that validate the strength of a password, and people come up with new solutions all the time. For example, the author of this topic came up with their own simple algorithm. Your task is to implement it!
 *
 * Here's how the algorithm should evaluate a password's strength:
 *
 * First, calculate the number of unique characters in the password, and split them into the following groups:
 * lowercase English letters;
 * uppercase English letters;
 * digits;
 * other characters.
 * Next, store the repetition factor for each group using the formula rep_factor = 1 - (1 - growth_factor)group_size. The group_size is determined on the previous step, and the growth_factor for each group is given below (respectively):
 * 0.25
 * 0.4
 * 0.4
 * 0.5
 * At this step, calculate the digit strength of the password using the formula (sum(repetition_factor * max_group_size))password.length. Here, the repetition_factor is the value found on the previous step, password.length is the size of the password, and max_group_size is the maximum size of the group as given below (respectively):
 * 26
 * 26
 * 10
 * 32
 * Finally, calculate the resulting strength as log2strength, where strength is the value obtained on the previous step.
 * Given a password, calculate its strength using the algorithm given above.
 * <pre>
 */
public class PasswordStrength {

    public static void main(String[] args) {
        System.out.println(new PasswordStrength().passwordStrength("abcc73?"));
    }

    double passwordStrength(String password) {
        int lowerCase = uniqueCount(password, Character::isLowerCase);
        int upperCase = uniqueCount(password, Character::isUpperCase);
        int digits = uniqueCount(password, Character::isDigit);
        int other = uniqueCount(password, ((IntPredicate)Character::isLetterOrDigit).negate());

        double rfLowerCase = repetitionFactor(0.25, lowerCase);
        double rfUpperCase = repetitionFactor(0.4, upperCase);
        double rfDigits = repetitionFactor(0.4, digits);
        double rfOther = repetitionFactor(0.5, other);

        double digitStrength = Math.pow(rfLowerCase * 26 + rfUpperCase * 26 + rfDigits * 10 + rfOther * 32, password.length());
        return Math.log(digitStrength) / Math.log(2);
    }

    private double repetitionFactor(double growthFactor, int groupSize) {
        return 1 - Math.pow(1 - growthFactor, groupSize);
    }

    int uniqueCount(String s, IntPredicate predicate) {
        return (int)s.chars().filter(predicate).distinct().count();
    }

}
