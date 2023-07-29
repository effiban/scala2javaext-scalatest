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
        assertThat(((Supplier<Object>)() ->  {
                    final int x = 1;
                    final int y = 2;
                    x + y;
                }
                ).get(), is(3));
    }
}