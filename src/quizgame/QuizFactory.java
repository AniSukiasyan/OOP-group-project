package quizgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizFactory {
    private static final int QUIZ_SIZE = 100;
    private final Random random = new Random();

    public Quiz createOopQuiz() {
        Quiz quiz = new Quiz("Object-Oriented Programming Quiz");
        List<Question> questionPool = new ArrayList<>();

        QuestionLoader loader = new QuestionLoader();

        Question[] fileQuestions = loader.loadFromTextFile("questions.txt");

        for (Question q : fileQuestions) {
            if (q != null) {
                questionPool.add(q);
            }
        }

        addCodeCompletionQuestions(questionPool);
        addCodeOutputQuestions(questionPool);
        addNumberSystemQuestions(questionPool);

        questionPool = deduplicateByPrompt(questionPool);
        Collections.shuffle(questionPool, random);

        int limit = Math.min(QUIZ_SIZE, questionPool.size());
        for (int i = 0; i < limit; i++) {
            quiz.addQuestion(questionPool.get(i));
        }

        return quiz;
    }

    private void addCodeCompletionQuestions(List<Question> questions) {
        for (int i = 0; i < 10; i++) {
            int value = random.nextInt(41) + 10;
            questions.add(new CodeCompletionQuestion(
                    "Complete the missing return statement:\n\n"
                            + "public int getValue() {\n"
                            + "    ________ " + value + ";\n"
                            + "}",
                    5,
                    "return"
            ));
        }

        for (int i = 0; i < 10; i++) {
            String field = pick(new String[]{"name", "age", "salary", "id", "grade"});
            String type = field.equals("age") || field.equals("id") ? "int" : "String";
            questions.add(new CodeCompletionQuestion(
                    "Complete the setter method name:\n\n"
                            + "private " + type + " " + field + ";\n\n"
                            + "public void ________(" + type + " " + field + ") {\n"
                            + "    this." + field + " = " + field + ";\n"
                            + "}",
                    5,
                    "set" + capitalize(field)
            ));
        }
    }

    private void addCodeOutputQuestions(List<Question> questions) {
        for (int i = 0; i < 10; i++) {
            int a = random.nextInt(9) + 1;
            int b = random.nextInt(9) + 1;
            int result = a + b;
            questions.add(new FillInBlankQuestion(
                    "What is the output of this Java code?\n\n"
                            + "int a = " + a + ";\n"
                            + "int b = " + b + ";\n"
                            + "System.out.println(a + b);",
                    5,
                    String.valueOf(result)
            ));
        }

        for (int i = 0; i < 5; i++) {
            String left = pick(new String[]{"Hello", "Java", "OOP", "Quiz", "Code"});
            String right = pick(new String[]{"World", "Game", "Class", "Player", "Time"});
            String output = left + right;
            questions.add(new FillInBlankQuestion(
                    "What is the output of this Java code?\n\n"
                            + "String first = \"" + left + "\";\n"
                            + "String second = \"" + right + "\";\n"
                            + "System.out.println(first + second);",
                    5,
                    output
            ));
        }

        for (int i = 0; i < 5; i++) {
            int value = random.nextInt(20) + 1;
            int after = value + 1;
            questions.add(new FillInBlankQuestion(
                    "What is the output of this Java code?\n\n"
                            + "int x = " + value + ";\n"
                            + "x++;\n"
                            + "System.out.println(x);",
                    5,
                    String.valueOf(after)
            ));
        }

        for (int i = 0; i < 5; i++) {
            boolean flag = random.nextBoolean();
            questions.add(new FillInBlankQuestion(
                    "What is the output of this Java code?\n\n"
                            + "boolean flag = " + flag + ";\n"
                            + "System.out.println(!flag);",
                    5,
                    String.valueOf(!flag)
            ));
        }
    }

    private void addNumberSystemQuestions(List<Question> questions) {
        for (int i = 0; i < 8; i++) {
            int decimal = random.nextInt(61) + 4;
            questions.add(new FillInBlankQuestion(
                    "Convert the decimal number " + decimal + " to binary.",
                    5,
                    Integer.toBinaryString(decimal)
            ));
        }

        for (int i = 0; i < 8; i++) {
            int decimal = random.nextInt(121) + 8;
            questions.add(new FillInBlankQuestion(
                    "Convert the decimal number " + decimal + " to hexadecimal.",
                    5,
                    Integer.toHexString(decimal).toUpperCase(),
                    Integer.toHexString(decimal).toLowerCase()
            ));
        }

        for (int i = 0; i < 8; i++) {
            int decimal = random.nextInt(81) + 8;
            questions.add(new FillInBlankQuestion(
                    "Convert the decimal number " + decimal + " to octal.",
                    5,
                    Integer.toOctalString(decimal)
            ));
        }

        for (int i = 0; i < 8; i++) {
            int decimal = random.nextInt(121) + 10;
            String hex = Integer.toHexString(decimal).toUpperCase();
            questions.add(new FillInBlankQuestion(
                    "Convert the hexadecimal number " + hex + " to decimal.",
                    5,
                    String.valueOf(decimal)
            ));
        }

        for (int i = 0; i < 8; i++) {
            int decimal = random.nextInt(61) + 5;
            String binary = Integer.toBinaryString(decimal);
            questions.add(new FillInBlankQuestion(
                    "Convert the binary number " + binary + " to decimal.",
                    5,
                    String.valueOf(decimal)
            ));
        }

        for (int i = 0; i < 8; i++) {
            int decimal = random.nextInt(81) + 8;
            String octal = Integer.toOctalString(decimal);
            questions.add(new FillInBlankQuestion(
                    "Convert the octal number " + octal + " to decimal.",
                    5,
                    String.valueOf(decimal)
            ));
        }
    }

    private String pick(String[] values) {
        return values[random.nextInt(values.length)];
    }

    private String capitalize(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    private List<Question> deduplicateByPrompt(List<Question> questions) {
        List<Question> uniqueQuestions = new ArrayList<>();
        Set<String> seenPrompts = new LinkedHashSet<>();

        for (Question question : questions) {
            if (seenPrompts.add(question.getPrompt())) {
                uniqueQuestions.add(question);
            }
        }

        return uniqueQuestions;
    }
}
