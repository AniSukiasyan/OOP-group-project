package OOPGroupWork.quizgame.factory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import OOPGroupWork.quizgame.model.DifficultyLevel;

import OOPGroupWork.quizgame.model.Quiz;
import OOPGroupWork.quizgame.questions.CodeCompletionQuestion;
import OOPGroupWork.quizgame.questions.FillInBlankQuestion;
import OOPGroupWork.quizgame.questions.MultipleChoiceQuestion;
import OOPGroupWork.quizgame.questions.Question;
import OOPGroupWork.quizgame.questions.TrueFalseQuestion;

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
            quiz.addQuestion(score(pool.get(i), difficulty));
        }

        return quiz;
    }

    public DifficultyLevel[] getAvailableDifficulties() {
        return DifficultyLevel.values();
    }

    private List<Question> createQuestionPool(DifficultyLevel difficulty) {
        if (difficulty == DifficultyLevel.EASY) {
            return createEasyQuestions();
        } else if (difficulty == DifficultyLevel.MEDIUM) {
            return createMediumQuestions();
        } else if (difficulty == DifficultyLevel.HARD) {
            return createHardQuestions();
        } else {
            return createExtremelyHardQuestions();
        }
    }

    private List<Question> createEasyQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new MultipleChoiceQuestion(
                "Which OOP principle allows one class to acquire the properties and behaviors of another class?",
                new String[]{"Encapsulation", "Inheritance", "Abstraction", "Composition"}, 'B'));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: Hiding internal data and allowing access only through methods is called ________.",
                "encapsulation"));

        questions.add(new TrueFalseQuestion(
                "Polymorphism allows the same method name to behave differently based on the object.",
                true));

        questions.add(new MultipleChoiceQuestion(
                "Which keyword is used in Java to create a subclass from a superclass?",
                new String[]{"implements", "this", "extends", "super"}, 'C'));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: A class that cannot be instantiated directly and is meant to be inherited is called an ________ class.",
                "abstract"));

        questions.add(new TrueFalseQuestion(
                "A private field can be accessed directly from any other class.",
                false));

        questions.add(new MultipleChoiceQuestion(
                "Which access modifier makes a field available only inside the same class?",
                new String[]{"public", "private", "protected", "static"}, 'B'));

        questions.add(new MultipleChoiceQuestion(
                "Which keyword refers to the current object in Java?",
                new String[]{"super", "class", "this", "self"}, 'C'));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: The ability to take many forms is called ________.",
                "polymorphism"));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: Creating objects from a class is called ________.",
                "instantiation"));

        questions.add(new TrueFalseQuestion(
                "A constructor can have the same name as its class.",
                true));

        questions.add(new MultipleChoiceQuestion(
                "Which method is automatically called when an object is created?",
                new String[]{"main", "constructor", "finalize", "equals"}, 'B'));

        return questions;
    }

    private List<Question> createMediumQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new CodeCompletionQuestion(
                "Complete the missing Java keyword:\n\npublic class Student _____ Person {\n}",
                "extends"));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: In inheritance, the class being inherited from is called the ________ class.",
                "superclass", "parent class", "base class"));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: A class that inherits from another class is called a ________ class.",
                "subclass", "child class", "derived class"));

        questions.add(new TrueFalseQuestion(
                "Java supports multiple inheritance of classes directly.",
                false));

        questions.add(new MultipleChoiceQuestion(
                "Which keyword lets a subclass call a parent constructor?",
                new String[]{"this", "super", "base", "parent"}, 'B'));

        questions.add(new CodeCompletionQuestion(
                "Complete the setter method name:\n\nprivate String name;\n\npublic void ________(String name) {\n    this.name = name;\n}",
                "setName"));

        questions.add(new MultipleChoiceQuestion(
                "Which pair is most commonly used for encapsulation?",
                new String[]{"public fields and no methods", "private fields with getters and setters",
                        "abstract fields", "interface fields"}, 'B'));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: A method that returns a private field value is often called a ________.",
                "getter", "accessor"));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: A method that changes a field value is often called a ________.",
                "setter", "mutator"));

        questions.add(new MultipleChoiceQuestion(
                "Which of the following is an example of runtime polymorphism?",
                new String[]{"Method overloading", "Method overriding", "Using variables", "Using loops"}, 'B'));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: Defining multiple methods with the same name but different parameters is method ________.",
                "overloading"));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: Changing inherited behavior in a subclass is method ________.",
                "overriding"));

        questions.add(new MultipleChoiceQuestion(
                "Compile-time polymorphism is usually associated with:",
                new String[]{"Overloading", "Overriding", "Inheritance only", "Interfaces only"}, 'A'));

        questions.add(new TrueFalseQuestion(
                "An interface is one way to achieve abstraction in Java.",
                true));

        addGeneratedCodeQuestions(questions);

        return questions;
    }

    private List<Question> createHardQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new MultipleChoiceQuestion(
                "Which keyword declares an abstract method in Java?",
                new String[]{"virtual", "abstract", "override", "final"}, 'B'));

        questions.add(new CodeCompletionQuestion(
                "Complete the code:\n\npublic ________ class Shape {\n    public abstract double area();\n}",
                "abstract"));

        questions.add(new MultipleChoiceQuestion(
                "A List<Animal> contains Dog and Cat objects. Each subclass overrides speak(). Which call uses dynamic dispatch?",
                new String[]{"animal.speak()", "Animal.speak()", "new Animal()", "super.speak() from main"}, 'A'));

        questions.add(new FillInBlankQuestion(
                "What is the output?\n\nclass A { String name() { return \"A\"; } }\nclass B extends A { String name() { return \"B\"; } }\nA item = new B();\nSystem.out.println(item.name());",
                "B"));

        questions.add(new CodeCompletionQuestion(
                "Complete the missing declaration:\n\npublic class Student ________ Comparable<Student> {\n    public int compareTo(Student other) { return 0; }\n}",
                "implements"));

        questions.add(new FillInBlankQuestion(
                "What is printed?\n\nint total = 0;\nfor (int i = 1; i <= 4; i++) {\n    total += i * i;\n}\nSystem.out.println(total);",
                "30"));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: Converting an object of a subclass to a superclass type is called ________.",
                "upcasting"));

        questions.add(new TrueFalseQuestion(
                "You can use this() and super() in the same constructor.",
                false));

        questions.add(new MultipleChoiceQuestion(
                "What is the result of: String s = 5 + 5 + \"5\";",
                new String[]{"15", "55", "105", "Error"}, 'C'));

        addNumberSystemQuestions(questions);

        return questions;
    }

    private List<Question> createExtremelyHardQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new FillInBlankQuestion(
                "What is printed?\n\nclass A {\n    void show() { System.out.print(\"A\"); }\n}\nclass B extends A {\n    void show() { System.out.print(\"B\"); }\n}\nA obj = new B();\nobj.show();",
                "B"));

        questions.add(new MultipleChoiceQuestion(
                "Which statement about dynamic dispatch in Java is correct?",
                new String[]{
                        "Method calls are always based on reference type",
                        "Overridden method calls are based on the actual object type at runtime",
                        "Static methods use dynamic dispatch",
                        "Constructors are dynamically dispatched"
                }, 'B'));

        questions.add(new TrueFalseQuestion(
                "Private methods are not overridden in Java.",
                true));

        questions.add(new FillInBlankQuestion(
                "What is printed?\n\nString a = \"Java\";\nString b = new String(\"Java\");\nSystem.out.println(a == b);",
                "false"));

        questions.add(new MultipleChoiceQuestion(
                "Which method should usually be overridden when equals() is overridden?",
                new String[]{"toString()", "clone()", "hashCode()", "compareTo()"}, 'C'));

        questions.add(new FillInBlankQuestion(
                "What is printed?\n\nint x = 3;\nSystem.out.println(++x + x++);",
                "8"));

        questions.add(new CodeCompletionQuestion(
                "Complete the missing keyword:\n\ntry {\n    int x = 10 / 0;\n} ________ (ArithmeticException e) {\n    System.out.println(\"Error\");\n}",
                "catch"));

        questions.add(new MultipleChoiceQuestion(
                "Which collection prevents duplicate elements?",
                new String[]{"List", "Set", "Queue", "ArrayList"}, 'B'));

        questions.add(new FillInBlankQuestion(
                "What is printed?\n\nint[] nums = {2, 4, 6};\nSystem.out.println(nums.length);",
                "3"));

        questions.add(new TrueFalseQuestion(
                "A static method can be overridden using runtime polymorphism.",
                false));

        questions.add(new CodeCompletionQuestion(
                "Complete the missing keyword:\n\npublic class Dog extends Animal {\n    @Override\n    public void speak() {\n        ________.speak();\n        System.out.println(\"Dog\");\n    }\n}",
                "super"));

        questions.add(new FillInBlankQuestion(
                "Fill in the blank: The process of converting a superclass reference back to a subclass type is called ________.",
                "downcasting"));

        questions.add(new MultipleChoiceQuestion(
                "Which exception is thrown when accessing an invalid array index?",
                new String[]{
                        "NullPointerException",
                        "ArrayIndexOutOfBoundsException",
                        "ClassCastException",
                        "ArithmeticException"
                }, 'B'));

        questions.add(new FillInBlankQuestion(
                "What is printed?\n\nint result = 2 + 3 * 4;\nSystem.out.println(result);",
                "14"));

        questions.add(new TrueFalseQuestion(
                "An abstract class can have constructors.",
                true));

        return questions;
    }

    private void addGeneratedCodeQuestions(List<Question> questions) {
        for (int i = 0; i < 6; i++) {
            int a = random.nextInt(9) + 1;
            int b = random.nextInt(9) + 1;

            questions.add(new FillInBlankQuestion(
                    "What is the output of this Java code?\n\nint a = " + a + ";\nint b = " + b
                            + ";\nSystem.out.println(a + b);",
                    String.valueOf(a + b)));
        }

        for (int i = 0; i < 5; i++) {
            int value = random.nextInt(40) + 10;

            questions.add(new CodeCompletionQuestion(
                    "Complete the missing return statement:\n\npublic int getValue() {\n    ________ " + value + ";\n}",
                    "return"));
        }
    }

    private void addNumberSystemQuestions(List<Question> questions) {
        for (int i = 0; i < 6; i++) {
            int decimal = random.nextInt(60) + 4;

            questions.add(new FillInBlankQuestion(
                    "Convert the decimal number " + decimal + " to binary.",
                    Integer.toBinaryString(decimal)));
        }

        for (int i = 0; i < 6; i++) {
            int decimal = random.nextInt(120) + 8;

            questions.add(new FillInBlankQuestion(
                    "Convert the decimal number " + decimal + " to hexadecimal.",
                    Integer.toHexString(decimal).toUpperCase(),
                    Integer.toHexString(decimal).toLowerCase()));
        }
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