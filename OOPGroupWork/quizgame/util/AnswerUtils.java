package OOPGroupProject.OOPGroupWork.quizgame.util;

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
        List<String> uniqueAnswers = new ArrayList<>();
        for (String answer : acceptedAnswers) {
            if (!containsIgnoreCase(uniqueAnswers, answer)) {
                uniqueAnswers.add(answer);
            }
        }
        return String.join(", ", uniqueAnswers);
    }

    public static String normalize(String value) {
        if (value == null) {
            return "";
        }
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
