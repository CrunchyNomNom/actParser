package parser;

import org.apache.commons.cli.MissingArgumentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Action {

    private Interpreter in;

    public Action(){
        this.in = new Interpreter();
    }

    public void run(Input input) throws InvalidPathException, IOException, MissingArgumentException, NoSuchElementException {
        String filePath = input.getFilePath();
        if(Files.exists(Paths.get(filePath))){
            byte[] mainArray = Files.readAllBytes(Paths.get(filePath));
            String text = clear(new String(mainArray, "UTF-8"));
            DocNode doc = in.makeDoc(text);
            QueryResolver qr = new QueryResolver(input.makeQuery(), doc);
            ArrayList<DocNode> list = qr.run();

            String output = new String();
            for(DocNode node : list) {
                output = output + node.toString() + "\n";
            }

            System.out.print(output);
        }
        else{ throw new InvalidPathException("Nieprawidlowa sciezka do pliku", ""); }
    }

    private String clear(String s){
        return  s.replaceAll("-\n", "")
                 .replaceAll(".Kancelaria Sejmu.*\n[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]\n","")
                 .replaceAll("\n.\n","")
                 .replaceFirst("s2p", " ")
                 .replaceFirst(".KON.*\nRZECZY.*EJ\nz.*7 r.\n", "KONSTYTUCJA RZECZYPOSPOLITEJ\n")
                 .replaceFirst("^Dz.U..*331\nUSTAWA\nz dnia .*r.\no oc.*ów\n", "USTAWA o Ochronie Konkurencji i Konsumentów\n");
    }
}
