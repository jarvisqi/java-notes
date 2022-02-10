package com.softmax.basic;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JiebaTest {


    public static void main(String[] args) {
        JiebaSegmenter segmenter = new JiebaSegmenter();

        WordDictionary wordDictionary = WordDictionary.getInstance();
        Path path = Paths.get("D:\\Source\\matrix\\java-tutorial\\basic\\src\\main\\resources\\dicts\\jieba.dict");

        System.out.println(path);
        wordDictionary.loadUserDict(path);

        String[] sentences = new String[]{"中国工商银行股份有限公司北京德外支行"};

        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);

        sentences = new String[]{"中国工商银行股份有限公司吉林市长春路支行"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);

        sentences = new String[]{"中国农业银行股份有限公司东乌珠穆沁旗乌拉盖管理区支行"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);

        sentences = new String[]{"中国农业银行股份有限公司柳河孤山子支行"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);

        sentences = new String[]{"中国银行哈尔滨市动力支行工程大学分理处"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);

        sentences = new String[]{"中国邮政储蓄银行股份有限公司平罗县支行"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);

        sentences = new String[]{"中国银行哈尔滨市动力支行工程大学分理处"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);

        sentences = new String[]{"浙江遂昌农村商业银行股份有限公司金竹支行湖山分理处"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);

        sentences = new String[]{"浙江松阳农村商业银行股份有限公司古市支行"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);


        sentences = new String[]{"上海农村商业银行股份有限公司南汇工业区支行"};
        System.out.println(segmenter.process(sentences[0], JiebaSegmenter.SegMode.SEARCH).get(0).word);
    }
}
