package org.example;

import de.tu_dresden.inf.lat.evee.general.data.exceptions.FormattingException;
import de.tu_dresden.inf.lat.evee.general.data.exceptions.ParsingException;
import de.tu_dresden.inf.lat.evee.proofs.data.Inference;
import de.tu_dresden.inf.lat.evee.proofs.data.Proof;
import de.tu_dresden.inf.lat.evee.proofs.data.exceptions.ProofGenerationFailedException;
import de.tu_dresden.inf.lat.evee.proofs.interfaces.IProof;
import de.tu_dresden.inf.lat.evee.proofs.json.JsonProofWriter;
import de.tu_dresden.inf.lat.evee.proofs.tools.MinimalProofExtractor;
import de.tu_dresden.inf.lat.evee.proofs.tools.measures.TreeSizeMeasure;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.LinkedList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void ProofExtractor(SimpleGraph graph) throws ProofGenerationFailedException, FormattingException {
        JSONArray edges = graph.getEdges();
        //edges.remove(edges.size()-1);
        Long conclusion = graph.getConclusion();
        IProof<Long> proof = new Proof<>(conclusion);

        for (Object edge : edges) {
            JSONArray e = (JSONArray) edge;
            LinkedList<Long> premises = new LinkedList<>();
            JSONArray premisesArray = (JSONArray) e.get(0);
            for (Object premise : premisesArray) {
                premises.add((Long) premise);
            }
            Inference<Long> inference = new Inference<>((Long) e.get(2), (String) e.get(1), premises);
            proof.addInference(inference);
        }
        IProof<Long> minTree = new MinimalProofExtractor<>(new TreeSizeMeasure<Long>()).extract(proof);
        JsonProofWriter writer = JsonProofWriter.getInstance();
        System.out.println("TREE:\n" + writer.toString(minTree));
    }

    public static void main(String[] args) throws FormattingException, ProofGenerationFailedException, ParsingException, IOException, ParseException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it
        // run the program with path from args
        String path = args[0];
        SimpleGraph graph = new SimpleGraph(path);
        ProofExtractor(graph);
    }
}