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

    @Test
    void testCodeEmptyString() {
        Soundex.code c = new Soundex.code();
        Value value = c.call(context, Values.make(""));

        assertEquals(Values.NIL, value);
    }

    @Test
    void testEqualsTrue() {
        Soundex.equals e = new Soundex.equals();
        Value value = e.call(context, Values.make("Hamburg"), Values.make("Hamburg"));

        assertEquals(Values.TRUE, value);
    }

    @Test
    void testEqualsTrue2() {
        Soundex.equals e = new Soundex.equals();
        Value value = e.call(context, Values.make("Hamburg"), Values.make("Himburrggg"));

        assertEquals(Values.TRUE, value);
    }

    @Test
    void testEqualsFalse() {
        Soundex.equals e = new Soundex.equals();
        Value value = e.call(context, Values.make("Banane"), Values.make("Zalando"));

        assertEquals(Values.FALSE, value);
    }

    @Test
    void testEqualsFalseWithNil() {
        Soundex.equals e = new Soundex.equals();
        Value value = e.call(context, Values.NIL, Values.make("Torte"));

        assertEquals(Values.FALSE, value);
    }

    @Test
    void testEqualsFalseWithBothNil() {
        Soundex.equals e = new Soundex.equals();
        Value value = e.call(context, Values.NIL, Values.NIL);

        assertEquals(Values.FALSE, value);
    }
}
