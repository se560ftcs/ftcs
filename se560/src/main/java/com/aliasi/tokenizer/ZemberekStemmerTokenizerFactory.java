/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.aliasi.tokenizer;

import java.io.ObjectInput;
import java.io.Serializable;

import edu.metu.se560.stemming.Stemming;

// Referenced classes of package com.aliasi.tokenizer:
//            ModifyTokenTokenizerFactory, PorterStemmer, TokenizerFactory, ModifiedTokenizerFactory

public class ZemberekStemmerTokenizerFactory extends ModifyTokenTokenizerFactory
    implements Serializable
{
    static class Serializer extends ModifiedTokenizerFactory.AbstractSerializer
    {

        public Object read(ObjectInput in, TokenizerFactory baseFactory)
        {
            return new ZemberekStemmerTokenizerFactory(baseFactory);
        }

        static final long serialVersionUID = -1426364356L;

        public Serializer()
        {
            this(null);
        }

        public Serializer(ZemberekStemmerTokenizerFactory factory)
        {
            super(factory);
        }
    }


    public ZemberekStemmerTokenizerFactory(TokenizerFactory factory)
    {
        super(factory);
    }

    public String modifyToken(String token)
    {
        return stem(token);
    }

    private static Stemming zemberek = new Stemming();
    public static String stem(String in)
    {
        String result = zemberek.parse(in);
        System.out.println(in + "->"+ result);
        return result;
    }

    Object writeReplace()
    {
        return new Serializer(this);
    }

    public String toString()
    {
        return (new StringBuilder()).append(getClass().toString()).append("\n  base factory=\n    ").append(baseTokenizerFactory().toString().replace("\n", "\n    ")).toString();
    }

    static final long serialVersionUID = 1658239134L;
}


/*
	DECOMPILATION REPORT

	Decompiled from: /Users/semimac/.m2/repository/com/aliasi/lingpipe/4.0.1/lingpipe-4.0.1.jar
	Total time: 17 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/