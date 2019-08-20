import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileOperations {

    public static void main(String[] args) throws IOException {

        String html = FileUtils.readFileToString(new File("/path/to/file.html"));
        
        Document doc = Jsoup.parse(html);
        
        Elements tableElements = doc.select("table");
        Element testResultsSummaryTable = tableElements.get(1);
        Elements testResultRows = testResultsSummaryTable.select(":not(thead) tr");

        List<TestResult> testResuls = new ArrayList<>();

        for (int i = 1; i < testResultRows.size(); i++) {
            Elements rowItems = testResultRows.get(i).select("td");
            
            String testMethod = rowItems.get(1).text();
            int duration = Integer.valueOf(rowItems.get(2).text());
            String testData = rowItems.get(3).text();
            String status = rowItems.get(4).text();

            testResuls.add(new TestResult(testMethod, duration, testData, status));
        }

        Map<String, List<TestResult>> groupTestResults = testResuls.stream()
                .collect(Collectors.groupingBy(TestResult::getTestMethod));

        List<TestResult> results = groupTestResults.values().stream()
                .map(group -> {
                    return new TestResult(
                            group.get(0).getTestMethod(),
                            (int) group.stream().map(TestResult::getDuration).mapToInt(Integer::intValue).average().getAsDouble(),
                            group.get(0).getTestData(),
                            group.get(0).getStatus()
                    );
                })
                .collect(Collectors.toList());

        try (FileWriter fileWriter = new FileWriter("/path/to/outputfile.csv")) {
            
            try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
                printWriter.println("Test Method, Duration, Test Data, Status");
                for (TestResult testResult : results) {
                    printWriter.println(testResult.getTestMethod() + "," + testResult.getDuration() + "," + testResult.getTestData() + "," + testResult.getStatus());
                }
            }
        }

        System.out.println("");
    }
}


class TestResult {
    public TestResult(String testMethod, int duration, String testData, String status) {
        this.testMethod = testMethod;
        this.duration = duration;
        this.testData = testData;
        this.status = status;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String testMethod;
    private int duration;
    private String testData;
    private String status;
}
