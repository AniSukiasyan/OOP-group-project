public class Main {
    public static void main(String[] args) {
        QuizGame game = new QuizGame();

        game.addQuestion(new Question(
                "What is 2 + 2?",
                new String[]{"3", "4", "5"},
                1
        ));

        game.addQuestion(new Question(
                "Capital of France?",
                new String[]{"Berlin", "Paris", "Rome"},
                1
        ));

        game.start();
    }
}