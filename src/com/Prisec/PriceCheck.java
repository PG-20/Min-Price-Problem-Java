package com.Prisec;

import java.util.*;

public class PriceCheck {
    private TreeMap<String, TreeMap<Double, String>> prefixMap = new TreeMap<>();
    private TreeMap<Double, String> innerMap;
    private HashSet<String> companies = new HashSet<>(Arrays.asList("A", "B", "C"));

    public PriceCheck(){
//        UNCOMMENT TO ADD SOME INITIAL VALUES TO MAP
//        innerMap = new TreeMap<>();
//        innerMap.put(1.0, "A");
//        innerMap.put(0.7, "B");
//        innerMap.put(0.5, "C");
//        prefixMap.put("125", innerMap);
//
//        innerMap = new TreeMap<>();
//        innerMap.put(1.8, "A");
//        innerMap.put(1.7, "B");
//        prefixMap.put("1", innerMap);
//
//        innerMap = new TreeMap<>();
//        innerMap.put(0.3, "A");
//        innerMap.put(1.1, "C");
//        prefixMap.put("12", innerMap);
//
//        innerMap = new TreeMap<>();
//        innerMap.put(2.7, "B");
//        innerMap.put(0.6, "C");
//        prefixMap.put("49", innerMap);
//
//        innerMap = new TreeMap<>();
//        innerMap.put(0.4, "A");
//        innerMap.put(1.4, "B");
//        innerMap.put(1.5, "C");
//        prefixMap.put("99", innerMap);
//
//        innerMap = new TreeMap<>();
//        innerMap.put(2.2, "B");
//        innerMap.put(0.45, "C");
//        prefixMap.put("999", innerMap);
    }

    public PriceCheck(TreeMap<String, TreeMap<Double, String>> preLoaded){
        this.prefixMap.putAll(preLoaded);
    }

    public TreeMap<String, TreeMap<Double, String>> getPrefixMap() {
        return this.prefixMap;
    }

    public HashSet<String> getCompanies() {
        return this.companies;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            String[] command = sc.nextLine().split(" ");
            if (command[0].equals("INSERT")) {
                this.insert(command);
            }
            else if (command[0].equals("QUERY")) {
                String[] queryResult = this.query(command[1]);
                System.out.println(command[1] + (queryResult.length == 0
                        ? " NA"
                        : " " + queryResult[0] + " " + queryResult[1] + " " + queryResult[2]
                ));
            }
        }
    }

    public void insert(String[] command){
        innerMap = this.prefixMap.get(command[2]) == null
                ? new TreeMap<>()
                : this.prefixMap.get(command[2]);
        innerMap.put(Double.parseDouble(command[3]), command[1]);
        this.companies.add(command[1]);
        this.prefixMap.put(command[2], innerMap);
    }

    public String[] query(String phone) {
        SortedMap<String, Double> track = new TreeMap<>();
        TreeMap<Double, String[]> minPriceMap = new TreeMap<>();
        if (this.prefixMap.isEmpty()) return new String[]{};    //Check for empty map
        for (Map.Entry<String, TreeMap<Double, String>> entry : this.prefixMap.subMap(this.prefixMap.firstKey(), true, phone,true).descendingMap().entrySet()) {
            if (entry.getKey().startsWith(phone.substring(0,1))){
                if (phone.startsWith(entry.getKey())) {
                    entry.getValue().forEach((price, company) -> {
                        if (!track.containsKey(company)) {
                            String[] pair = new String[]{company, entry.getKey()};
                            track.put(company, price);
                            minPriceMap.put(price, pair);
                        }
                    });
                }
            }
            else break;
            if (this.companies.size() == track.size()) break;
        }
        Map.Entry<Double, String[]> res = minPriceMap.firstEntry();
        return res == null
                ? new String[]{}
                : new String[]{res.getValue()[0], res.getValue()[1], res.getKey().toString()};
    }
}

