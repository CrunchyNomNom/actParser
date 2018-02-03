package parser;

import java.util.ArrayList;

public class DocNode {

    private final DocNodeType type;
    private final DocNode parent;
    private final String id;
    private ArrayList<DocNode> list;
    private String content;

    public DocNode(DocNodeType type, String id, DocNode parent, String content){
        this.type = type;
        this.id = id;
        this.parent = parent;
        this.list = new ArrayList<>();
        this.content = content;
    }

    public DocNodeType getType() {
        return type;
    }

    public DocNode getParent() {
        return parent;
    }

    public ArrayList<DocNode> getList() { return list; }

    public String getId() { return id; }

    @Override
    public String toString() {
        String result = content;
        for(DocNode node : list)
            result = result + "\r\n" + node.toString();
        return result;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addToList(DocNode node) {
        list.add(node);
    }
}
