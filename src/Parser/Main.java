package Parser;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Lexer do_it = new Lexer();
        //do_it.process_input();




        //System.out.println("hello");
        //Node mine;
        //BST first = new BST();
        /*
        first.add(1);
        first.add(2);
        first.add(3);
        first.add(4);
        first.add(5);
        first.add(6);
        first.add(7);
        first.add(8);
         */
        /*
        first.add(8);
        first.add(7);
        first.add(6);
        first.add(5);
        first.add(4);
        //first.add(3);
        //first.add(2);
        //first.add(1);
        */
        /*
        for(int i = 0; i < 25; ++i) {
            first.add(i);

        }
         */

        //Lexer lex = new Lexer();
        //lex.process_input();

        Parser parser = new Parser();
        parser.process_input();
        parser.parse();





        /*
        //first.display();
        //String input = "if (x isequals -5) and (y isnotequals 10) print (x90); // this is a statement";
        //String input = "print (\"Hello World\"); // this is a statement";
        String input = "whatever99 text";
        //Lexer lexer = new Lexer();
        try
        {
            //System.out.println("Tokens to match:");
            //parser.display_matching_tokens();
            lex.generate_tokens(input);
            System.out.println("Tokens generated from string:");
            lex.display_generated_tokens();
            lex.display_matching_tokens();
        }
        catch (Lexer.TokenException error)
        {
            System.out.println(error.getMessage());
        }

         */

        // write your test code HERE
    }
}
