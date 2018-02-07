package parser;

public class Interpreter {

    public Interpreter() {

    }

    public DocNode makeDoc(String text,boolean isTOC) {
        DocNode root = new DocNode(DocNodeType.ROOT, null, null, null);
        DocNode node = root;
        String activeContent = new String();
        String id = new String();
        boolean isNextTitle = false;

        for (String line : text.replaceAll("\r\n", "#").split("#")) {

            if (line.startsWith("DZIAŁ ")) {
                isNextTitle = true;
                node.setContent(activeContent);
                while (DocNodeType.DZIAL.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[1].replace(".", "");
                activeContent = "\r\n\t" + line;
                node = new DocNode(DocNodeType.DZIAL, id, node, activeContent);
                node.getParent().addToList(node);

            } else if (line.startsWith("Rozdział ")) {
                isNextTitle = true;
                node.setContent(activeContent);
                while (DocNodeType.ROZDZIAL.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[1].replace(".", "");
                activeContent = "\r\n\t" + line;
                node = new DocNode(DocNodeType.ROZDZIAL, id, node, activeContent);
                node.getParent().addToList(node);

            }  else if (line.startsWith("Art. ")) {
                isNextTitle = false;
                node.setContent(activeContent);
                while (DocNodeType.ARTYKUL.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                String[] lineContent = line.split(" ");
                id = lineContent[1].replace(".", "");
                if (lineContent.length > 2 && lineContent[2].equals("1.")) {
                    activeContent = lineContent[0] + " " + lineContent[1];
                    node = new DocNode(DocNodeType.ARTYKUL, id, node, activeContent);
                    node.getParent().addToList(node);
                    node.setContent(activeContent);
                    activeContent = line.replaceFirst("Art\\. [0-9a-z]{1,4}\\. ", "");
                    node = new DocNode(DocNodeType.USTEP, "1", node, activeContent);
                    node.getParent().addToList(node);
                } else {
                    activeContent = line;
                    node = new DocNode(DocNodeType.ARTYKUL, id, node, activeContent);
                    node.getParent().addToList(node);
                }

            }  else if (isNextTitle || Character.isUpperCase(line.charAt(0)) && Character.isUpperCase(line.charAt(1))){
                node.setContent(activeContent);
                DocNode tmp = node;
                while (DocNodeType.TYTUL.getPriority() >= tmp.getType().getPriority())
                    tmp = tmp.getParent();

                DocNode title = new DocNode(DocNodeType.TYTUL, id, tmp, "\t\t" + line);
                title.getParent().addToList(title);

            }else if (line.matches("^\\d+[a-z]?\\..*")) {
                node.setContent(activeContent);
                while (DocNodeType.USTEP.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[0].replace(".", "");
                activeContent = line;
                node = new DocNode(DocNodeType.USTEP, id, node, activeContent);
                node.getParent().addToList(node);

            } else if (line.matches("^\\d+[a-z]?\\).*")) {
                node.setContent(activeContent);
                while (DocNodeType.PUNKT.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[0].replace(")", "");
                activeContent = line;
                node = new DocNode(DocNodeType.PUNKT, id, node, activeContent);
                node.getParent().addToList(node);

            } else if (line.matches("^[a-z]\\).*")) {
                node.setContent(activeContent);
                while (DocNodeType.LITERA.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[0].replace(")", "");
                activeContent = line;
                node = new DocNode(DocNodeType.LITERA, id, node, activeContent);
                node.getParent().addToList(node);
            } else activeContent = activeContent + "\r\n" + line;
        }
        node.setContent(activeContent);
        String tmp = root.getContent();
        root.setContent(root.getList().get(0).getContent());
        root.getList().get(0).setContent(tmp);
        if(isTOC  && tmp.startsWith("\r\nW trosce"))
            root.getList().get(0).setContent("\t\tPREAMBUŁA");
        return root;
    }
}