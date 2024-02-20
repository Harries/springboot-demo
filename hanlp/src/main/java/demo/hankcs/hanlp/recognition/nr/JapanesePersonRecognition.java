/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/11/12 21:39</create-date>
 *
 * <copyright file="JapanesePersonRecogniton.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package demo.hankcs.hanlp.recognition.nr;

import demo.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import demo.hankcs.hanlp.corpus.tag.Nature;
import demo.hankcs.hanlp.dictionary.CoreDictionary;
import demo.hankcs.hanlp.dictionary.nr.JapanesePersonDictionary;
import demo.hankcs.hanlp.seg.common.Vertex;
import demo.hankcs.hanlp.seg.common.WordNet;
import demo.hankcs.hanlp.utility.Predefine;

import java.util.List;

import static demo.hankcs.hanlp.dictionary.nr.NRConstant.WORD_ID;

/**
 * 日本人名识别
 *
 * @author hankcs
 */
public class JapanesePersonRecognition
{
    /**
     * 执行识别
     *
     * @param segResult      粗分结果
     * @param wordNetOptimum 粗分结果对应的词图
     * @param wordNetAll     全词图
     */
    public static void recognition(List<Vertex> segResult, WordNet wordNetOptimum, WordNet wordNetAll)
    {
        StringBuilder sbName = new StringBuilder();
        int appendTimes = 0;
        char[] charArray = wordNetAll.charArray;
        DoubleArrayTrie<Character>.LongestSearcher searcher = JapanesePersonDictionary.getSearcher(charArray);
        int activeLine = 1;
        int preOffset = 0;
        while (searcher.next())
        {
            Character label = searcher.value;
            int offset = searcher.begin;
            String key = new String(charArray, offset, searcher.length);
            if (preOffset != offset)
            {
                if (appendTimes > 1 && sbName.length() > 2) // 日本人名最短为3字
                {
                    insertName(sbName.toString(), activeLine, wordNetOptimum, wordNetAll);
                }
                sbName.setLength(0);
                appendTimes = 0;
            }
            if (appendTimes == 0)
            {
                if (label == JapanesePersonDictionary.X)
                {
                    sbName.append(key);
                    ++appendTimes;
                    activeLine = offset + 1;
                }
            }
            else
            {
                if (label == JapanesePersonDictionary.M)
                {
                    sbName.append(key);
                    ++appendTimes;
                }
                else
                {
                    if (appendTimes > 1 && sbName.length() > 2)
                    {
                        insertName(sbName.toString(), activeLine, wordNetOptimum, wordNetAll);
                    }
                    sbName.setLength(0);
                    appendTimes = 0;
                }
            }
            preOffset = offset + key.length();
        }
        if (sbName.length() > 0)
        {
            if (appendTimes > 1)
            {
                insertName(sbName.toString(), activeLine, wordNetOptimum, wordNetAll);
            }
        }
    }

    /**
     * 是否是bad case
     * @param name
     * @return
     */
    public static boolean isBadCase(String name)
    {
        Character label = JapanesePersonDictionary.get(name);
        if (label == null) return false;
        return label.equals(JapanesePersonDictionary.A);
    }

    /**
     * 插入日本人名
     * @param name
     * @param activeLine
     * @param wordNetOptimum
     * @param wordNetAll
     */
    private static void insertName(String name, int activeLine, WordNet wordNetOptimum, WordNet wordNetAll)
    {
        if (isBadCase(name)) return;
        wordNetOptimum.insert(activeLine, new Vertex(Predefine.TAG_PEOPLE, name, new CoreDictionary.Attribute(Nature.nrj), WORD_ID), wordNetAll);
    }
}
