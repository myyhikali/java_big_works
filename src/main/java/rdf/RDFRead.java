package rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

public class RDFRead {

    private String fileName;
    private Model model;


    public RDFRead(String filename){
        if(filename == null)
            this.fileName = "data/example.rdf";
        else
            this.fileName = filename;
        this.model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(fileName);

        if (in == null) {
            throw new IllegalArgumentException("File:" + fileName + " not found");
        }
        model.read(in, "", "Turtle");
    }

    public Model getModel(){
        return this.model;
    }



    public static void main(String []args){

        Model model = new RDFRead("./tmp/rdf/text.rdf").getModel();

        model.write(System.out);
    }
}
