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

    @Test
    @DisplayName("check1")
    public void check1() {
        performCheck1();
    }

    @Test
    @DisplayName("check2")
    public void check2() {
        performCheck2();
    }
}