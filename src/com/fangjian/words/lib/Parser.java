package com.fangjian.words.lib;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: fangjian
 * Date: 13-7-6
 * To change this template use File | Settings | File Templates.
 */
public class Parser {
    /*
    public static void main(String[] args) throws IOException {
        System.out.print("hello");
        // File file=new File("d.ifo");
        Properties prop = getProp("d.ifo");
        prop.list(System.out);
        ArrayList<DictItem> dictItems=readDictIdx("d.idx");
        printList(dictItems);

        printWordList("d.dict",dictItems);
    }

    */
    private Context mContext;
    private AssetManager assets;
    public Parser(Context context){
        this.mContext=context;
        assets=context.getAssets();
    }

    /**
     * read ifo file, get word count
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public  Properties getprop(String filename) throws IOException {
        //inputstream is = new fileinputstream("d.ifo");
        InputStream is =assets.open(filename);
        Properties prop = new Properties();
        prop.load(is);
        is.close();
        return prop;
        //prop.list(system.out);
    }

    /**
     * read idx file and save to buffer
     *
     * @param filename
     * @throws IOException
     */
    public  ArrayList<DictItem> readDictIdx(String filename) throws IOException {
        InputStream is =assets.open(filename);
        DataInputStream dis = new DataInputStream(is);
        final int MAX_WORD_LENGTH = 256;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(MAX_WORD_LENGTH);
        ArrayList<DictItem> dictItemArrayList=new ArrayList<DictItem>();

        //read all words
        int b = dis.read();
        while (b != -1) {
            //read a word,  which end with '\0'
            baos.reset();
            do {
                baos.write(b);
            } while ((b = dis.read()) != 0);
            String word = baos.toString("UTF-8");
            int offset = dis.readInt();
            int length = dis.readInt();
            //save item
            DictItem di=new DictItem(word,offset,length);
            dictItemArrayList.add(di);
            //read next item,maybe -1 ,means End of file
            b = dis.read();
        }
        is.close();
        return dictItemArrayList;
    }

    public  void printList(List<DictItem> list){
        for(DictItem obj:list){
            System.out.println(obj.toString());
        }
    }
    public  String getContent(String dictFileName,int offset,int length) throws IOException{
        InputStream is =assets.open(dictFileName);
        DataInputStream dis = new DataInputStream(is);
        byte[] b=new byte[length];
        skipFully(dis,offset);
        dis.readFully(b,0,length);
        is.close();
        String content=new String(b,"UTF-8");
        return content;
    }
    public   void skipFully(DataInputStream dis,int off)throws IOException{
        int n = 0;
        while (n < off) {
            int count = dis.skipBytes(off-n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }
    public   void printWordList(String dictFileName,List<DictItem> dictItems)throws IOException{
        for(DictItem di:dictItems){
            String content=getContent(dictFileName,di.getOffset(),di.getLength());
            System.out.println(di.toString());
            System.out.println(content);

        }
    }
}
