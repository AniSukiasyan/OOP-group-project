package quizgame.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import quizgame.model.DifficultyLevel;
import quizgame.model.Quiz;
import quizgame.questions.CodeCompletionQuestion;
import quizgame.questions.FillInBlankQuestion;
import quizgame.questions.MultipleChoiceQuestion;
import quizgame.questions.Question;
import quizgame.questions.TrueFalseQuestion;

public class QuizFactory {
    private final Random random = new Random();

    public Quiz createOopQuiz() {
        return createOopQuiz(DifficultyLevel.EASY);
    }

    public Quiz createOopQuiz(DifficultyLevel difficulty) {
        Quiz quiz = new Quiz("Object-Oriented Programming Quiz", difficulty);
        List<Question> pool = createQuestionPool(difficulty);
        Collections.shuffle(pool, random);

        int limit = Math.min(difficulty.getQuestionCount(), pool.size());
        for (int i = 0; i < limit; i++) {
            quiz.addQuestion(pool.get(i));
        }

        return quiz;
    }

    public DifficultyLevel[] getAvailableDifficulties() {
        return DifficultyLevel.values();
    }

    private List<Question> createQuestionPool(DifficultyLevel difficulty) {
        List<Question> questions = new ArrayList<>();

        questions.add(score(new MultipleChoiceQuestion(
                "Which OOP principle allows one class to acquire the properties and behaviors of another class?",
                new String[]{"Encapsulation", "Inheritance", "Abstraction", "Composition"}, 'B'), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: Hiding internal data and allowing access only through methods is called ________.",
                "encapsulation"), difficulty));
        questions.add(score(new TrueFalseQuestion(
                "Polymorphism allows the same method name to behave differently based on the object.", true), difficulty));
        questions.add(score(new MultipleChoiceQuestion(
                "Which keyword is used in Java to create a subclass from a superclass?",
                new String[]{"implements", "this", "extends", "super"}, 'C'), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: A class that cannot be instantiated directly and is meant to be inherited is called an ________ class.",
                "abstract"), difficulty));
        questions.add(score(new TrueFalseQuestion(
                "A private field can be accessed directly from any other class.", false), difficulty));
        questions.add(score(new MultipleChoiceQuestion(
                "Which access modifier makes a field available only inside the same class?",
                new String[]{"public", "private", "protected", "static"}, 'B'), difficulty));
        questions.add(score(new MultipleChoiceQuestion(
                "Which keyword refers to the current object in Java?",
                new String[]{"super", "class", "this", "self"}, 'C'), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: The ability to take many forms is called ________.",
                "polymorphism"), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: Creating objects from a class is called ________.",
                "instantiation"), difficulty));

        questions.add(score(new CodeCompletionQuestion(
                "Complete the missing Java keyword:\n\npublic class Student _____ Person {\n}",
                "extends"), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: In inheritance, the class being inherited from is called the ________ class.",
                "superclass", "parent class", "base class"), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: A class that inherits from another class is called a ________ class.",
                "subclass", "child class", "derived class"), difficulty));
        questions.add(score(new TrueFalseQuestion(
                "Java supports multiple inheritance of classes directly.", false), difficulty));
        questions.add(score(new MultipleChoiceQuestion(
                "Which keyword lets a subclass call a parent constructor?",
                new String[]{"this", "super", "base", "parent"}, 'B'), difficulty));

        questions.add(score(new CodeCompletionQuestion(
                "Complete the setter method name:\n\nprivate String name;\n\npublic void ________(String name) {\n    this.name = name;\n}",
                "setName"), difficulty));
        questions.add(score(new MultipleChoiceQuestion(
                "Which pair is most commonly used for encapsulation?",
                new String[]{"public fields and no methods", "private fields with getters and setters",
                        "abstract fields", "interface fields"}, 'B'), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: A method that returns a private field value is often called a ________.",
                "getter", "accessor"), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: A method that changes a field value is often called a ________.",
                "setter", "mutator"), difficulty));

        questions.add(score(new MultipleChoiceQuestion(
                "Which of the following is an example of runtime polymorphism?",
                new String[]{"Method overloading", "Method overriding", "Using variables", "Using loops"}, 'B'), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: Defining multiple methods with the same name but different parameters is method ________.",
                "overloading"), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "Fill in the blank: Changing inherited behavior in a subclass is method ________.",
                "overriding"), difficulty));
        questions.add(score(new MultipleChoiceQuestion(
                "Compile-time polymorphism is usually associated with:",
                new String[]{"Overloading", "Overriding", "Inheritance only", "Interfaces only"}, 'A'), difficulty));

        questions.add(score(new MultipleChoiceQuestion(
                "Which keyword declares an abstract method in Java?",
                new String[]{"virtual", "abstract", "override", "final"}, 'B'), difficulty));
        questions.add(score(new TrueFalseQuestion(
                "An interface is one way to achieve abstraction in Java.", true), difficulty));
        questions.add(score(new CodeCompletionQuestion(
                "Complete the code:\n\npublic ________ class Shape {\n    public abstract double area();\n}",
                "abstract"), difficulty));

        addGeneratedCodeQuestions(questions, difficulty);
        addNumberSystemQuestions(questions, difficulty);
        addAdvancedQuestions(questions, difficulty);

        return questions;
    }

    private void addGeneratedCodeQuestions(List<Question> questions, DifficultyLevel difficulty) {
        for (int i = 0; i < 6; i++) {
            int a = random.nextInt(9) + 1;
            int b = random.nextInt(9) + 1;
            questions.add(score(new FillInBlankQuestion(
                    "What is the output of this Java code?\n\nint a = " + a + ";\nint b = " + b
                            + ";\nSystem.out.println(a + b);",
                    String.valueOf(a + b)), difficulty));
        }

        for (int i = 0; i < 5; i++) {
            int value = random.nextInt(40) + 10;
            questions.add(score(new CodeCompletionQuestion(
                    "Complete the missing return statement:\n\npublic int getValue() {\n    ________ " + value + ";\n}",
                    "return"), difficulty));
        }
    }

    private void addNumberSystemQuestions(List<Question> questions, DifficultyLevel difficulty) {
        for (int i = 0; i < 6; i++) {
            int decimal = random.nextInt(60) + 4;
            questions.add(score(new FillInBlankQuestion(
                    "Convert the decimal number " + decimal + " to binary.",
                    Integer.toBinaryString(decimal)), difficulty));
        }

        for (int i = 0; i < 6; i++) {
            int decimal = random.nextInt(120) + 8;
            questions.add(score(new FillInBlankQuestion(
                    "Convert the decimal number " + decimal + " to hexadecimal.",
                    Integer.toHexString(decimal).toUpperCase(),
                    Integer.toHexString(decimal).toLowerCase()), difficulty));
        }
    }

    private void addAdvancedQuestions(List<Question> questions, DifficultyLevel difficulty) {
        questions.add(score(new MultipleChoiceQuestion(
                "A List<Animal> contains Dog and Cat objects. Each subclass overrides speak(). Which call uses dynamic dispatch?",
                new String[]{"animal.speak()", "Animal.speak()", "new Animal()", "super.speak() from main"}, 'A'), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "What is the output?\n\nclass A { String name() { return \"A\"; } }\nclass B extends A { String name() { return \"B\"; } }\nA item = new B();\nSystem.out.println(item.name());",
                "B"), difficulty));
        questions.add(score(new CodeCompletionQuestion(
                "Complete the missing declaration:\n\npublic class Student ________ Comparable<Student> {\n    public int compareTo(Student other) { return 0; }\n}",
                "implements"), difficulty));
        questions.add(score(new FillInBlankQuestion(
                "What is printed?\n\nint total = 0;\nfor (int i = 1; i <= 4; i++) {\n    total += i * i;\n}\nSystem.out.println(total);",
                "30"), difficulty));
    }

    private Question score(Question question, DifficultyLevel difficulty) {
        int points = difficulty.getBasePoints();

        if (question instanceof CodeCompletionQuestion) {
            points += 4;
        } else if (question instanceof FillInBlankQuestion) {
            points += 2;
        } else if (question instanceof TrueFalseQuestion) {
            points -= 1;
        }

        if (question.getPrompt().contains("\n\n")) {
            points += 1;
        }

        return question.copyWithScoring(difficulty, Math.max(1, points));
    }
}
