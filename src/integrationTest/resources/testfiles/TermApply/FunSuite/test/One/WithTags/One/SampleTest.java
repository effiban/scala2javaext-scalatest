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

    @Test
    @DisplayName("check")
    @Tag("myTag")
    public void check() {
        performTheCheck();
    }
}