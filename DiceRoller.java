public class DiceRoller {
    public static void main(String[] args) {
        // Placeholder for user input handling
        String userInput = "2d6+3"; // Example input
        DiceExpression expression = new DiceExpression(userInput);
        int result = expression.evaluate();
        System.out.print("Input: " + userInput);
        System.out.print(" Output: ");
        expression.getRollResults().forEach(r -> System.out.print(r + ", "));
        System.out.println(" Result: " + result);
    }
}
