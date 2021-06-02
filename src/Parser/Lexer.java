package Parser;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// a lexer does lexical analysis on a string to create a list of Tokens.
// the job of the lexer is to use an input string and create a list of tokens that represent the string
public class Lexer extends Inputter{
    protected LinkedList<TokenType> matching_tokens;
    protected LinkedList<Token> generated_tokens;

    /*
    BADMATCH,
    NUMBER,
    IDENTIFIER,
    KEYWORD,
    OPERATOR,
    PUNCTUATOR,
    COMMENT
     */
    public Lexer()
    {
        matching_tokens = new LinkedList<>(); // TokenType
        generated_tokens = new LinkedList<>(); // Token
        // add some types of matching tokens
        // a BADMATCH is when invalid identifier syntax is used
        add_token_type("-?[0-9]+[a-zA-Z]+", TokenType.TkType.BADMATCH); // this needs to be checked before operators
        add_token_type("(\\/)(\\/).*", TokenType.TkType.COMMENT); // this needs to be checked before operators
        add_token_type("-?[0-9]+", TokenType.TkType.NUMBER); // this needs to be checked before operators
        add_token_type("[+-/\\*=]", TokenType.TkType.OPERATOR);
        add_token_type("var|if|else|isequal|isnotequal|and|or|print", TokenType.TkType.KEYWORD);
        add_token_type("[a-zA-Z]+[0-9]*", TokenType.TkType.IDENTIFIER);
        add_token_type("\"(.)*\"", TokenType.TkType.STRING);
        // this needs to be checked before checking for identifiers
        add_token_type("[;()]", TokenType.TkType.PUNCTUATOR);
    }

    public void add_token_type(String pattern, TokenType.TkType code)
    {
        matching_tokens.add(new TokenType(Pattern.compile("^("+pattern+")"), code));
    }

    public void generate_tokens(String input)
    {
        String trimmed = input.trim();
        while (!trimmed.equals("")) // there is more input to lex
        {
            boolean found_match = false;
            // for each token in the matching tokens list,
            for (TokenType token : matching_tokens)
            {
                Matcher m = token.make_matcher(trimmed);
                if (m.find()) // a match is found
                {
                    found_match = true;
                    String token_val = m.group().trim();
                    trimmed = m.replaceFirst("").trim();
                    if (token.get_code() == TokenType.TkType.BADMATCH)
                        throw new TokenException("Bad Token: " + token_val);
                    generated_tokens.add(new Token(token.get_code(), token_val));
                    break;
                }
            }
            if (!found_match) throw new TokenException("Did not find token match: " + trimmed.charAt(0));
        }
    }

    public void display_generated_tokens()
    {
        //int i = 1;
        for (Token token : generated_tokens)
        {
            //System.out.println("Line " + i + ":");
            //if (token.value.equals(";"))
            //    ++i;

            token.display();
        }
    }

    public void display_matching_tokens()
    {
        for (TokenType type : matching_tokens)
        {
            type.display();
        }
    }

    // Details about one type of token -- what kind of pattern it matches as well as what it represents
    // this class does not have the value of the token
    public static class TokenType {

        // token types
         enum TkType {
            // ARITHEMETIC, this is now an 'operator'
            BADMATCH,
            NUMBER,
            IDENTIFIER,
            KEYWORD,
            OPERATOR,
            PUNCTUATOR,
            COMMENT,
            STRING
        }
        private final Pattern to_match;
        private final TkType code;

        // constructor
        public TokenType(Pattern to_match, TkType code)
        {
            this.to_match = to_match;
            this.code = code; // this could be an enumerator??
        }

        // returns a matcher object that uses to_match pattern and string input
        public Matcher make_matcher(String input)
        {
            return to_match.matcher(input);
        }

        // give the code to the newly created Token
        public TkType get_code()
        {
            return code;
        }

        public void display()
        {
            System.out.print(to_match + " " + code + '\n');
        }
    }

    // be a RuntimeException plus more
    public static class TokenException extends RuntimeException
    {
        public TokenException(String message)
        {
            // give the message to the parent class
            super(message);
        }
    }

    public static class Token extends Node {
        private final TokenType.TkType code; // enumerator for different token types
        private final String value; // the value of the token

        public Token (TokenType.TkType code, String value)
        {
            this.code = code;
            this.value = value;
        }

        public void display()
        {
            System.out.print(code + " " + value + '\n');
        }
    }

    public void process_input()
    {
        //Lexer lexer = new Lexer();
        //Scanner scan = new Scanner(System.in);
        //String filename;
        //System.out.println("enter your filename: ");
        //filename = scan.nextLine();
        //System.out.println("Ah, so your filename is " + filename);
        input_file("file.txt");

        int i = 0;
        try
        {
            if (line_count <= 0)
            {
                System.out.println("No input lines");
                return;
            }
            //System.out.println("Tokens to match:");
            //parser.display_matching_tokens();
            //System.out.println("Total lines: " + line_count);
            for (i = 0; i < line_count; ++i) {
                //System.out.println("Tokens generated from line " + (i+1) + ":");
                generate_tokens(lines[i]);
                //display_matching_tokens();
            }
            display_generated_tokens();
        }
        catch (Lexer.TokenException error)
        {
            // could be a bad token or no match
            System.out.print("On line " + (i+1) + ", ");
            System.out.println(error.getMessage());
        }

    }

}

