package cynthianoel.mareu.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cynthianoel.mareu.model.Employee;

public abstract class DummyEmployeeGenerator {
    public static List<Employee> DUMMY_EMPLOYEES = Arrays.asList(
            new Employee(1, "Francis"+" R","francis"+"@lamzone.com", true),
            new Employee(2, "Alexandra"+" S","alexandra"+"@lamzone.com", true),
            new Employee(3, "Maxime"+" W", "maxime"+"@lamzone.com",true),
            new Employee(4,"Alex"+" H", "alex"+"@lamzone.com",true),
            new Employee(5,"Paul"+" Y", "paul"+"@lamzone.com",true),
            new Employee(6,"Viviane"+" L", "viviane"+"@lamzone.com",true),
            new Employee(7,"Amandine"+" H", "amandine"+"@lamzone.com",true),
            new Employee(8,"Luc"+" P", "luc"+"@lamzone.com",true),
            new Employee(9,"Cécilia"+" B", "cecilia"+"@lamzone.com",true),
            new Employee(10,"Lindsey"+" M", "lindsey"+"@lamzone.com",true),
            new Employee(11,"Céline"+" R", "celine"+"@lamzone.com",true),
            new Employee(12,"Minh"+" D", "minh"+"@lamzone.com",true),
            new Employee(13,"Danny"+" N", "danny"+"@lamzone.com",true),
            new Employee(14,"Karina"+" R", "karina"+"@lamzone.com",true),
            new Employee(15,"Jacques"+" N", "jacques"+"@lamzone.com",true),
            new Employee(16,"Pepe"+" N", "pepe"+"@lamzone.com",true),
            new Employee(18,"Filou"+" N", "filou"+"@lamzone.com",true),
            new Employee(17,"François"+" G", "francois"+"@lamzone.com",true),
            new Employee(19,"Dalale"+" R", "dalale"+"@lamzone.com",true),
            new Employee(20,"Laura"+" L", "laura"+"@lamzone.com",true),
            new Employee(21,"Alexis"+" H", "alexis"+"@lamzone.com",true),
            new Employee(22,"Paula"+" F", "paula"+"@lamzone.com",true),
            new Employee(23,"Jules"+" A", "jules"+"@lamzone.com",true),
            new Employee(24,"Jean"+" M", "jean"+"@lamzone.com",true),
            new Employee(25,"Cynthia"+" N", "cynthia"+"@lamzone.com",true)
            );

    static List<Employee> generateEmployees() {
        return new ArrayList<>(DUMMY_EMPLOYEES);
    }
}
