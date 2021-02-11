package org.patrickroelofs.han;

import java.util.ArrayList;
import java.util.List;

public class PrimeTestingApp {

    private static final int HIGHEST_NUMBER_TO_TEST = 2000;

    public static void main(String[] args) {
        PrimeTestingApp app = new PrimeTestingApp();
        app.startTesting();
    }

    private void startTesting() {
        NumberUnderTest numberUnderTest = new NumberUnderTest();

        List<PrimeTester> testers = new ArrayList<>();

        testers.add(new PrimeTester(numberUnderTest, HIGHEST_NUMBER_TO_TEST));
        testers.add(new PrimeTester(numberUnderTest, HIGHEST_NUMBER_TO_TEST));
        testers.add(new PrimeTester(numberUnderTest, HIGHEST_NUMBER_TO_TEST));
        testers.add(new PrimeTester(numberUnderTest, HIGHEST_NUMBER_TO_TEST));

        for(PrimeTester tester : testers) {
            new Thread(tester).start();
        }
    }
}
