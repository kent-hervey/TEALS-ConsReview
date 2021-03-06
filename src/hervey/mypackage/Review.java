package hervey.mypackage;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
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
       // System.out.println(temp);
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
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
	  
	  System.out.println("at top of textToString method");
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
    System.out.println("temp is:  " + temp);
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
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
  
/* Activity 2 Sentiment Value
 * takes filename as a String and returns a double
 * looks at each word in the review and finds the sentiment value
 * each word's value gets added to a total sentiment variable
 * that variable is returned once the review is fully looked at
 */
  public static double totalSentiment(String filename) {
	  String review = textToString(filename);
	  review += " ";
	  
	  double sentimentTotal = 0.0;
	  String word = "";
	  
	  for(int i = 0; i < review.length(); i++) {
		  char letter = review.charAt(i);
		  
		  if (letter != ' ') {
			  word += letter;
		  } else {
			  word = removePunctuation(word);
			  double sentiment = sentimentVal(word);
			  sentimentTotal += sentiment;
			  word = "";
		  }
	  }
	  
	  return sentimentTotal;
  }
  
/* Activity 2 Star Rating
 * takes filename as a String and returns an integer
 * takes the total sentiment of the filename and sets it to a new variable "total"
 * total is then compared to a number to figure out what range of numbers it fall into
 * that range is associated with a star ranking and total's star ranking is returned
 */
  public static int starRating(String filename) {
	  double total = totalSentiment(filename);
	  int stars = 0;
	  
	  if (total > 35.0) {
		  stars = 5;
	  }
	  else if (total > 25.0 && total <= 35.0) {
		  stars = 4;
	  }
	  else if (total > 15.0 && total <= 25.0) {
		  stars = 3;
	  }
	  else if (total > 5.0 && total <= 15.0) {
		  stars = 2;
	  }
	  else if (total > -5.0 && total <= 5.0) {
		  stars = 1;
	  }
	  else {
		  stars = 0;
	  }
	  
	  return stars;
  }
  
// Activity 3
  public static String fakeReview(String filename) {
	  System.out.println("asdlkfj");
	  String review = textToString(filename);
	  
	  System.out.println("review is:  " + review);
	  String fakeReview =  "";
	  
	  for(int i = 0; i < review.length(); i++) {
		  char letter = review.charAt(i);
		  
		  if (letter != '*') {
			  fakeReview += review.charAt(i);
		  } else if(letter == '*') {
			  fakeReview += randomAdjective();
			  while(letter != ' ') {
				i++;
			  }
		  }
			  
	  }
	  System.out.println("bottom of fakeReview just before return and fakeReview var is:  " + fakeReview);
	  return fakeReview;
  }
}