public class FizzBuzz {
    public static void main(String[] args) {
        fizzBuzz(100);
    }

    private static void fizzBuzz(int num) {
        for(int i = 1; i < num; i++) {
            if (i % 15 == 0)
                System.out.print("FizzBuzz ");
            else if(i % 5 == 0)
                System.out.print("Buzz ");
            else if(i % 3 == 0)
                System.out.print("Fizz ");
            else
                System.out.print(i + " ");
        }
    }
}
