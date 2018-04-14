/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import  java.util.HashSet;
import java.util.HashMap;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    //-----------------------------------------------------
    ArrayList<String> wordList = new ArrayList<String>();
    HashSet<String> wordSet=new HashSet<>();
    HashMap<String,ArrayList<String>> lettersToWord=new HashMap<>();
    int WordLength=DEFAULT_WORD_LENGTH;
    HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<>();
    public static String sortletters(String s){

        char tempArray[] = s.toCharArray();
        Arrays.sort(tempArray);
        return  new String(tempArray);
    }


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String returnword=sortletters(word);
            ArrayList<String> val;
            if (lettersToWord.containsKey(returnword))
            {
                ArrayList<String> temp=lettersToWord.get(returnword);
                if(!temp.contains(word))
                {
                    temp.add(word);
                }
                lettersToWord.put(returnword,temp);
            }
            else
            {
                ArrayList<String> arr=new ArrayList<>();
                arr.add(word);
                lettersToWord.put(returnword,arr);
            }
            int len=returnword.length();

            if(sizeToWords.containsKey(len)){
                ArrayList<String> arr=sizeToWords.get(len);
                arr.add(word);
                sizeToWords.put(len,arr);
            }
            else
            {
                ArrayList<String> temp1=new ArrayList<>();
                temp1.add(word);
                sizeToWords.put(len,temp1);
            }
        }
    }


    public boolean isGoodWord(String word, String base)
    {
        if(wordSet.contains(word))
        {
            if(!word.contains(base))
            {
                return true;
            }
        }
        return false;
    }


    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String returnword=sortletters(targetWord);
        for(String temp2:wordList)
        {
            String retwordlist=sortletters(temp2);
            if(returnword.length()==temp2.length()){
                if(returnword.equals(retwordlist)){
                    result.add(temp2);
                }
            }
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();
        String retnewstring=new String();
        String tmp;
        ArrayList tmparrlist;
        String get=new String();


        for(char j='a';j<='z';j++)
        {
            tmp=word+j;
            retnewstring=sortletters(tmp);
            if(lettersToWord.containsKey(retnewstring))
            {
                temp=lettersToWord.get(retnewstring);
                for (String s:temp) {
                    result.add(s);
                }

                }
        }
        return result;
    }

    public String pickGoodStarterWord()
    {
        ArrayList<String> z=sizeToWords.get(WordLength);
        if(WordLength< MAX_WORD_LENGTH)
            WordLength++;
        while(true)
        {
            String w=z.get(random.nextInt(z.size()));
            if(getAnagramsWithOneMoreLetter(w).size()>=MIN_NUM_ANAGRAMS)
                return w;
        }


    }
}



