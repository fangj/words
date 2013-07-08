package com.fangjian.words.lib;

/**
 * Created with IntelliJ IDEA.
 * User: fangjian
 * Date: 13-7-6
 * The item of idx file:word,offset,length
 */
public class DictItem {
    String word;
    int offset;

    @Override
    public String toString() {
        return "DictItem{" +
                "word='" + word + '\'' +
                ", offset=" + offset +
                ", length=" + length +
                '}';
    }

    public DictItem(String word, int offset, int length) {
        this.word = word;
        this.offset = offset;
        this.length = length;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    int length;
}
