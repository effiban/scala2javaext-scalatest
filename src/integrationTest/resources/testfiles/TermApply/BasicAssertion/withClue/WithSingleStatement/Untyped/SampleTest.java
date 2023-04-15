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

    public void dummy() {
        Try.ofSupplier(() -> doSomething())
                .recover(e -> switch (e) {
                    case AssertionFailedError ex -> fail("special clue", ex);
                    default -> throw e;
                }
        );
    }
}