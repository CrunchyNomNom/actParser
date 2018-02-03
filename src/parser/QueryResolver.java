package parser;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class QueryResolver {

    private final ArrayList<Searched> query;
    private final DocNode root;

    QueryResolver(ArrayList<Searched> query, DocNode root) {
        this.query = query;
        this.root = root;
    }

    public ArrayList<DocNode> run() throws NoSuchElementException {
        ArrayList<DocNode> result = new ArrayList<>();
        DocNode node = root;
        for(Searched element: query) {
            if(element.isRangedSearch()) {
                ArrayList<DocNode> tmp = rangeOfNodes(node, element);
                for(DocNode t : tmp)
                    result.add(t);
                return result;
            }
            else node = goDeeper(node, element);
        }
        if(node != null) {
            result.add(node);
        }
        else throw new NoSuchElementException("Nie znaleziono elementu.");

        return result;
    }

    private DocNode goDeeper(DocNode node, Searched element) throws NoSuchElementException {
        for(DocNode nd : node.getList())
            if(nd.getType().equals(element.getType()))
                if(nd.getId().equals(element.getId()))
                    return nd;

        return null;
    }

    private ArrayList<DocNode> rangeOfNodes(DocNode node, Searched element) throws NoSuchElementException {
        ArrayList<DocNode> result = new ArrayList<>();
        for(DocNode nd : node.getList()) {
            if(nd.getType().equals(element.getType())) {
                if(nd.getId().equals(element.getId())){
                    result.add(nd);
                    if(nd.getId().equals(element.getLastId()))
                        return result;
                }
            }
        }
        if(result.equals(null))
            throw new NoSuchElementException("Nie znaleziono elementu.");

        return result;
    }

}
