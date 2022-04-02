import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;

public class BusSystemManagement extends JFrame {
    private JButton findShortestPathButton;
    private JButton searchBusStopButton;
    private JButton searchTripButton;
    private JPanel mainPanel;
    private JTabbedPane tabs;
    private JTextField fromBusStopInput;
    private JTextField toBusStopInput;
    private JButton searchForShortestPathButton;
    private JLabel fromBusStopLabel;
    private JLabel toBusStopLabel;
    private JLabel shortestPathTitle;
    private JLabel costLabel;
    private JPanel shortestPathPanel;
    private JPanel busStopSearchPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JLabel searchHereLabel;
    private JPanel tripSearchPanel;
    private JTextField tripSearchTextInput;
    private JButton tripSearchButton;
    private JLabel tripSearchLabel;
    private JLabel shortestPathOutput;
    private JLabel shortestPathCostOutput;
    private JPanel shortestPathOutputPane;
    private JList<Stop> busStopSearchResults;
    private JList<Stop> tripSearchResults;

    ShortestPathsStore shortestPaths;
    BusStopSearch busStopSearch;
    ArrivalTimes tripSearch;

    public BusSystemManagement(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        shortestPaths = new ShortestPathsStore();
        busStopSearch = new BusStopSearch();
        tripSearch = new ArrivalTimes();

        shortestPathOutputPane.setVisible(false);

        searchForShortestPathButton.addActionListener(e -> {
            int busStopFrom = (Integer.parseInt(fromBusStopInput.getText()));
            int busStopTo = (Integer.parseInt(toBusStopInput.getText()));
            AbstractMap.SimpleEntry<Double, String> value = shortestPaths.shortestPathFromTo(busStopFrom, busStopTo);
            shortestPathOutput.setText(value.getValue());
            shortestPathCostOutput.setText(value.getKey().toString());
            shortestPathOutputPane.setVisible(true);
        });
        searchButton.addActionListener(e -> {
            String searchText = searchTextField.getText();
            busStopSearchResults.setListData((Stop[]) busStopSearch.searchString(searchText).toArray());
        });
        tripSearchButton.addActionListener(e -> {
            String searchText = tripSearchTextInput.getText();
            tripSearchResults.setListData((Stop[]) tripSearch.searchArrivalTime(searchText).toArray());
        });
    }
}
