public class User {


    private String name;
    private int grade;
    private String nationality;
    private final Race race;

    public User() {
        this("John Wang", 2000, "Chinese", Race.ASIAN);
    }


    public User(String name, int grade, String nationality) {
        this.name = name;
        this.grade = grade;
        this.nationality = nationality;
        race = Race.ASIAN;
    }

    public User(String name, int grade, String nationality, Race race) {
        this.name = name;
        this.grade = grade;
        this.nationality = nationality;
        this.race = race;
    }

    public Race getRace() {
        return race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "\n"+name + " (" + grade + ", " + nationality + ", " + race.toString()  + ")";
    }

}
