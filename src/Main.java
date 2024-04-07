import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Runnable logic = () -> {
                String route = generateRoute("RLRFR", 100);
                int freqOfR = 0;
                for (int j = 0; j < route.length(); j++) {
                    if ('R' == (route.charAt(j))) {
                        freqOfR++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (!sizeToFreq.containsKey(freqOfR)) {
                        sizeToFreq.put(freqOfR, 1);
                    } else {
                        sizeToFreq.put(freqOfR, sizeToFreq.get(freqOfR) + 1);
                    }
                }
            };
            Thread thread = new Thread(logic);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int theMostRepeatedQuantity = sizeToFreq.values().stream().max(Integer::compareTo).get();
        int theMostRepeatedValue = sizeToFreq.keySet().stream().filter(x -> sizeToFreq.get(x) == theMostRepeatedQuantity).findAny().get();

        System.out.printf("Самое частое количество повторений %d (встретилось %d раз(а))\n", theMostRepeatedValue, theMostRepeatedQuantity);
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> map : sizeToFreq.entrySet()) {
            if (map.getKey() != theMostRepeatedValue) {
                System.out.printf("- %d (%d раз(а))\n", map.getKey(), map.getValue());
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}