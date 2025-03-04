package demo.hankcs.hanlp.algorithm;

import junit.framework.TestCase;

public class LongestCommonSubstringTest extends TestCase
{
    String a = "www.hankcs.com";
    String b = "hankcs";
    public void testCompute() throws Exception
    {
//        System.out.println(LongestCommonSubstring.compute(a.toCharArray(), b.toCharArray()));
        assertEquals(6, LongestCommonSubstring.compute(a.toCharArray(), b.toCharArray()));
    }

    public void testLongestCommonSubstring() throws Exception
    {
//        System.out.println(LongestCommonSubstring.compute(a, b));
        assertEquals(6, LongestCommonSubstring.compute(a, b));
    }
}