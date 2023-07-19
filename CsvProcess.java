import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * CsvProcess is the main class of processing
 */
class CsvProcess {
    ArrayList<Records> records = new ArrayList<>();
    HashSet<String> uniqueRegion = new HashSet<>();
    HashSet<Integer> year = new HashSet<>();
    HashMap<Integer, Integer> birthCountByYear = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> deathCountByYear = new HashMap<Integer, Integer>();

    int uniqueBirthCount, uniqueDeathCount;
    Constants constantsObj = new Constants();

    /**
     * @return return 1 on successful read of a file
     */
    public int readCsv() {
        try {
            String line = "", splitBy = ",";
            BufferedReader br = new BufferedReader(new FileReader("/Users/sakthivel/Documents/java prctice/csvProcess/src/Copy of Birth and Death Dataset.csv"));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] singleRecord = line.split(splitBy);
                Records recordsObj = new Records(Integer.parseInt(singleRecord[0]), singleRecord[1], singleRecord[2], Integer.parseInt(singleRecord[3]));
                records.add(recordsObj);
            }
            for (Records recordsObj : records) {
                uniqueRegion.add(recordsObj.region);
                year.add(recordsObj.year);
            }

        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return 1;
    }

    /**
     *
     * @param type - Parameter as "Birth" Or "Death"
     * @return - returns hashmap of number of birth and death year wise
     */
    public HashMap<Integer, Integer> getBirthDeathCountByYear(String type) {
        HashMap<Integer,Integer> birthDeathCountOnYear = new HashMap<>();

        for (int years : year) {
            int count = 0;
            for (Records recordsObj : records) {
                if (recordsObj.year == years) {
                    if (recordsObj.birthDeath.equals(type)) {
                        count += recordsObj.count;
                    }
                }
            }
            birthDeathCountOnYear.put(years, count);
        }
        return birthDeathCountOnYear;
    }

    /**
     * Unique region - display unique regions from the read records
     */
    public int uniqueRegions() {
        Iterator<String> reg = uniqueRegion.iterator();
        System.out.println("Regions in the record:");
        while (reg.hasNext()) {
            System.out.println(reg.next());
        }
        return 1;
    }

    /**
     * unique years - display unique years from the read records
     */
    public int uniqueYears() {
        Iterator<Integer> date = year.iterator();
        System.out.println("\n\nPeriods in the record:");
        while (date.hasNext()) {
            System.out.println(date.next());
        }
        return 1;
    }

    /**
     * total count - calculates the total birth and death count of all the records
     */
    public int totalCount() {
        int totalBirth = 0, totalDeath = 0;
        for (Records recordsObj : records) {
            if (recordsObj.birthDeath.equals(constantsObj.birth)) {
                totalBirth += recordsObj.count;
            } else if (recordsObj.birthDeath.equals(constantsObj.death)) {
                totalDeath += recordsObj.count;
            }
        }
        System.out.println("Overall birth count: " + totalBirth);
        System.out.println("Overall death count: " + totalDeath);
        return 1;
    }

    /**
     * total count for years - calculates the total birth and death count year wise
     */
    public int totalCountForYear() {
        birthCountByYear = getBirthDeathCountByYear(constantsObj.birth);
        deathCountByYear = getBirthDeathCountByYear(constantsObj.death);
        for (int years : year) {
            System.out.println("Overall death count for the year " + years + " : " + birthCountByYear.get(years)+" \n Overall birth count for the year "+years+" : "+deathCountByYear.get(years));
        }
        return 1;
    }

    /**
     * total birth and death for region - calculates the total birth and death count for each region
     */
    public int totalCountForRegion() {
        for (String regions : uniqueRegion) {
            uniqueBirthCount = 0;
            uniqueDeathCount = 0;
            for (Records recordsObj : records) {
                if (recordsObj.region.equals(regions)) {
                    if (recordsObj.birthDeath.equals(constantsObj.birth)) {
                        uniqueBirthCount += recordsObj.count;
                    } else if (recordsObj.birthDeath.equals(constantsObj.death)) {
                        uniqueDeathCount += recordsObj.count;
                    }
                }
            }
            System.out.println("Overall birth count for the region " + regions + " : " + uniqueBirthCount);
            System.out.println("Overall death count for the region " + regions + " : " + uniqueDeathCount);
        }
        return 1;
    }

    /**
     * Maximum death and birth rate year - finds which year has the highest birth and death count
     */
    public int maxCountYear() {
        birthCountByYear = getBirthDeathCountByYear(constantsObj.birth);
        deathCountByYear = getBirthDeathCountByYear(constantsObj.death);
        int maxBirthRate = (Collections.max(birthCountByYear.values()));
        int maxDeathRate = (Collections.max(deathCountByYear.values()));

        for (Map.Entry<Integer, Integer> entry : birthCountByYear.entrySet()) {
            if (entry.getValue() == maxBirthRate) {
                System.out.println("The year " + entry.getKey() + " has the highest birth rate of: " + maxBirthRate);
            }
        }
        for (Map.Entry<Integer, Integer> entry : deathCountByYear.entrySet()) {
            if (entry.getValue() == maxDeathRate) {
                System.out.println("The year " + entry.getKey() + " has the highest death rate of: " + maxDeathRate);
            }
        }
        return 1;
    }

    /**
     * Maximum death and birth rate for each region with year - finds the highest birth and death count of region and its year
     */
    public int maxCountForRegion() {
        for (String regions : uniqueRegion) {
            int maxBirth = 0, maxDeath = 0, maxBirthYear = 0, maxDeathYear = 0;
            for (Records recordsObj : records) {
                if (recordsObj.region.equals(regions)) {
                    if (recordsObj.birthDeath.equals(constantsObj.birth)) {
                        if (recordsObj.count > maxBirth) {
                            maxBirth = recordsObj.count;
                            maxBirthYear = recordsObj.year;
                        }
                    } else if (recordsObj.birthDeath.equals(constantsObj.death)) {
                        if (recordsObj.count > maxDeath) {
                            maxDeath = recordsObj.count;
                            maxDeathYear = recordsObj.year;
                        }
                    }
                }
            }
            System.out.println("Highest birth count for the region " + regions + " : " + maxBirth + ", on the year: " + maxBirthYear);
            System.out.println("Highest death count for the region " + regions + " : " + maxDeath + ", on the year: " + maxDeathYear);
        }
        return 1;
    }
}