import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is a text-based implementation of the Hangman game.
 * This game does not follow the classic rules where the player has limited guesses, but instead has unlimited guesses.
 * The player gets a new word when the player has finished the previous word or restarts the program.
 * Date: 6/11/2018
 * @author Joshua Johnston
 *
 */

public class HangMan {
	
	public static void main(String[] args) throws IOException{
		
		int missedCount = 0;
		boolean inPlay = false;
		
		String[] words = createWordList();
		String currentWord = getWord(words);
		String tempWord = currentWord.replaceAll("[a-zA-Z]", "*");	           //setting the word to all '*'	
		
		System.out.print("(Guess) Enter a letter in " + tempWord + " > ");
		String currentGuess = getGuess();
		
		while(true){
			
			if(currentGuess.equals("y") && inPlay){                           //Finished the previous word, and the user wants to keep playing
				currentWord = getWord(words);                                 //Get a new word
				tempWord = currentWord.replaceAll("[a-zA-Z]", "*");           //Setting the word to all '*' again
				inPlay = false;
			}
			else if(currentWord.contains(currentGuess)){                      //If the guess is in word, and not already in the word then add it
					if(tempWord.contains(currentGuess)){
						System.out.println(currentGuess + " is already in the word");
					}
					else{
						
						int[] locations = findLocations(currentWord, currentGuess.charAt(0));  //Getting the list of locations where the letter is in the word
						char[] temp =  tempWord.toCharArray();
					
						for(int i = 0; i < locations.length; i++){
							if(locations[i] != -1){
								int index = locations[i];
								temp[index] = currentGuess.charAt(0);                          //replacing the '*' with the letter
							}
						}
						tempWord = String.valueOf(temp);
					
					}				
				}
			else{
				missedCount++;
				System.out.println(currentGuess + " is not in the word");				
			}
			
			if(currentWord.equals(tempWord)){     //You win else keep guessing
				System.out.println("The word is " + currentWord + ". You missed " + missedCount + " times");
				System.out.print("Do you want to guess an other word? Enter y or n > ");
				missedCount = 0;				
				inPlay = true;
				
				currentGuess = getGuess();
				if(currentGuess.equals("n")){
					break;
				}
				
			}
			else{
				System.out.print("(Guess) Enter a letter in " + tempWord + " > ");	
				currentGuess = getGuess();
			}			
		}
		
	}//end of main()
//-----------------------------------------------------------------------------------
/**
 * This method reads a file of words. The first line has the number of words.
 * An array is created with the size given from the file, and then the words are stored into the array.
 * @return array[]
 * @throws IOException
 */
public static String[] createWordList() throws IOException{

	FileReader inputFile = new FileReader("words1.txt");	
	BufferedReader reader = new BufferedReader(inputFile);
	
	String firstLine = reader.readLine();	
	int size = Integer.parseInt(firstLine);
	
	String[] words = new String[size];
	
	for(int i = 0; i < words.length; i++){		
			
		words[i] = reader.readLine();		
	}
	
	inputFile.close();
	reader.close();
	
	return words;	
}//end of createWordList()
//-----------------------------------------------------------------------------------
/**
 * This method generates a random word from the given array.
 * @param array[] wordsList
 * @return String word
 */
public static String getWord(String[] wordsList){	
	
	String randomWord = wordsList[(int)(Math.random() * wordsList.length)];
	
	return randomWord;
}//end of getWord()
//-----------------------------------------------------------------------------------
/**
 * This method reads the players input.
 * @return String guess
 */
public static String getGuess(){
	@SuppressWarnings("resource")
	Scanner input = new Scanner(System.in);
	
	String guess = input.next();	
	
	return guess;
}//end of getGuess()
//-----------------------------------------------------------------------------------
/**
 * This method finds the locations of the letter in the given string.
 * @param word
 * @param letter
 * @return int[] locations
 */
public static int[] findLocations(String word, char letter){
	int[] locations = new int[word.length()];
	
	for(int i = 0; i < locations.length; i++){     //initializing the array to all -1 because 0 can be a location.
		locations[i] = -1;
	}
	
	for(int i = 0; i < word.length(); i++){       
		if(word.charAt(i) == letter){
			locations[i] = i;
		}
		
	}
	return locations;	
}//end of findLocations()
//-----------------------------------------------------------------------------------

/////////////////////////////////////////////////////////////////////////////////////
}//end of HangMan Class
