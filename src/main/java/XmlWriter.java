import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



public class XmlWriter {


    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("->This is an international university app");
        System.out.println("->This app stores students data.");
        System.out.println("->Use the following menu to add, modify, or delete students info: ");
        System.out.println();


        String filepath = "src/main/resources/users.xml";
        ArrayList<User> users = XmlReader.readUsersFromXml(filepath);
        int choice = -1;
        while (choice != 0) {
            System.out.println("1 - List of international students\r\n2 - Add new student\r\n3 - Modify student info\r\n" +
                                "4 - Delete student info\r\n0 - Exit");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 0 || 4 < choice) {
                    System.out.println("Not valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Not valid option.");
                scanner.nextLine();
            }
            switch (choice) {
                case 1 -> System.out.println(users);
                case 2 -> addNewUser(users);
                case 3 -> modifyUser(users);
                case 4 -> deleteUser(users);
            }
        }
        saveUsersToXml(users, filepath);
    }

    private static void addNewUser(ArrayList<User> users) {
        System.out.print("Enter name of student: ");
        String name = scanner.nextLine();
        int grade = readGrade();
        System.out.print("Enter nationality of student: ");
        String nationality = scanner.nextLine();
        Race race = readRace();
        users.add(new User(name,  grade, nationality, race));
    }

    private static void modifyUser(ArrayList<User> users) {
        User user = findUserIn(users);
        int grade = readGrade();
        System.out.print("Enter nationality of student: ");
        String nationality = scanner.nextLine();
        Race race = readRace();
        users.set(users.indexOf(user),
                  new User(user.getName(), grade, nationality, race));
    }

    private static void deleteUser(ArrayList<User> users) {
        users.remove(findUserIn(users));
    }

    private static User findUserIn(ArrayList<User> users) {
        User user = new User();
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter name of student: ");
            name = scanner.nextLine();
            for (User userElement : users) {
                if (userElement.getName().equals(name)) {
                    return userElement;
                }
            }
            name = "";
        }
        return user;
    }

    private static int readGrade() {
        int grade = 0;
        while (grade == 0){
            try {
                System.out.print("Enter grade of student: ");
                grade = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("This is not a grade. Please enter an integer.");
                scanner.nextLine();
            }
        }
        return grade;
    }

    private static Race readRace() {
        Race race = Race.ASIAN;
        String rawInput = "";
        while (rawInput.isEmpty()) {
            try {
                System.out.println("Enter the student's ethnicity: ");
                rawInput = scanner.nextLine();
                race = Race.valueOf(rawInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Not valid eye color.");
                rawInput = "";
            }
        }
        return race;
    }

    public static void saveUsersToXml(ArrayList<User> users, String filepath) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element rootElement = document.createElement("users");
            document.appendChild(rootElement);

            for (User user : users) {
                Element userElement = document.createElement("user");
                rootElement.appendChild(userElement);
                createChildElement(document, userElement, "name", user.getName());
                createChildElement(document, userElement, "grade", String.valueOf(user.getGrade()));
                createChildElement(document, userElement, "nationality", user.getNationality());
                createChildElement(document, userElement, "race", user.getRace().toString());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new FileOutputStream(filepath));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createChildElement(Document document, Element parent, String tagName, String value) {
        Element element = document.createElement(tagName);
        element.setTextContent(value);
        parent.appendChild(element);
    }

}
