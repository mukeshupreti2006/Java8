package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class AllInOne {
    static List<Transaction> transactions;


    static {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 1500, "Bombay", "INRB"));
        transactions.add(new Transaction(2, 500, "Delhi", "INRD"));
        transactions.add(new Transaction(3, 1210, "Bangalore", "INRBA"));
        transactions.add(new Transaction(3, 110, "Lucknow", "INRBA"));
        transactions.add(new Transaction(3, 900, "Mumbai", "INRBA"));
    }


    public static void main(String[] args) {

        findingMaxTransaction();

        buildingStreams();


        CollectorsMethods();


        limitFunction();

        reduceMethod();


        ifPresentOfOptional();


        maptoIntFunction();

        letterCounter();

        uniqueWordInFile();

        expensiveTransaction();

        getExpensivecity();

        getExpensivecityV2();

        howManyTransactions();

        totalValue();

        averaging();

        highestTransaction();

        reducing();


    }

    private static void CollectorsMethods() {

        System.out.println("CollectorsMethods");
        Set<String> cities =
                transactions.stream()
                        .filter(t -> t.getValue() > 1000)
                        .map(Transaction::getCity)
                        .collect(toSet());
        System.out.println("expesive city >1000 -->" + cities);

        Set<String> cities1 =
                transactions.stream()
                        .filter(t -> t.getValue() > 1000)
                        .map(Transaction::getCity)
                        .collect(Collectors.toCollection(HashSet::new));
        System.out.println("expesive city >1000 with hashset -->" + cities1);

        Map<String, List<Transaction>> transactionsByCurrencies =
                transactions.stream().collect(groupingBy(
                        Transaction::getCurrency));
        System.out.println("transactionsByCurrencies " + transactionsByCurrencies);

        Map<Boolean, List<Transaction>> expesiveAndCheep = transactions.stream().collect(Collectors.partitioningBy(x -> x.getValue() > 1000));
        System.out.println(" divide list of transaction by partitionby  " + expesiveAndCheep);

        // sum of trainsection value by each city
        Map<String, Integer> cityToSum =
                transactions.stream().collect(groupingBy(
                        Transaction::getCity, summingInt(Transaction::getValue)));
        System.out.println(" each city transaction sum " + cityToSum);

        Map<String, Optional<Transaction>> cityToHighestTransaction =
                transactions.stream().collect(groupingBy(
                        Transaction::getCurrency, maxBy(Comparator.comparing(Transaction::getValue))));
        System.out.println(cityToHighestTransaction);
        System.out.println(" each city hightest trasaction " + cityToSum);

        System.out.println("EXCITING");
        Map<String, Map<String, Double>> cityByCurrencyToAverage =
                transactions.stream().collect(groupingBy(Transaction::getCity,
                        groupingBy(Transaction::getCurrency,
                                averagingInt(Transaction::getValue))
                ));
        System.out.println(cityByCurrencyToAverage);

        System.out.println(" each city average trasaction " + cityToSum);

        System.out.println("*****************");
    }

    private static void buildingStreams() {
        System.out.println("buildingStreams");

        //finite Streams
        Stream<Integer> numbersFromValues = Stream.of(1, 2, 3, 4);
        int[] numbers = {1, 2, 3, 4};
        IntStream numbersFromArray = Arrays.stream(numbers);

        // infinite streams . you cna limit by limit method

        Stream<Integer> numbers1 = Stream.iterate(0, n -> n + 10);

        // infinite loop
        // numbers1.collect(Collectors.toList());


        numbers1.limit(10).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("_________________________");
    }

    private static void findingMaxTransaction() {
        System.out.println("findingMaxTransaction");
        transactions.stream().max(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getValue() - o2.getValue();
            }
        }).ifPresent(System.out::println);

        transactions.stream().max(Comparator.comparing(Transaction::getValue)).ifPresent(System.out::println);
        System.out.println("******************************");
    }

    private static void ifPresentOfOptional() {
        System.out.println("limitFunction");
        transactions.stream()
                .filter(t -> t.getCity() == "Bombay")
                .findAny()
                .ifPresent(System.out::println);
    }

    private static void maptoIntFunction() {
        System.out.println("limitFunction");
        int statementSum =
                transactions.stream()
                        .mapToInt(Transaction::getValue)
                        .sum(); //works!
        System.out.println(statementSum);

        /*int statement =
                transactions.stream()
                        .map(Transaction::getValue)
                        .sum(); error since Stream has no sum method*/

        System.out.println("_________________________");
    }

    private static void reduceMethod() {
        System.out.println("limitFunction");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        int product = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println(product);
        int product2 = numbers.stream().reduce(1, Integer::max);
        System.out.println(product2);

        //max element

        int max = numbers.stream().reduce(1, Integer::max);
        System.out.println(max);

        System.out.println("_________________________");
    }

    private static void limitFunction() {
        System.out.println("limitFunction");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        numbers.stream()
                .filter(n -> {
                    System.out.println("filtering " + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    System.out.println("mapping " + n);
                    return n * n;
                })
                .limit(2)
                .forEach(System.out::println);

        System.out.println("_________________________");
    }


    //**flat map
    public static void letterCounter() {
        System.out.println("expensiveTransaction");

        Stream<String> words = Stream.of("Java", "Magazine", "is", "the", "best");

        Map<String, Long> letterToCount = words.map(w -> w.split(""))
                .flatMap(Arrays::stream)
                .collect(groupingBy(Function.identity(), Collectors.counting()));

        System.out.println(letterToCount);
        System.out.println("_________________________");

    }


    //flatMap
    public static void uniqueWordInFile() {
        System.out.println("expensiveTransaction");

        try {
            Files.lines(Paths.get("/Users/mukesh.upreti/Google Drive/Java8/src/streams/stuff.txt"))
                    .map(line -> line.split("\\s+"))// Stream<String[]>
                    .distinct().flatMap(Arrays::stream) //Stream<String[]>
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("_________________________");

    }


    // to get list of all expensive transaction

    public static void expensiveTransaction() {
        System.out.println("expensiveTransaction");


        transactions.stream()
                .filter(t -> t.getValue() > 1000)
                .map(Transaction::getId)
                .collect(Collectors.toList())
                .forEach(System.out::println);


        System.out.println("_________________________");


    }

    /**
     * collect operation
     */

    public static void getExpensivecity() {
        System.out.println("getExpensivecity");
        transactions.stream()
                .filter(t -> t.getValue() > 1000)
                .map(Transaction::getCity)
                .collect(Collectors.toSet())
                .forEach(System.out::println);
        System.out.println("_________________________");
    }

    /**
     * However, using toCollection() you can have more control.
     */

    public static void getExpensivecityV2() {
        System.out.println("getExpensivecityV2");
        transactions.stream()
                .filter(t -> t.getValue() > 1000)
                .map(Transaction::getCity)
                .collect(Collectors.toCollection(HashSet::new))
                .forEach(System.out::println);
        System.out.println("_________________________");
    }

    private static void howManyTransactions() {
        System.out.println("howManyTransactions");
        System.out.println(transactions.stream().collect(Collectors.counting()));
        System.out.println("_________________________");

    }

    /**
     * summing
     * <p>
     * can use summing Double(), summingInt(), and summingLong() to sum the values of a Double, an Int, or a Long property of the elements in a stream
     */
    private static void totalValue() {

        System.out.println("totalValue");

        System.out.println(transactions.stream().collect(Collectors.summingInt(Transaction::getValue)));
        System.out.println("_________________________");
    }


    /**
     * can use averaging Double(), averagingInt(), and averagingLong() to calculate the average,
     */

    private static void averaging() {
        System.out.println("averaging");

        System.out.println(transactions.stream().collect(Collectors.averagingInt(Transaction::getValue)));
        System.out.println("_________________________");
    }

    private static void reducing() {
        System.out.println("reducing");

        System.out.println(transactions.stream().collect(Collectors.reducing(0, Transaction::getValue, Integer::sum)));
        System.out.println("_________________________");
    }

    /**
     * we use the static method comparing(), which generates a Comparator object from a function passed as an argument.
     * The function is used to extract a comparable key from the element of a stream.
     */

    private static void highestTransaction() {
        System.out.println("highestTransaction");

        Optional<Transaction> highestTransaction = transactions.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Transaction::getValue)));
        System.out.println(highestTransaction.get());

        Optional<Transaction> minTransaction = transactions.stream()
                .collect(Collectors.minBy(Comparator.comparing(Transaction::getValue)));
        System.out.println(minTransaction.get());
        System.out.println("_________________________");

    }


}
