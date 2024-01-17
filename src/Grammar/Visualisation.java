package Grammar;

import java.util.HashMap;
import java.util.Map;

public class Visualisation {

    private static StringBuilder dot = new StringBuilder();
    private static Map<Node, Integer> nodeIdMap = new HashMap<>();
    private static int nodeIdCounter = 0;

    private static final String[] IGNORE_NODES = {"with", "Ada", ".", "Text_IO", "use"};

    public static String visualize(Node root) {
        dot = new StringBuilder();
        nodeIdMap.clear(); // Réinitialisation de la map d'identifiants de nœuds
        nodeIdCounter = 0; // Réinitialisation du compteur d'identifiants
        dot.append("digraph G {\n");
        dot.append("  node [shape=box, style=filled, fillcolor=\"#EEEEEE\", color=\"#EEEEEE\", width=1.5, fontsize=20];\n");
        dot.append("  edge [color=\"#31CEF0\", arrowhead=vee, arrowsize=0.8, fontname=\"Sans\"];\n");

        traverse(root, 0); // Passer le niveau initial

        // Ajout des déclarations de nœuds après la traversée
        for (Map.Entry<Node, Integer> entry : nodeIdMap.entrySet()) {
            Node node = entry.getKey();
            int nodeId = entry.getValue();
            dot.append("  node_").append(nodeId).append(" [label=\"").append(node.getRule()).append("\", shape=box");
            // Ajoutez ici toute logique supplémentaire pour ajuster la couleur, la taille, etc.
            dot.append("];\n");
        }

        dot.append("}\n");
        System.out.println(dot.toString());
        return dot.toString();
    }

    private static void traverse(Node node, int level) {
        if (node != null && node.getChildren() != null) {
            String parentNode = getNodeId(node);
            for (Node child : node.getChildren()) {
                if (child != null && !isNodeIgnored(child)) {
                    String childNode = getNodeId(child);
                    dot.append("  \"").append(parentNode).append("\" -> \"").append(childNode).append("\";\n");
                    traverse(child, level + 1);
                }
            }
        }
    }

    private static String getNodeId(Node node) {
        if (!nodeIdMap.containsKey(node)) {
            nodeIdMap.put(node, nodeIdCounter++);
        }
        return "node_" + nodeIdMap.get(node);
    }

    private static boolean isNodeIgnored(Node node) {
        String rule = node.getRule();
        for (String ignoredNode : IGNORE_NODES) {
            if (rule.equals(ignoredNode)) {
                return true;
            }
        }
        return false;
    }
}
