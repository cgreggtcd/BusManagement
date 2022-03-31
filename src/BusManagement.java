import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BusManagement {

    public static void main(String[] args) {
        ShortestPathsStore shortestPaths = new ShortestPathsStore();
        shortestPaths.shortestPathFromTo(646, 1277);
    }
}
