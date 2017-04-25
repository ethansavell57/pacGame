/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import java.io.*;

/**
 *
 * @author ethan
 */
public class writeFile {

    private String path;
    private boolean append_to_file = false;

    public writeFile(String file_path) {
        path = file_path;
    }

    public writeFile(String filePath, boolean append_value) {
        path = filePath;
        append_to_file = append_value;
    }

    public void writeToFile(String textLine) throws IOException {
        FileWriter write = new FileWriter(path, append_to_file);
        PrintWriter print_line = new PrintWriter( write );
        print_line.printf("%s" + "%n", textLine);
        print_line.close();




    }

}
