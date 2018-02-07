package parser;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class QueryRes {

    private final ArrayList<Searched> query;
    private final DocNode root;

    QueryRes(ArrayList<Searched> query, DocNode root) {
        this.query = query;
        this.root = root;
    }

    public ArrayList<DocNode> run() throws NoSuchElementException {
        ArrayList<DocNode> result = new ArrayList<>();
        DocNode node = root;
        if (query.get(0).getType() == DocNodeType.ROOT) {
            result.add(node);
            return result;
        }
        else if (query.get(0).getType() != DocNodeType.ARTYKUL) {
            if(query.get(0).getType() == DocNodeType.ROOT){
                result.add(node);
                return result;
            }

            for(Searched element: query) {
                for(DocNode nd : node.getList())
                    if(nd.getType().equals(element.getType()))
                        if(nd.getId().equals(element.getId()))
                            node = nd;
            }
            if(node != root) {
                result.add(node);
            }
            else throw new NoSuchElementException("Nie znaleziono elementu.");
        }
        else if (query.get(0).getType() == DocNodeType.ARTYKUL) {
            if(query.get(0).getId() != query.get(0).getLastId()) {
                result = findManyArts(node, Integer.parseInt(query.get(0).getId()), Integer.parseInt(query.get(0).getLastId()));
            } else {
                for(Searched element: query) {
                    if(element.getType() == DocNodeType.ARTYKUL) {
                        ArrayList<DocNode> tmp = findArt(root, element.getId());
                        if(tmp.isEmpty()){ throw new NoSuchElementException("Nie znaleziono elementu."); }
                        else node = tmp.get(0);
                    } else for(DocNode nd : node.getList())
                        if(nd.getType().equals(element.getType()))
                            if(nd.getId().equals(element.getId()))
                                node = nd;
                }
                result.add(node);
            }
        }
        if(result.isEmpty())
            throw new NoSuchElementException("Nie znaleziono elementu.");
        return result;
    }

    private ArrayList<DocNode> findArt(DocNode root, String id){
        ArrayList<DocNode> result = new ArrayList<>();
        int tmp = Integer.parseInt(id.replaceAll("[a-z]", ""));
        if(tmp >= 115 && tmp <= 129){
            result.add(new DocNode(DocNodeType.ARTYKUL,"0",null,"Art. 115–129. (pominięte)"));
        } else {
            for (DocNode node : root.getList()) {
                if (node.getType().getPriority() > 4) {
                    result.addAll(findArt(node, id));
                } else if (id.equals(node.getId()))
                    result.add(node);
            }
        }
        return result;
    }

    private ArrayList<DocNode> findManyArts(DocNode root, int alpha, int omega){
        ArrayList<DocNode> result = new ArrayList<>();
        for(DocNode node : root.getList()){
            if(node.getType().getPriority() > 4){
                result.addAll(findManyArts(node, alpha, omega));
            } else if(node.getId().contains("-129") && alpha >= 115 && omega <= 129){
                result.add(new DocNode(DocNodeType.ARTYKUL,"0",null,"Art. 115–129. (pominięte)"));
            } else {
                int tmp = Integer.parseInt(node.getId().replaceAll("[a-z]", "").replaceAll("–129",""));
                if(alpha <= tmp && omega >= tmp)
                    result.add(node);
            }
        }
        return result;
    }
}