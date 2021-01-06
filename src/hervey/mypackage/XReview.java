package hervey.mypackage;

import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class XReview {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
    
    //read in the positive adjectives in postiveAdjectives.txt
    try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        //System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
    
    //read in the negative adjectives in negativeAdjectives.txt
    try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  
  /** 
   * Method to return a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   * @param fileName - the name of the file containing the text to return
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * Method that returns the sentiment value of word as a number between 
   * -4 (very negative) to 3 (very positive sentiment)
   * 
   * @param word - the word whose sentiment value is being determined
   * @return - the sentiment value of word
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
  
  /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
  
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   * @return a random adjective found in either 
   * randomNegativeAdjectives.txt or randomPositiveAdjectives.txt
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }
  
  /**
   * Method to test sentimentVal with different words 
   */
  public static void activity1()
  {
    
    System.out.println(XReview.sentimentVal("happily"));
    System.out.println(XReview.sentimentVal("terrible"));
    System.out.println(XReview.sentimentVal("cold"));
    System.out.println();
    System.out.println(XReview.sentimentVal("awesome"));
    System.out.println(XReview.sentimentVal("rotten"));
    System.out.println(XReview.sentimentVal("super"));
  }
  
  
  /*
   * Method to determine the total sentiment value of a review. 
   */
  public static double totalSentiment(String fileName) {
    String reviewText = textToString(fileName);
    int len = reviewText.length();
    int index = 0;
    double sum = 0.0;
    String currentWord = "";
    while( index < len )
    {
      if( index == len-1 )
      {
        currentWord += reviewText.substring(index, index+1);
        currentWord = removePunctuation(currentWord);

        sum += sentimentVal(currentWord);
      }
      else if( !(reviewText.substring(index, index+1).equals(" ")))
      {
        currentWord += reviewText.substring(index, index+1);
      }
      else
      {
        currentWord = removePunctuation(currentWord);

        sum += sentimentVal(currentWord);
        currentWord = "";
      }
      index++;
    }//end while
    return sum;
  }
  
  public static double totalSentiment2(String fileName) {
    String reviewText = textToString(fileName);
    int len = reviewText.length();
    int i = 0; //Keeps track of index
    double sum = 0.0;
    int numWords = 0;
    String currentWord = "";
    while(i < len)
    {
      if(i == len-1)
      {
        currentWord += reviewText.substring(i, i+1);
        currentWord = removePunctuation(currentWord);
        sum += sentimentVal(currentWord);
        numWords++;
      }
      else if(!(reviewText.substring(i, i+1).equals(" ")))
      {
        currentWord += reviewText.substring(i, i+1);
      }
      else
      {
        currentWord = removePunctuation(currentWord);
        sum += sentimentVal(currentWord);
        numWords++;
        currentWord = "";
      }
      i++;
    }//end while
    
    //this solution has been modified to return average instead.  Ideally, it would
    //only increment numWords if the sentiment value was non-zero.
    return sum/numWords; //Return the average sentiment value
  }
  
  
  /**
   * Method to determine the star rating of a review found in the text file provided
   * by the parameter.
   * 
   * @param fileName - the name of the file containing the review
   * @return the star rating of the review, which is an integer value between 0 and 4
   */
  public static int starRating( String fileName )
  {
    double totalSentiment = totalSentiment(fileName);
    if( totalSentiment < -10 )
    {
      return 0;
    }
    else if( totalSentiment < 0 )
    {
      return 1;
    }
    else if( totalSentiment < 10 )
    {
      return 2;
    }
    else if( totalSentiment < 20 )
    {
      return 3;
    }
    else
    {
      return 4;
    }
  }
  
  /**
   * Method to create and return a fake review
   * 
   * @param fileName the name of the file containing the original review text
   * @return string containing the fake review
   */
  public static String fakeReview(String fileName)
  {
    String reviewWords = XReview.textToString(fileName);
    String oneWord;
    String fakeReview = "";
    
    // removing words by finding spaces and looping to process all words
    int spacePosition = reviewWords.indexOf(" ");
    while (spacePosition != -1)
    {
      // use substring to copy first word
      oneWord = reviewWords.substring(0, spacePosition);
      
      //remove that word from the string reviewWords and process oneWord
      reviewWords = reviewWords.substring(spacePosition +1);
      
      // if word is an adjective replace it with a random word
      if (oneWord.substring(0, 1).equals ("*"))
      {
        String punctuation = getPunctuation(oneWord);
        String randomWord = randomPositiveAdj();
        fakeReview = fakeReview + randomWord + punctuation;
      }
      else
      {
        fakeReview += oneWord; // add word if it is not an adjective
      }
      fakeReview += SPACE; // add a space to seperate words
      
      // find next space in the string
      spacePosition = reviewWords.indexOf(" ");
    } // end of while loop
    
    // processing the last word
    if (reviewWords.substring(0, 1).equals ("*"))
    {
      String punctuation = getPunctuation(reviewWords);
      String randomWord = randomPositiveAdj();
      fakeReview = fakeReview + randomWord + punctuation;
    }
    else
    {
      fakeReview += reviewWords; // add word if it is not an adjective
    }
    return fakeReview;
  } // end of method fakeReview
  
  /**
   * Method to create and return a strong fake review using String methods
   * to decompose the string
   * 
   * @param fileName the name of the file containing the original review text
   * @return string containing the fake review
   */
  public static String activity4 (String fileName)
  {
    String reviewWords = XReview.textToString(fileName);
    String oneWord;
    String fakeReview = "";
    String replacementWord;
    int spacePosition;
    double sentimentValue;
    
    while (reviewWords.length() >0)
    {
      // use substring to copy first word
      spacePosition = reviewWords.indexOf(" ");
      if (spacePosition != -1)
      {
        oneWord = reviewWords.substring(0, spacePosition);
        reviewWords = reviewWords.substring(spacePosition + 1);
      }
      else
      {
        oneWord = reviewWords;
        reviewWords = "";
      }
      // check to see if it is adjective
      if (oneWord.substring(0,1).equals("*"))
      { 
        String punctuation = getPunctuation(oneWord);
        // remove the * and remove any trailing punctuation
        String temp = oneWord.substring(1);
        if ( !punctuation.equals("") )
          temp = temp.substring(0, temp.length()-1);
        // get adjectives sentiment value
        sentimentValue = XReview.sentimentVal(temp);
        // find stronger adjective to replace current word
        if (sentimentValue >0)
        {
          replacementWord = randomAdjective();
          double replacementSentimentValue =
            sentimentVal(replacementWord);
          //loop until stronger adjective found
          while (replacementSentimentValue < sentimentValue)
          {
            replacementWord = randomAdjective();
            replacementSentimentValue =
              sentimentVal(replacementWord);
          }
        }
        else // look for stronger negative adjective
        {
          replacementWord = randomAdjective();
          double replacementSentimentValue =
            sentimentVal(replacementWord);
          while (replacementSentimentValue > sentimentValue)
          {
            replacementWord = randomAdjective();
            replacementSentimentValue =
              sentimentVal(replacementWord);
          }
        }
        fakeReview = fakeReview + replacementWord + punctuation;
      }
      else
        fakeReview = fakeReview + oneWord;
      fakeReview += SPACE;
    } // end of while loop
    return fakeReview;
  }
  
}
