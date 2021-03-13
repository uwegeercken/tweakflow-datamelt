package com.datamelt.tweakflow.coords;

import com.datamelt.tweakflow.std.Stringer;
import com.twineworks.tweakflow.lang.values.UserCallContext;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.Values;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringerTest
{
    private final UserCallContext context = null;

    @Test
    void testReverse() {
        Stringer.reverse r = new Stringer.reverse();
        Value value = r.call(context, Values.make("Donnerstag"));

        assertEquals("gatsrennoD", value.string());
    }

    @Test
    void testReverseEmpty() {
        Stringer.reverse r = new Stringer.reverse();
        Value value = r.call(context, Values.make(""));

        assertEquals(Values.make(""), value);
    }

    @Test
    void testReverseNil() {
        Stringer.reverse r = new Stringer.reverse();
        Value value = r.call(context, Values.NIL);

        assertEquals(Values.NIL, value);
    }
}
