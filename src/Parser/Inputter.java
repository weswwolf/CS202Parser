package Parser;

import java.io.*;
//import java.io.File;


public class Inputter {

    protected int line_count;
    protected String [] lines;

    Inputter()
    {
        //line_count = 0;
        lines = new String[]{};
    }

    public void input_file(String input_filename)
    {
        try {
            //System.out.println(new File(".").getAbsolutePath());
            //System.out.println(new File(".").getAbsoluteFile());
            BufferedReader reader = new BufferedReader(new FileReader(input_filename));
            String line = new String();
            line_count = 0;

            // read the file to determine how many lines there are
            while ((line = reader.readLine()) != null)
            {
                ++line_count;
                //System.out.println(line);
            }
            //System.out.println("the file has " + line_count + " line(s).");
            lines = new String[line_count]; // create an array with the exact number of elements as lines

            // start read the file again but this time set the lines
            reader = new BufferedReader(new FileReader(input_filename));
            int i = 0;
            line = new String();
            while ((line = reader.readLine()) != null && i < line_count)
            {
                lines[i] = line;
                ++i;
            }
        }
        catch (IOException exc) {
            //System.out.println("IO operation failed");
            System.out.println("no file with that name");
        }
       return;
    }

    public void display()
    {
        System.out.println("line count: " + line_count);
        for (int i = 0; i < line_count; ++i)
        {
            System.out.println(lines[i]);
        }
    }


}
