package parser;

public class Searched {

    private final DocNodeType type;
    private String id;
    private boolean rangedSearch;
    private String lastId;

    public Searched(DocNodeType type, String id) {
        this.type = type;
        this.id = id;
        rangedSearch = false;
        if(this.id.contains(":"));
            String[] tmp = this.id.split(":");
            this.id = tmp[0];
            lastId = tmp[1];
            rangedSearch = true;
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

    public boolean isRangedSearch() {
        return rangedSearch;
    }
}
