package com.datamelt.tweakflow.coords;

import com.datamelt.tweakflow.strings.Soundex;
import com.twineworks.tweakflow.lang.values.UserCallContext;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.Values;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoundexTest
{
    private final UserCallContext context = null;

    @Test
    void testCode() {
        Soundex.code c = new Soundex.code();
        Value value = c.call(context, Values.make("Hamburg"));

        assertEquals("H516", value.string());
    }
}
