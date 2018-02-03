package parser;

public class Interpreter {

    public Interpreter() {

    }

    public DocNode makeDoc(String text) {
        DocNode root = new DocNode(DocNodeType.ROOT, null, null, null);
        DocNode node = root;
        String activeContent = new String();
        String id = new String();

        for (String line : text.replaceAll("\n", "#").split("#")) {

            if (line.matches("Art\\. .*")) {
                node.setContent(activeContent);
                while (DocNodeType.ARTYKUL.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                String[] lineContent = line.split(" ");
                id = lineContent[1].replace(".", "");
                if (lineContent[2].equals("1.")) {
                    activeContent = lineContent[0] + lineContent[1];
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

            } else if (line.matches("DZIAŁ .*")) {
                node.setContent(activeContent);
                while (DocNodeType.DZIAL.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[1].replace(".", "");
                activeContent = line;
                node = new DocNode(DocNodeType.DZIAL, id, node, activeContent);
                node.getParent().addToList(node);

            } else if (line.matches("Rozdział .*") || line.matches("^ROZDZIAŁ .*")) {
                node.setContent(activeContent);
                while (DocNodeType.ROZDZIAL.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[1].replace(".", "");
                activeContent = line;
                node = new DocNode(DocNodeType.ROZDZIAL, id, node, activeContent);
                node.getParent().addToList(node);

            } else if (line.matches("[0-9]{1,2}[a-z]{0,1}\\..*")) {
                node.setContent(activeContent);
                while (DocNodeType.USTEP.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[0].replace(".", "");
                activeContent = line;
                node = new DocNode(DocNodeType.USTEP, id, node, activeContent);
                node.getParent().addToList(node);

            } else if (line.matches("[0-9]{1,2}[a-z]{0,1}\\).*")) {
                node.setContent(activeContent);
                while (DocNodeType.PUNKT.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[0].replace(")", "");
                activeContent = line;
                node = new DocNode(DocNodeType.PUNKT, id, node, activeContent);
                node.getParent().addToList(node);

            } else if (line.matches("[a-z]\\).*")) {
                node.setContent(activeContent);
                while (DocNodeType.LITERA.getPriority() >= node.getType().getPriority())
                    node = node.getParent();

                id = line.split(" ")[0].replace(")", "");
                activeContent = line;
                node = new DocNode(DocNodeType.LITERA, id, node, activeContent);
                node.getParent().addToList(node);
            } else activeContent = activeContent + "\n" + line;
        }
        node.setContent(activeContent);
        return root;
    }
}