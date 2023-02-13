package dummy;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import org.hamcrest.Matchers.*;
import org.hamcrest.MatcherAssert.*;

public class SampleTest {

    public SampleTest() {
    }

    @Nested
    @DisplayName("air conditioner")
    public class AirConditioner {

        public AirConditioner() {
        }

        @Test
        @DisplayName("cooling")
        public void cooling() {
            checkCooling();
        }

        @Test
        @DisplayName("heating")
        public void heating() {
            checkHeating();
        }
    }
}