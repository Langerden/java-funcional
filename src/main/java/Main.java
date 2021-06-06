import entity.IUser;
import entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        users.add(new User("Juan", 25));
        users.add(new User("Lucas", 50));
        users.add(new User("Lautaro", 15));
        users.add(new User("Pablo", 24));
        users.add(new User("Andres", 21));
        users.add(new User("Facundo", 31));

        //Count elements after filters
        long cant = users.stream()
                .filter(user -> user.getAge() <= 20)
                .filter(user -> user.getName().startsWith("L"))
                .count();
        System.out.println(cant);

        //Return result type of List => collect
        List<User> newUsers = users.stream()
                .filter(user -> user.getAge() < 25)
                .collect(Collectors.toList());
        System.out.println(newUsers);

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            numbers.add(i);
        }

        //Manipulate and change data from a list and return de result => map
        List<Integer> resultMap = numbers.stream()
                .map(num -> num * num)
                .collect(Collectors.toList());
        System.out.println("Map: " + resultMap);

        //Same logic => forEach
        numbers.stream()
                .map(num -> num * num)
                .forEach(num -> System.out.println("For Each: " + num));

        //Concat elements from lists => flatMap
        List<User> concatList = Stream.of(users, newUsers)
                .flatMap(element -> element.stream()) //Combinar colecciones
                .collect(Collectors.toList());
        System.out.println("FlatMap: " + concatList);

        //Search if any element matches with contidtion => anyMatch or firstMatch
        List<Integer> searchResult = new ArrayList<>(Arrays.asList(9, 4, 2, 6, 6, 6, 7, 8, 9, 10, 10, 1));
        boolean result = searchResult.stream()
                .anyMatch(cal -> cal < 6);
//                .filter(num -> num < 6).count() > 0;
        System.out.println("Any Match Cal < 6: " + result);

        //Return the value of the list with filter condition or a default value => .get() orElse()
        User findAnyResult = users.stream()
                .filter(user -> user.getAge() > 70)
                .findAny() //or findFirst
                .orElse(new User("NoName", 12));
//                .get(); //Return the value. If value doesn't exist, throw Exception
        System.out.println("Filter > 70: " + findAnyResult);

        //Math Operations
        System.out.println("Cantidad total de elementos: " + numbers.stream().count());
        System.out.println("Suma total de elementos: " + numbers.stream().mapToInt(n -> n).sum());
        System.out.println("Promedio: " + numbers.stream().mapToInt(n -> n).average().orElse(0));
        System.out.println("Min value: " + numbers.stream().mapToInt(n -> n).min().getAsInt());
        System.out.println("Max value: " + numbers.stream().mapToInt(n -> n).max().getAsInt());

        //Generate a unique value from a collection => Reduce
        //Reduce (value of acumulador, lambda expression (param 1 = acumulador, param2 = element))
        System.out.println(Stream.of(5, 7, 9, 22, 5, 12, 4, 2)
                .reduce(0, (acumulador, element) -> acumulador + element));
//        System.out.println(Stream.of("Java", "C#", "Python").reduce("", (acum, language) -> acum + language + "|" ));
        System.out.println(Stream.of("Java", "C#", "Python").reduce("", (acum, leng) -> acum.equals("") ? leng : acum + " | " + leng));

        //Work with unique elements => distinct
        List<String> names = new ArrayList<>(Arrays.asList("Dense", "Facu", "Mel", "Dense", "Juan", "Mel"));
        names.stream().distinct().forEach(s -> System.out.println(s));

        //Order
        System.out.println("List ASC: " + searchResult.stream().sorted().collect(Collectors.toList()));
        System.out.println("List DESC: " + searchResult.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));

        System.out.println("Order by Name: " + users.stream().sorted(Comparator.comparing(user -> user.getName())).collect(Collectors.toList()));
        System.out.println("Order by Age: " + users.stream().sorted(Comparator.comparing(user -> user.getAge())).collect(Collectors.toList()));
        System.out.println("Limit: " + users.stream().sorted(Comparator.comparing(user -> user.getName())).limit(2).collect(Collectors.toList()));

        //Saltear u omitir elementos
        System.out.println("Skip: " + users.stream().sorted(Comparator.comparing(user -> user.getName())).skip(3).collect(Collectors.toList()));

        //METODOS POR REFERENCIA

        //Estaticos
        numbers.stream().map(Main::calculateCube).forEach(System.out::println);
        System.out.println("-------------------------------------------------");

        //De Instancia
        User usuario = new User();
        numbers.stream().map(usuario::sumHundred).forEach(System.out::println);
        System.out.println("-------------------------------------------------");

        //De Instancia de forma arbitraria
        users.stream().map(User::getName).sorted().forEach(System.out::println);
        System.out.println("-------------------------------------------------");

        //Constructor
        IUser iUser = User::new;
        users.add(iUser.createEntity("Denu", 25));
        users.add(iUser.createEntity("Pedro", 35));
        users.stream().forEach(System.out::println);
    }

    public static double calculateCube(int number) {
        return Math.pow(number, 3);
    }
}
