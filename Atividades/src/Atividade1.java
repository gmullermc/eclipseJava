import java.util.Random;
import java.util.Scanner;

public static void main(String[] args) {
	public class Atividade1 {
			  Random rand = new Random();
			
			  int num = rand.nextInt(100);
			  
			  Scanner myObj = new Scanner(System.in);
			  System.out.print("Chute um numero: ");
			  int chute = myObj.nextInt();
			  
			  	  if (num == chute) {
				  System.out.println("Como tu soube? Parabéns.");
			  }else {
				  System.out.println("Tente de novo.");					  
			  }
		  }
}		  