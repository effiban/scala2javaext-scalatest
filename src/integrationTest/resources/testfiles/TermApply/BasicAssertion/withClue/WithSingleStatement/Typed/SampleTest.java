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

    public SampleTest() {
    }

    public int calculateShouldReturnResult() {
        return Try.<int>ofSupplier(() -> calculate())
                .recover(e -> switch (e) {
                    case AssertionFailedError ex -> fail("error in calculation", ex);
                    default -> throw e;
                }
        )
        .get();
    }
}