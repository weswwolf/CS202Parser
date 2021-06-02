package Parser;

import java.util.Iterator;

// the Parsers job is to take a bunch of tokens and do syntactic analysis on them
// to create what people call an Abstract Syntax Tree that represents the logic of
// the tokens
public class Parser extends Lexer
{
    private Abstract_Syntax_Tree ast;
    private int token_list_index;

    Parser()
    {
        ast = new Abstract_Syntax_Tree();
        token_list_index = 0;
    }

    // form the AST from the Linked List of tokens
    public void parse()
    {
        // if the Token List is empty, we cannot parse it, so return

        // for each token in the tokens list
        // check the previous token to ensure the syntax is okay
        // then add the token to the tree
    }
}
