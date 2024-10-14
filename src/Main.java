import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        Callable<Void> counter1 = () -> {
            Reader.calculate("src/text.txt");
            return null;
        };
        Callable<Void> counter2 = () -> {
            Reader.calculate("src/text1.txt");
            return null;
        };
        Callable<Void> counter3 = () -> {
            Reader.calculate("src/text2.txt");
            return null;
        };

        ExecutorService es = Executors.newFixedThreadPool(3);

        List<Future<Void>> futures = new ArrayList<>();

        futures.add(es.submit(counter1));
        futures.add(es.submit(counter2));
        futures.add(es.submit(counter3));


        for(Future<Void> future: futures){
            while (!future.isDone()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        es.shutdown();
        System.out.println(wordCount.toString());
    }
}