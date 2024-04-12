import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceExpression {
    private String expression;
    private int diceCount = 1;
    private int diceSides;
    private String optionalOperation;
    private int optionalOperationOperand;

    public ArrayList<Integer> getRollResults() {
        return rollResults;
    }

    private ArrayList<Integer> rollResults = new ArrayList<>();

    public DiceExpression(String expression) {
        try {
            this.expression = expression.trim(); // Trim the input string
            String regex = "(\\d*)d(\\d+)\\s*([+\\-*])\\s*(\\d+)?"; // Regular expression

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(expression);

            if (matcher.matches()) {
                diceCount = getCount(matcher, diceCount);
                diceSides = Integer.parseInt(matcher.group(2));

                boolean isValidDiceSides = DiceType.isValidNumberOfSides(diceSides);
                if (!isValidDiceSides) throw new DiceException("Invalid dice type");
                boolean validOperation = validateOperationAndOperand(matcher.group(3), matcher.group(4));
                if (!validOperation) throw new DiceException("Invalid operation/operand");

                optionalOperation = matcher.group(3);
                optionalOperationOperand = Integer.parseInt(matcher.group(4));

            } else {
                System.out.println("Invalid input format");
            }
        } catch (NumberFormatException | DiceException e) {
            throw new DiceException(e.getMessage());
        }
    }

    private boolean validateOperationAndOperand(String operation, String operand){
        if (operation == null || operand == null){
            return false;
        }
        return operation.isEmpty() ? operand.isEmpty() : !operand.isEmpty();
    }

    private static int getCount(Matcher matcher, int diceCount) {
        Optional<String> numberOfDice = Optional.of(matcher.group(1));
        if (!numberOfDice.get().isEmpty()){
            diceCount = Integer.parseInt(numberOfDice.get());
        }
        return diceCount;
    }




    public int evaluate() {
        int result = rollDice(diceCount, diceSides);

        switch (optionalOperation) {
            case "-" : result -= optionalOperationOperand; break;
            case "*" : result *= optionalOperationOperand; break;
            default: result += optionalOperationOperand;
        }
        return result;
    }

    private int rollDice(int diceCount, int diceSides){
        int result = 0;
        for (int i = diceCount; i > 0; i--) {
            int rollResult = DiceRoll.roll(diceSides);
            rollResults.add(rollResult);
            result += rollResult;
        }
        return result;
    }
}