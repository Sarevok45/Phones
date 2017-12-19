

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

    public static String ns = "http://somewhere#";
    public static String foafNS = "http://xmlns.com/foaf/0.1#";
    public static String foafEmailURI = foafNS+"email";
    public static String foafKnowsURI = foafNS+"knows";
    public static String stringTypeURI = "http://www.w3.org/2001/XMLSchema#string";

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello World!");
        BasicConfigurator.configure();
        String filename = "example5.rdf";

        // Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null)
            throw new IllegalArgumentException("File: "+filename+" not found");

        // Read the RDF/XML file
        //model.read(in, null);

        //Classes
        OntClass phone = model.createClass(ns+"Phone");
        OntClass display = model.createClass(ns + "Display");
        OntClass producent = model.createClass((ns + "Producent"));
        OntClass camera = model.createClass(ns + "Camera");
        OntClass system = model.createClass(ns + "System");
        OntClass processor = model.createClass(ns + "Processor");
        OntClass memory = model.createClass(ns + "Memory");
        OntClass sim = model.createClass(ns + "Sim");
        OntClass weight = model.createClass(ns + "Weight");
        OntClass gps = model.createClass(ns + "Gps");
        OntClass bluetooth = model.createClass(ns + "Bluetooth");

        //Subclass
        OntClass type = model.createClass(ns+ "DisplayType");
        OntClass inches = model.createClass(ns + "Inches");
        OntClass resolution = model.createClass(ns + "Resolution");
        OntClass phone_model = model.createClass(ns + "Model");
        OntClass front_camera = model.createClass(ns+ "Front_Camera");
        OntClass back_camera = model.createClass(ns + "Back_Camera");
        OntClass ram = model.createClass(ns + "Ram");
        OntClass storage = model.createClass(ns + "Storage");

        // Add Subclassees to Class
        model.getOntClass(ns + "Display").addSubClass(type);
        model.getOntClass(ns + "Display").addSubClass(inches);
        model.getOntClass(ns + "Display").addSubClass(resolution);
        model.getOntClass(ns + "Producent").addSubClass(phone_model);
        model.getOntClass(ns + "Camera").addSubClass(front_camera);
        model.getOntClass(ns + "Camera").addSubClass(back_camera);
        model.getOntClass(ns + "Memory").addSubClass(ram);
        model.getOntClass(ns + "Memory").addSubClass(storage);


        //Property

        Property hasDisplay = model.createProperty(ns + "hasDisplay");
        Property hasProducent = model.createProperty(ns+ "hasProducent");
        Property hasCamera = model.createProperty(ns + "hasCamera");
        Property hasSystem = model.createProperty(ns + "hasSystem");
        Property hasProcessor = model.createProperty((ns + "hasProcessor"));
        Property hasMemory = model.createProperty(ns + "hasMemory");
        Property hasSim = model.createProperty(ns + "hasSim");
        Property hasWeight = model.createProperty(ns + "hasWeight");
        Property hasGps = model.createProperty(ns + "hasGps");
        Property hasbluetooth = model.createProperty(ns + "hasBluetooth");


        FileOutputStream out = new FileOutputStream(filename);
        model.write(out);
    }
}
