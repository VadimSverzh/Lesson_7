import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args)
    {
        ArrayList<Employee> staff = loadStaffFromFile();

        Collections.sort(staff, (o1, o2) -> {if (o1.getSalary().compareTo(o2.getSalary()) == 0)
            return  (o1.getName().compareTo(o2.getName()));
        else return o1.getSalary().compareTo(o2.getSalary());});

        for (Employee employee: staff) {
            System.out.println(employee);
        }
        System.out.println();
        staff.stream().filter(e -> convertToLocalDate(e.getWorkStart()).getYear() == 2017)
                      .max(Comparator.comparing(Employee::getSalary))
                      .ifPresent(System.out::println);
    }

    private static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}