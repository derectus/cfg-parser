import java.io.IOException;
import java.util.Scanner;

import com.nuricilengir.gammar.CFGException;
import com.nuricilengir.gammar.CFGrammar;
import com.nuricilengir.handler.FileHandler;

public class Demo {
	static Scanner scanner;
	public static void main(String[] args) {
		scanner = new Scanner(System.in);

		System.out.println("Please enter <input_file> : ");
		String input_file = scanner.nextLine();
		System.out.println("Please enter <input_string> : ");
		String input_string = scanner.nextLine();
		try {
			CFGrammar grammar = FileHandler.readFromFile(input_file);

			System.out.println((grammar.isAccepted(input_string)) ? "Is valid" : "Is not valid");

		} catch (IOException e) {
			System.out.println("Error reading file or file is not formatted correctly. \n\t"
					+ "<input_file> entered was: " + input_file);

		} catch (IndexOutOfBoundsException id) {
			System.out.println("Invalid arguments provided. Please try \n\t<input_file> <input_string>\n\t"
					+ "Informed Arguments: " + input_file +" ," +input_string);

		} catch (CFGException j) {
			j.printStackTrace();
		}
	}

}
