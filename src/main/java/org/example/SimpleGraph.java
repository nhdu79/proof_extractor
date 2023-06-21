package org.example;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;

public class SimpleGraph {
    private JSONArray edges;
    private JSONArray vertices;

    public SimpleGraph(String path) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject jo = (JSONObject) obj;
        this.edges = (JSONArray) jo.get("edges");
        this.vertices = (JSONArray) jo.get("full_vertices");
    }

    public JSONArray getEdges() {
        return edges;
    }

    public JSONArray getVertices() {
        return vertices;
    }

    public String getDescription(int idx) {
        return (String) vertices.get(idx);
    }

    public Long getConclusion() {
        Object edge = edges.get((edges.size()-1));
        JSONArray edgeArray = (JSONArray) edge;
        return (Long) edgeArray.get(2);
    }
}
