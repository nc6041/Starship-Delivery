import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * StarshipLoader interface
 */
interface StarshipLoaderInterface {
    public StarshipGraph loadFile(String csvFilePath) throws FileNotFoundException;

}

/**
 * Loads a list of locations into a graph from a csv file to be used in the back end code
 */
public class StarshipLoader implements StarshipLoaderInterface {
    /**
     * Loads a csv file and parses it, adding vertexes and correct edges and weights into a graph
     * @param csvFilePath file to be used
     * @return graph
     * @throws FileNotFoundException if file does not exist
     */
    @Override
    public StarshipGraph loadFile(String csvFilePath) throws FileNotFoundException {
        if (!new File(csvFilePath).exists()) {
            throw new FileNotFoundException("File does not exist");
        } else {
            Scanner scanner = new Scanner(new File(csvFilePath), "UTF-8");
            StarshipGraph graph = new StarshipGraph();

            int buildingIdx = 0;
            int b1Idx = 0;
            int b2Idx = 0;
            int b3Idx = 0;
            int d1Idx = 0;
            int d2Idx = 0;
            int d3Idx = 0;

            String header = scanner.nextLine();
            String[] headerArray = header.split(",");
            for (int k = 0; k < headerArray.length; k++) {
                if (headerArray[k].contains("Building")) {
                    buildingIdx = k;
                } else if (headerArray[k].contains("B1")) {
                    b1Idx = k;
                } else if (headerArray[k].contains("B2")) {
                    b2Idx = k;
                } else if (headerArray[k].contains("B3")) {
                    b3Idx = k;
                } else if (headerArray[k].contains("D1")) {
                    d1Idx = k;
                } else if (headerArray[k].contains("D2")) {
                    d2Idx = k;
                } else if (headerArray[k].contains("D3")) {
                    d3Idx = k;
                }
            }
            // reset scanner to the top
            scanner = new Scanner(new File(csvFilePath), "UTF-8");
            // skip the header line
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                Scanner rowSc = new Scanner(scanner.nextLine());
                // While row has more columns to read
               while (rowSc.hasNext()) {
                    // Split the csv row into an array
                    String[] rowDataArr = rowSc.nextLine().split(",");

                    for (int i = 0; i < rowDataArr.length; i++) {
//                        String[] dataArray = scanner.nextLine().split(",");
                        if (i == buildingIdx) {
                            graph.insertVertex(rowDataArr[buildingIdx]);
                        }
                        if (i == b1Idx) {
                            graph.insertVertex(rowDataArr[b1Idx]);
                            graph.insertEdge(rowDataArr[buildingIdx], rowDataArr[b1Idx],
                                    Integer.parseInt(rowDataArr[d1Idx]));
                        }
                        if (i == b2Idx) {
                            graph.insertVertex(rowDataArr[b2Idx]);
                            graph.insertEdge(rowDataArr[buildingIdx], rowDataArr[b2Idx],
                                    Integer.parseInt(rowDataArr[d2Idx]));
                        }
                        if (i == b3Idx) {
                            graph.insertVertex(rowDataArr[b3Idx]);
                            graph.insertEdge(rowDataArr[buildingIdx], rowDataArr[b3Idx],
                                    Integer.parseInt(rowDataArr[d3Idx]));
                        }
                    }

                    }
                }
                scanner.close();
                return graph;
            }
        }

    private int quoteCounter(String entry) {
        int count = 0;
        for (int i = 0; i < entry.length(); i++) {
            if (entry.charAt(i) == '"') {
                count++;
            }
        }
        return count;
    }

}

