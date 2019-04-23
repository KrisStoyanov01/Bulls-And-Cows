import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    //private static Integer playerScore = 0;
    //private static Integer AIScore = 0;
    private static Boolean winForAI = false;
    private static Boolean winForPlayer = false;

    public static void main(String[] args) {
        boolean playAgain = true;
        while(playAgain) {
            playAGame();
            System.out.println("For playing again type \"yes\"");
            String answer = scanner.nextLine();
            if(!answer.toLowerCase().equals("yes")){
                playAgain = false;
            }
        }
    }

    private static void playAGame(){
        String myNum = generateRandomNumber();
        System.out.println("Think of a number with different digits...");
        winForAI = false;
        winForPlayer = false;
        Set<String> possibleNums = generateAllNumbers();
        while (true) {
            giveResponse(myNum);
            if(winForPlayer || winForAI){
                break;
            }
            guess(possibleNums);
        }
    }

    private static void guess(Set<String> possibleNums) {

        Iterator<String> iter = possibleNums.iterator();
        if(!iter.hasNext()){
            //TODO: Missing "fuck you" return for trying to lie
        }else {
            Random random = new Random();
            String AIguess = selectRandom(iter, random);

            System.out.println("My guess: " + AIguess);

            System.out.print("Cows: ");
            //TODO: Missing "fuck you" return for giving illegal answer
            int cowsCount = Integer.parseInt(scanner.nextLine());

            System.out.print("Bulls: ");
            //TODO: Missing "fuck you" return for giving illegal answer
            int bullsCount = Integer.parseInt(scanner.nextLine());
            removeFromPossibleNums(possibleNums, AIguess, new BullsCowsCounter(bullsCount, cowsCount));
            if (bullsCount == 4) {
                System.out.println("Game over!\nI win (as always)!");
                winForAI = true;
            }
        }
    }

    private static void giveResponse(String myNum){
        System.out.println("Make a guess about mine: ");
        String playerGuess = scanner.nextLine();
        //TODO: Missing "fuck you" return for giving illegal guess
        Integer bulls = giveBullsCount(playerGuess, myNum);
        Integer cows = giveCowsCount(playerGuess, myNum);
        cows -= bulls;
        if(bulls == 4){
            winForPlayer = true;
            System.out.println("You win! :(");
        }else {
            System.out.println("Cows: " + cows + "\nBulls: " + bulls);
        }
    }

    private static void again() throws Exception {
        System.out.println("Wanna play again?\nType yes for another one");
        String answer = scanner.nextLine();
        if(answer.toLowerCase().equals("yes")){
            playAGame();
        }else {
            throw new Exception();
        }
    }

    private static Set<String> generateAllNumbers() {
        Set<String> allNums = new LinkedHashSet<>();
        for (int i = 1000; i < 10_000; i++) {
            allNums.add(String.valueOf(i));
        }
        Iterator<String> iter = allNums.iterator();
        while (iter.hasNext()) {
            String checkedNumber = iter.next();
            Set<Character> digits = new LinkedHashSet<>();
            for (char c : checkedNumber.toCharArray()) {
                if (digits.contains(c)) {
                    iter.remove();
                    break;
                }
                digits.add(c);
            }
        }
        return allNums;
    }

    private static void removeFromPossibleNums(Set<String> possibleNums, String guess, BullsCowsCounter guessBullsCowsCounter) {
        Iterator<String> iter = possibleNums.iterator();
        while (iter.hasNext()) {
            String str = iter.next();
            BullsCowsCounter bullsCowsCounter = new BullsCowsCounter(0, 0);
            for (int i = 0; i < str.length(); i++) {
                if (guess.charAt(i) == str.charAt(i)) {
                    bullsCowsCounter.bullCount++;
                } else if (guess.contains(String.valueOf(str.charAt(i)))) {
                    bullsCowsCounter.cowCount++;
                }
            }
            if (!bullsCowsCounter.equals(guessBullsCowsCounter)) {
                iter.remove();
            }
        }
    }

    private static String generateRandomNumber(){
        Random random = new Random();
        List<String> allNums = new LinkedList<>(generateAllNumbers());
        return allNums.get(random.nextInt(allNums.size()));
    }

    private static Integer giveCowsCount(String guess,String myNum){
        Integer cows = 0;
        for (char c1 : guess.toCharArray()) {
            for (char c2 : myNum.toCharArray()) {
                if(c1 == c2){
                    cows++;
                }
            }
        }
        return cows;
    }

    private static Integer giveBullsCount(String guess,String myNum){
        Integer bulls = 0;
        List<Character> dividedGuess = returnStringAsCharList(guess);
        List<Character> dividedMyNum = returnStringAsCharList(myNum);

        for (Character character : dividedGuess) {
            if(character.equals(dividedMyNum.get(dividedGuess.indexOf(character)))){
                bulls++;
            }
        }
        return bulls;
    }

    private static List<Character> returnStringAsCharList(String string){
        List<Character> defragmented = new LinkedList<>();
        for (Character mN : string.toCharArray()) {
            defragmented.add(mN);
        }
        return defragmented;
    }

    public static <T> T selectRandom(final Iterator<T> iter, final Random random) {

        T selected = iter.next();
        int count = 1;
        while (iter.hasNext()) {
            final T current = iter.next();
            ++count;
            if (random.nextInt(count) == 0) {
                selected = current;
            }
        }
        return selected;
    }
}
