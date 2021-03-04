import nl.han.ica.oose.dea.testedfizzbuzz.FizzBuzzExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FizzBuzzTest {
    private FizzBuzzExecutor sut;

    @BeforeEach
    void setup() {
        sut = new FizzBuzzExecutor();
    }

//    @Test
//    void executeWithValidIntTest(){
//        var testValue = sut.execute(37);
//
//        Assertions.assertEquals("37", testValue);
//    }

    @Test
    void executeFizz() {
        var testValue = sut.execute(3);

        Assertions.assertEquals("Fizz", testValue);
    }

    @Test
    void executeBuzz() {
        var testValue = sut.execute(5);

        Assertions.assertEquals("Buzz", testValue);
    }

    @Test
    void executeFizzBuzz() {
        var testValue = sut.execute(15);

        Assertions.assertEquals("FizzBuzz", testValue);
    }

    // Code Coverage:
    // FizzBuzzExecutor: 100%
    // FizzBuzzRunner: 0%
}
