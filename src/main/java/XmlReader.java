import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;



public class XmlReader {

    public static ArrayList<User> readUsersFromXml(String filepath) {
        ArrayList<User> users = new ArrayList<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new FileInputStream(filepath));

            Element rootElement = document.getDocumentElement();


            NodeList childsOfRootElement = rootElement.getChildNodes();

            for (int i = 0; i < childsOfRootElement.getLength(); i++) {
                Node childNode = childsOfRootElement.item(i);

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childsOfUserTag = childNode.getChildNodes();
                    String name = "";
                    int grade = 0;
                    String nationality = "";
                    Race race = Race.ASIAN;
                    for (int j = 0; j < childsOfUserTag.getLength(); j++) {
                        Node childNodeOfUserTag = childsOfUserTag.item(j);
                        if (childNodeOfUserTag.getNodeType() == Node.ELEMENT_NODE) {
                            switch (childNodeOfUserTag.getNodeName()) {
                                case "name" -> name = childNodeOfUserTag.getTextContent();
                                case "grade" -> grade = Integer.parseInt(childNodeOfUserTag.getTextContent());
                                case "nationality" -> nationality = childNodeOfUserTag.getTextContent();
                                case "race" -> race = Race.valueOf(childNodeOfUserTag.getTextContent());
                            }
                        }
                    }
                    users.add(new User(name, grade, nationality, race));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


}
