package com.datamelt.tweakflow.std;

import com.twineworks.tweakflow.lang.values.*;

public class Strings
{
        public static final class reverse implements UserFunction, Arity1UserFunction
        {
            @Override
            public Value call(UserCallContext context, Value value)
            {
                if(value.isNil())
                {
                    return Values.NIL;
                }
                String orgValue = value.string();
                StringBuilder returnValue = new StringBuilder();
                if(orgValue.length()>0)
                {
                    for (int i = value.string().length(); i > 0; i--)
                    {
                        String v = orgValue.substring(i - 1, i);
                        returnValue.append(v);
                    }
                    return Values.make(returnValue.toString());
                }
                else
                {
                    return value;
                }

            }

        }

    public static void main(String[] args)
    {
        UserCallContext context = null;

        Strings.reverse r = new Strings.reverse();
        Value v = r.call(context, Values.NIL);

        System.out.println(v.string());
    }
}
