import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.log4j.BasicConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {


    public static String ns = "http://somewhere#";
    public static String foafNS = "http://xmlns.com/foaf/0.1#";
    public static String foafEmailURI = foafNS+"email";
    public static String foafKnowsURI = foafNS+"knows";
    public static String stringTypeURI = "http://www.w3.org/2001/XMLSchema#string";

    public static void main(String[] args) throws FileNotFoundException {

        BasicConfigurator.configure();
        String filename = "example6.rdf";

        // Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

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
        OntClass battery = model.createClass(ns + "Battery");

        //Subclass
        OntClass type = model.createClass(ns+ "DisplayType");
        OntClass inches = model.createClass(ns + "Inches");
        OntClass resolution = model.createClass(ns + "Resolution");
        OntClass phone_model = model.createClass(ns + "Model");
        OntClass front_camera = model.createClass(ns+ "Front_Camera");
        OntClass back_camera = model.createClass(ns + "Back_Camera");
        OntClass ram = model.createClass(ns + "Ram");
        OntClass storage = model.createClass(ns + "Storage");
        OntClass LiOn = model.createClass(ns + "LiOn");
        OntClass LiPo = model.createClass(ns + "LiPo");

        // Add Subclassees to Class
        model.getOntClass(ns + "Display").addSubClass(type);
        model.getOntClass(ns + "Display").addSubClass(inches);
        model.getOntClass(ns + "Display").addSubClass(resolution);
        model.getOntClass(ns + "Producent").addSubClass(phone_model);
        model.getOntClass(ns + "Camera").addSubClass(front_camera);
        model.getOntClass(ns + "Camera").addSubClass(back_camera);
        model.getOntClass(ns + "Memory").addSubClass(ram);
        model.getOntClass(ns + "Memory").addSubClass(storage);
        model.getOntClass(ns + "Battery").addSubClass((LiOn));
        model.getOntClass(ns + "Battery").addSubClass((LiPo));

        //Property

        // Display property
        ObjectProperty hasDisplay = model.createObjectProperty(ns + "hasDisplay");
        ObjectProperty hasDisplayInches = model.createObjectProperty(ns+"hasDisplayInches");
        ObjectProperty hasDisplayType = model.createObjectProperty(ns + "hasDisplayType");
        ObjectProperty hasDisplayResolution = model.createObjectProperty(ns + "hasDisplayResolution");
        model.getObjectProperty(ns + "hasDisplay").addSubProperty(hasDisplayInches);
        model.getObjectProperty(ns + "hasDisplay").addSubProperty(hasDisplayType);
        model.getObjectProperty(ns + "hasDisplay").addSubProperty(hasDisplayResolution);

        ObjectProperty hasProducent = model.createObjectProperty(ns+ "hasProducent");
        ObjectProperty hasFrontCamera = model.createObjectProperty(ns + "hasFrontCamera");
        ObjectProperty hasBackCamera = model.createObjectProperty(ns + "hasBackCamera");
        ObjectProperty hasSystem = model.createObjectProperty(ns + "hasSystem");
        ObjectProperty hasProcessor = model.createObjectProperty((ns + "hasProcessor"));
        ObjectProperty hasMemory = model.createObjectProperty(ns + "hasMemory");
        ObjectProperty hasSim = model.createObjectProperty(ns + "hasSim");
        ObjectProperty hasWeight = model.createObjectProperty(ns + "hasWeight");
        ObjectProperty hasGps = model.createObjectProperty(ns + "hasGps");
        ObjectProperty hasBluetooth = model.createObjectProperty(ns + "hasBluetooth");

        //Battery property
        ObjectProperty hasBattery = model.createObjectProperty(ns + "hasBattery");
        ObjectProperty hasLiOn= model.createObjectProperty( ns + "hasLiOn");
        ObjectProperty hasLiPo= model.createObjectProperty(ns+ "hasLiPo");
        model.getObjectProperty(ns+ "hasBattery").addSubProperty(hasLiOn);
        model.getObjectProperty(ns+ "hasBattery").addSubProperty(hasLiPo);

        //Datatype

        DatatypeProperty BatterySize  = model.createDatatypeProperty(ns+ "BatterySize");
        DatatypeProperty Inches  = model.createDatatypeProperty(ns+ "Inches");
        DatatypeProperty Resolution  = model.createDatatypeProperty(ns+ "Resolution");
        DatatypeProperty DisplayType  = model.createDatatypeProperty(ns+ "DisplayType");
        DatatypeProperty MemorySize  = model.createDatatypeProperty(ns+ "MemorySize");
        DatatypeProperty RAMSize  = model.createDatatypeProperty(ns+ "RAMSize");


        Individual Ascend_P2 = model.createIndividual(ns + "Ascend_P2", phone);
        Ascend_P2.addProperty(MemorySize, model.createTypedLiteral(200));

        Individual LiOn_2000 = model.createIndividual(ns + "LiOn_2000", LiOn);
        LiOn_2000.addProperty(BatterySize, model.createTypedLiteral(2000));

        JSONParser parser = new JSONParser();
        List<String> batteryList = new ArrayList<String>();
        List<String> bluetoothList = new ArrayList<String>();
        List<String> frontCameraList = new ArrayList<String>();
        List<String> backCameraList = new ArrayList<String>();
        List<String> displayList = new ArrayList<String>();
        List<String> brandList = new ArrayList<String>();
        List<String> processorList = new ArrayList<String>();
        List<String> sizeList = new ArrayList<String>();
        try {

            Object obj = parser.parse(new FileReader(
                    "phpnes.json"));

            JSONObject jsonObject = (JSONObject) obj;
            Iterator<JSONObject> iterator = jsonObject.values().iterator();
            JSONObject JSONObjectChild = new JSONObject();

            while (iterator.hasNext()) {
                JSONObjectChild = iterator.next();
                Iterator<JSONObject> iteratorChild = JSONObjectChild.values().iterator();


                JSONObject JSONPhone = new JSONObject();
                while (iteratorChild.hasNext()){
                    JSONPhone = iteratorChild.next();
                    Individual bluetoothTemp ;
                    Individual brandTemp ;
                    Individual frontCameraTemp;
                    Individual backCameraTemp;
                    Individual batteryTemp;
                    Individual processorTemp;
                    Individual sizeTemp;

                    String name = JSONPhone.get("DeviceName").toString();
                    Individual temp = model.createIndividual(ns + name.replace(" ", "_") , phone);

                    if (JSONPhone.containsKey("bluetooth")) {
                        if (!bluetoothList.contains(JSONPhone.get("bluetooth").toString())) {
                            bluetoothList.add(JSONPhone.get("bluetooth").toString());
                            bluetoothTemp = model.createIndividual(ns + JSONPhone.get("bluetooth").toString().replace(" ", "_"), bluetooth);
                            temp.addProperty(hasBluetooth, bluetoothTemp);
                        } else {

                            temp.addProperty(hasBluetooth, model.getIndividual(ns + JSONPhone.get("bluetooth").toString().replace(" ", "_")));
                        }
                    }
                    if (JSONPhone.containsKey("Brand")) {
                        if (!brandList.contains(JSONPhone.get("Brand").toString())) {
                            brandList.add(JSONPhone.get("Brand").toString());
                            brandTemp = model.createIndividual(ns + JSONPhone.get("Brand").toString().replace(" ", "_"), producent);
                            temp.addProperty(hasProducent, brandTemp);
                        } else {

                            temp.addProperty(hasProducent, model.getIndividual(ns + JSONPhone.get("Brand").toString().replace(" ", "_")));
                        }
                    }
                    if (JSONPhone.containsKey("primary_")) {
                        System.out.println( "jestem w primary");
                        String mp="" ;
                        Pattern pattern = Pattern.compile("([0-9]+(\\.[0-9]+)? MP).*");
                        String tempfront = JSONPhone.get("primary_").toString();
                        Matcher matcher = pattern.matcher(tempfront);

                        if(matcher.find()){
                            mp = matcher.group(1);
                        }
                        else {
                            if(tempfront.equals("No"))
                                mp = "No";
                            else
                                mp = "Yes";
                        }

                        if (!frontCameraList.contains(JSONPhone.get("primary_").toString())) {
                            frontCameraList.add(JSONPhone.get("primary_").toString());
                            frontCameraTemp = model.createIndividual(ns + mp.replace(".", "_").replace(" ", "") , front_camera);
                            temp.addProperty(hasFrontCamera, frontCameraTemp);
                        } else {

                            temp.addProperty(hasFrontCamera, model.getIndividual(ns+ mp.replace(".", "_").replace(" ", "")));
                        }
                    }
                    if (JSONPhone.containsKey("secondary")) {
                        String mp="";
                        Pattern pattern = Pattern.compile("([0-9]+(\\.[0-9]+)? MP).*");
                        String tempback = JSONPhone.get("secondary").toString();
                        Matcher matcher = pattern.matcher(tempback);

                        if(matcher.find()){
                            mp = matcher.group(1);
                        }
                        else {
                            if(tempback.equals("No"))
                                mp = "No";
                            else
                                mp = "Yes";
                        }

                        if (!backCameraList.contains(JSONPhone.get("secondary").toString())) {
                            backCameraList.add(JSONPhone.get("secondary").toString());
                            backCameraTemp = model.createIndividual(ns + mp.replace(".", "_").replace(" ", "") , back_camera);
                            temp.addProperty(hasBackCamera, backCameraTemp);
                        } else {

                            temp.addProperty(hasBackCamera, model.getIndividual(ns + mp.replace(".", "_").replace(" ", "")));
                        }
                    }
                    if (JSONPhone.containsKey("battery_c")){
                        String size = "";
                        Pattern pattern = Pattern.compile("[0-9]+");
                        String tempBattery = JSONPhone.get("battery_c").toString();
                        Matcher matcher = pattern.matcher(tempBattery);

                        if(matcher.find()){
                            size = matcher.group();
                        }
                        if(!batteryList.contains(JSONPhone.get("battery_c").toString())) {
                            batteryList.add(JSONPhone.get("battery_c").toString());



                            if(tempBattery.contains("Li-Po")){
                                batteryTemp = model.createIndividual(ns + "LiPo_" + size , LiPo);
                                temp.addProperty(hasLiPo, batteryTemp);
                            }
                            if(tempBattery.contains("Li-Ion")){
                                batteryTemp = model.createIndividual(ns + "LiOn_" + size , LiOn);
                                temp.addProperty(hasLiOn, batteryTemp);
                            }

                        }
                        else{
                            if(JSONPhone.get("battery_c").toString().contains("Li-Po")){
                                temp.addProperty(hasLiPo, model.getIndividual(ns + "LiPo_"+size));
                            }
                            if(JSONPhone.get("battery_c").toString().contains("Li-Ion")){
                                temp.addProperty(hasLiOn, model.getIndividual(ns + "LiOn_"+size));
                            }
                        }


                    }
                    if (JSONPhone.containsKey("chipset")){
                        String chipsetType = "";

                        chipsetType = JSONPhone.get("chipset").toString();

                        if(!processorList.contains(chipsetType)){
                            processorList.add(chipsetType);
                            processorTemp = model.createIndividual(ns + chipsetType.replace(" ", "_"), processor);
                            temp.addProperty(hasProcessor, model.getIndividual(ns + chipsetType.replace(" ", "_")) );
                        }
                        else {
                            temp.addProperty(hasProcessor, model.getIndividual(ns + chipsetType.replace(" ", "_")) );
                        }

                    }
                    if (JSONPhone.containsKey("size")){
                        String sizeInches = "";

                        sizeInches = JSONPhone.get("size").toString();

                        String[] splited = sizeInches.split("\\s+");

                        if(!sizeList.contains(sizeInches)){
                            sizeList.add(sizeInches);
                            sizeTemp = model.createIndividual(ns + splited[0].replace(" ", "_"), inches);
                            temp.addProperty(hasDisplayInches, model.getIndividual(ns + splited[0].replace(" ", "_")) );
                        }
                        else {
                            temp.addProperty(hasDisplayInches, model.getIndividual(ns + splited[0].replace(" ", "_")) );
                        }

                    }

                    System.out.println(name);
                }
                //DO what ever you whont with jsonChildObject


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        Ascend_P2.addProperty(hasBattery, LiOn_2000);

        FileOutputStream out = new FileOutputStream(filename);
        model.write(out);
    }
}
