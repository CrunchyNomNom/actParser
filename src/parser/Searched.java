package parser;

public class Searched {

    private DocNodeType type;
    private String id;
    private String lastId;

    public Searched(DocNodeType type, String id) {
        this.type = type;
        this.id = id;
        if(this.id.contains(":")){
            String[] tmp = this.id.split(":");
            this.id = tmp[0];
            lastId = tmp[1];
        } else this.lastId = id;
    }

    public DocNodeType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getLastId() {
        return lastId;
    }

    public void refactor(String s) {
        switch (s){
            case "d":      type = DocNodeType.DZIAL;
            case "r":      type = DocNodeType.ROZDZIAL;
            case "a":      type = DocNodeType.ARTYKUL;
            case "u":      type = DocNodeType.USTEP;
            case "p":      type = DocNodeType.PUNKT;
            case "l":      type = DocNodeType.LITERA;
            default:       type = DocNodeType.UNDEFINED;
        }
    }
}
