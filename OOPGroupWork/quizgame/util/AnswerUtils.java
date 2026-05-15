// AnswerUtils.java
package OOPGroupWork.quizgame.util;

import java.util.ArrayList;
import java.util.List;
/**
 * Utility class for comparing, normalizing, and formatting quiz answers.
 */
public final class AnswerUtils {

    /**
     * Private constructor to prevent creating objects of this utility class.
     */
    private AnswerUtils() {
    }

    /**
     * Checks whether the user's answer matches any accepted answer.
     *
     * @param userAnswer the answer entered by the user
     * @param acceptedAnswers the accepted correct answers
     * @return {@code true} if the user's answer matches one of the accepted answers;
     * otherwise {@code false}
     */
    public static boolean matchesAny(String userAnswer, String[] acceptedAnswers) {
        String normalizedUserAnswer = normalize(userAnswer);

        for (String acceptedAnswer : acceptedAnswers) {
            if (normalize(acceptedAnswer).equalsIgnoreCase(normalizedUserAnswer)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Formats accepted answers into a comma-separated string.
     *
     * @param acceptedAnswers the accepted answers to format
     * @return a comma-separated string of unique accepted answers
     */
    public static String formatAcceptedAnswers(String[] acceptedAnswers) {
        List<String> uniqueAnswers = new ArrayList<>();
        for (String answer : acceptedAnswers) {
            if (!containsIgnoreCase(uniqueAnswers, answer)) {
                uniqueAnswers.add(answer);
            }
        }
        return String.join(", ", uniqueAnswers);
    }

    /**
     * Normalizes an answer by trimming spaces and replacing multiple spaces
     * with a single space.
     *
     * @param value the answer value to normalize
     * @return the normalized answer, or an empty string if the value is {@code null}
     */
    public static String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replaceAll("\\s+", " ");
    }

    /**
     * Checks whether a list contains a value while ignoring case.
     *
     * @param values the list of values to check
     * @param candidate the value to search for
     * @return {@code true} if the list contains the value; otherwise {@code false}
     */
    private static boolean containsIgnoreCase(List<String> values, String candidate) {
        for (String value : values) {
            if (value.equalsIgnoreCase(candidate)) {
                return true;
            }
        }
        return false;
    }
}