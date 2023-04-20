package ru.netology;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        Runnable logic = () -> {
            String route = generateRoute("RLRFR", 100);
            int count = 0;
            for (int i = 0; i < route.length(); i++) {
                if (route.charAt(i) == 'R') {
                    count++;
                }
            }
            synchronized (sizeToFreq) {
                if (sizeToFreq.containsKey(count)) {
                    sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                } else {
                    sizeToFreq.put(count, 1);
                }
            }
        };

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(logic);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join(); // зависаем, ждём когда поток объект которого лежит в thread завершится
        }

        int sizeForMaxRepetitions = 0;
        int maxRepetitions = 0;
        for (Map.Entry<Integer, Integer> size : sizeToFreq.entrySet()) {
            if (size.getValue() > maxRepetitions) {
                maxRepetitions = size.getValue();
                sizeForMaxRepetitions = size.getKey();
            }
        }
        
        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)", sizeForMaxRepetitions, maxRepetitions);
        System.out.println();
        System.out.println("Другие значения");
        for (Map.Entry<Integer, Integer> size : sizeToFreq.entrySet()) {
            System.out.println("- " + size.getKey() + "(" + size.getValue() + ")");
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

