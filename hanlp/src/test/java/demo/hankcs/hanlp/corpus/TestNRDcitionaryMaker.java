package demo.hankcs.hanlp.corpus;

import java.util.LinkedList;
import java.util.List;

import demo.hankcs.hanlp.corpus.dictionary.EasyDictionary;
import demo.hankcs.hanlp.corpus.dictionary.NRDictionaryMaker;
import demo.hankcs.hanlp.corpus.document.CorpusLoader;
import demo.hankcs.hanlp.corpus.document.Document;
import demo.hankcs.hanlp.corpus.document.sentence.word.IWord;
import demo.hankcs.hanlp.corpus.document.sentence.word.Word;

public class TestNRDcitionaryMaker
{

    public static void main(String[] args)
    {
        EasyDictionary dictionary = EasyDictionary.create("data/dictionary/2014_dictionary.txt");
        final NRDictionaryMaker nrDictionaryMaker = new NRDictionaryMaker(dictionary);
        CorpusLoader.walk("D:\\JavaProjects\\CorpusToolBox\\data\\2014\\", new CorpusLoader.Handler()
        {
            @Override
            public void handle(Document document)
            {
                List<List<Word>> simpleSentenceList = document.getSimpleSentenceList();
                List<List<IWord>> compatibleList = new LinkedList<List<IWord>>();
                for (List<Word> wordList : simpleSentenceList)
                {
                    compatibleList.add(new LinkedList<IWord>(wordList));
                }
                nrDictionaryMaker.compute(compatibleList);
            }
        });
        nrDictionaryMaker.saveTxtTo("D:\\JavaProjects\\HanLP\\data\\test\\person\\nr1");
    }

}
