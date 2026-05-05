package quizgame;

import java.util.ArrayList;
import java.util.List;

public final class AnswerUtils {
    private AnswerUtils() {
    }

    public static boolean matchesAny(String userAnswer, String[] acceptedAnswers) {
        String normalizedUserAnswer = normalize(userAnswer);

        for (String acceptedAnswer : acceptedAnswers) {
            if (normalize(acceptedAnswer).equalsIgnoreCase(normalizedUserAnswer)) {
                return true;
            }
        }

        return false;
    }

    public static String formatAcceptedAnswers(String[] acceptedAnswers) {
        if (acceptedAnswers.length == 0) {
            return "";
        }

        List<String> uniqueAnswers = new ArrayList<>();
        for (String answer : acceptedAnswers) {
            if (!containsIgnoreCase(uniqueAnswers, answer)) {
                uniqueAnswers.add(answer);
            }
        }

        if (uniqueAnswers.size() == 1) {
            return uniqueAnswers.get(0);
        }

        return String.join(", ", uniqueAnswers);
    }

    public static String normalize(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    private static boolean containsIgnoreCase(List<String> values, String candidate) {
        for (String value : values) {
            if (value.equalsIgnoreCase(candidate)) {
                return true;
            }
        }
        return false;
    }
}
