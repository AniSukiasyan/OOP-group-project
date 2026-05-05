package quizgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QuestionLoader {

    public Question[] loadFromTextFile(String fileName) {
        Question[] questions = new Question[200];
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null && count < questions.length) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                String type = parts[0];

                try {
                    switch (type) {
                        case "MCQ":
                            questions[count] = new MultipleChoiceQuestion(
                                    parts[1],
                                    parts[2].split(","),
                                    parts[3].charAt(0),
                                    Integer.parseInt(parts[4])
                            );
                            count++;
                            break;
                        case "TF":
                            questions[count] = new TrueFalseQuestion(
                                    parts[1],
                                    Boolean.parseBoolean(parts[2]),
                                    Integer.parseInt(parts[3])
                            );
                            count++;
                            break;
                        case "FIB":
                        case "COMP": // Both use the same constructor structure
                            questions[count] = new FillInBlankQuestion(
                                    parts[1],
                                    Integer.parseInt(parts[2]),
                                    parts[3]
                            );
                            count++;
                            break;
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("File Error: " + e.getMessage());
        }

        return questions;
    }
}
