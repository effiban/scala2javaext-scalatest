package dummy;

import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Function.*;
import org.hamcrest.Matchers.*;
import org.hamcrest.MatcherAssert.*;

public class SampleTest {

    @Nested
    @DisplayName("air conditioners")
    public class AirConditioners {

        @Test
        @DisplayName("should lower temperature when cooling selected")
        public void shouldLowerTemperatureWhenCoolingSelected() {
            checkCooling();
        }

        @Test
        @DisplayName("should raise temperature when heating selected")
        public void shouldRaiseTemperatureWhenHeatingSelected() {
            checkHeating();
        }
    }
}