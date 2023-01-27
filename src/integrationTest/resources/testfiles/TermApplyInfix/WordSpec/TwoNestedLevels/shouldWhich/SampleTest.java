package dummy;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    public SampleTest() {
    }

    @Nested
    @DisplayName("Coffee machine")
    public class CoffeeMachine {

        public CoffeeMachine() {
        }

        @Nested
        @DisplayName("should provide a button")
        public class ShouldProvideAButton {

            public ShouldProvideAButton() {
            }

            @Test
            @DisplayName("which prepares an Espresso")
            public void whichPreparesAnEspresso() {
                verifyEspressoButton();
            }
        }
    }
}