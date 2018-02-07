package parser;

import org.apache.commons.cli.*;

import java.util.ArrayList;

public class Input {

    private CommandLine cmd;
    private Options options;

    public Input(String[] args) throws ParseException {
        this.options = this.setOptions();
        this.parse(args);
    }

    private Options setOptions() {
        Options options = new Options();
        options.addOption("f", true, "Wypisz plik");
        options.addOption("d", true, "Wypisz dzial");
        options.addOption("r", true, "Wypisz rozdzial");
        options.addOption("a", true, "Wypisz artykul");
        options.addOption("u", true, "Wypisz ustep");
        options.addOption("p", true, "Wypisz punkt");
        options.addOption("l", true, "Wypisz litere");
        options.addOption("s", false, "Wypisz spis tresci");

        return options;
    }

    private void parse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options,args);
    }

    public String getFilePath() throws MissingArgumentException {
        if(cmd.hasOption("f")) {
            return cmd.getOptionValue("f");
        }
        else throw new MissingArgumentException("Error. Wywolaj opcje -f podajac sciezke do pliku.");
    }

    public String getDzial() { return cmd.getOptionValue("d"); }

    public String getRozdzial() { return cmd.getOptionValue("r"); }

    public String getArtykul() { return cmd.getOptionValue("a"); }

    public String getUstep() { return cmd.getOptionValue("u"); }

    public String getPunkt() { return cmd.getOptionValue("p"); }

    public String getLitera() { return cmd.getOptionValue("l"); }

    public ArrayList<Searched> makeQuery() {
        ArrayList<Searched> query = new ArrayList();
        DocNodeType type = DocNodeType.UNDEFINED;
        String[] options = new String("d r a u p l").split(" ");
        for(String letter : options){
            if(cmd.hasOption(letter)){
                String xd = cmd.getOptionValue(letter);
                type = refactor(letter);
                Searched s = new Searched(type, xd);
                query.add(s);
            }
        }
        if (query.isEmpty())
            query.add(new Searched(DocNodeType.ROOT, "0"));

        return query;
    }

    public DocNodeType refactor(String s) {
        switch (s){
            case "d":      return DocNodeType.DZIAL;
            case "r":      return DocNodeType.ROZDZIAL;
            case "a":      return DocNodeType.ARTYKUL;
            case "u":      return DocNodeType.USTEP;
            case "p":      return DocNodeType.PUNKT;
            case "l":      return DocNodeType.LITERA;
            default:       return DocNodeType.UNDEFINED;
        }
    }

    public boolean isTOC(){
        if(cmd.hasOption("s"))
            return true;
        return false;
    }
}
