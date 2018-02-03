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
            System.out.print("parsed");
            QueryResolver qr = new QueryResolver(input.makeQuery(), doc);
            ArrayList<DocNode> list = qr.run();

            String output = new String();
            for(DocNode node : list) {
                output = output + node.toString() + "\r\n";
            }

            System.out.print(output);
        }
        else{ throw new InvalidPathException("Nieprawidlowa sciezka do pliku", ""); }
    }

    private String clear(String s){
        return  s.replaceAll("-\r\n", "")
                 .replaceAll(".Kancelaria Sejmu.*\r\n[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]\r\n","")
                 .replaceAll("\r\n.\r\n","")
                 .replaceFirst("s2p", " ")
                 .replaceFirst(".KON.*\r\nRZECZY.*EJ\r\nz.*7 r.\r\n", "KONSTYTUCJA RZECZYPOSPOLITEJ\r\n")
                 .replaceFirst("^Dz.U..*331\r\nUSTAWA\r\nz dnia .*r.\r\no oc.*ów\n", "USTAWA o Ochronie Konkurencji i Konsumentów\r\n");
    }
}
