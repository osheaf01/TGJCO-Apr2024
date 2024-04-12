public class DiceRoll {

    // Static method to roll a die with a specified number of sides
    public static int roll(int sides) {
        // Implement dice rolling logic
        return (int) (Math.random() * sides) + 1;
    }

}