import java.util.Scanner;

public class Main {

	private EnigmaFile encryptInput;
	
	private Bombe breaker;
	
	public void encryptInputFile(Scanner userInput) {
		
		System.out.println("**************************************************************\n"
		   		+ "                      Encrypt message              \n"
		   		+ "**************************************************************\n ");
		
		System.out.println("************   Entering enigma machine settings  *************\n ");
		
		try {
			
			encryptInput.encryptMessage(userInput);
			
			System.out.println("Finished");
		
		} catch (Exception e) {
			
			
			System.err.println(e.getMessage() + " - Please try again");
			
			
			encryptInputFile(userInput);
		}
		
	}
	
	public void decryptMessage(Scanner userInput) {
		
		System.out.println("**************************************************************\n"
				   		+ "                      Decrypt message              \n"
				   		+ "**************************************************************\n ");
		
		System.out.println("Enter message you wish to decrypt");
		
		String message = userInput.nextLine();
		
		System.out.println("Enter possible search criteria (seperate each word by space) ");
		
		String searchCriteria[] = userInput.nextLine().split(" ");
		
		System.out.println("Do you want program to search againt a list of common words along side search criteria ?"
				+ "\n (hint: this will increase decryption time) "
				 );
		
		String userResponse = userInput.nextLine();
		
		boolean checkCommonWords = false;
		
		if(userResponse.contains("y") | userResponse.contains("Y")) {
			
			checkCommonWords = true;
			
		}
		
		System.out.println("\n******** Entering missing (and/or existing) machine settings ********* \n");
		
		try {
			breaker.decryptInput(message, searchCriteria, checkCommonWords, userInput);
		} catch (Exception e) {
			System.err.println("Try again");
			decryptMessage(userInput);
		}
		
	}
	
	public void encryptWhileWriting(Scanner userInput) {
		
		System.out.println("**************************************************************\n"
		   		+ "                Encrypt while wrting              \n"
		   		+ "**************************************************************\n ");
		
		try {
			EnigmaMachine machine = encryptInput.buildEnigmaMachineFromInput(userInput);
		} catch (Exception e) {
			System.err.println("Try again");
			encryptWhileWriting(userInput);
		}
			
	}

	public void quit() {
		
		System.out.println("************\n  Quit \n************\n ");
		
		System.exit(0);
		
	}
	
	public Main() {
		
		breaker = new Bombe();
		
		encryptInput = new EnigmaFile();
		
	}
	
	public static void main(String[] args) {
		
		int userResponse;
		
		Scanner userInput = new Scanner(System.in);
		
		Main myProgram = new Main();
		
		while(true) {
			
			System.out.println("What do you want to do ? Enter ... \n 1 - Encrypt input file \n 2 - Use Bombe to decrypt message  \n 3 - Encrypt as you write \n 4 - Quit ");
			
			userResponse = Integer.parseInt( userInput.nextLine() );
			
			if( userResponse == 1 ) {
			
				myProgram.encryptInputFile(userInput);
			
			}else if( userResponse == 2 ) {
			
				myProgram.decryptMessage(userInput);
			
			}else if( userResponse == 3) {
			
				myProgram.encryptWhileWriting(userInput);
			
			}else {
			
				myProgram.quit();
			
			}

		}
		
		
	}

}
