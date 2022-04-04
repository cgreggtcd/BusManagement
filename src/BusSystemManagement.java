import javax.swing.*;
import java.util.AbstractMap;
import java.util.ArrayList;

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
    private JList<TripSectionDetails> tripSearchResults;
    private JPanel shortestPathErrorPanel;
    private JLabel shortestPathErrorLabel;
    private JButton errorClose;
    private JPanel busStopSearchErrorPane;
    private JScrollPane busStopReturnPanel;
    private JPanel tripSearchErrorPanel;
    private JLabel tripSearchErrorLabel;
    private JScrollPane tripSearchReturnPanel;

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
        shortestPathErrorPanel.setVisible(false);
        busStopSearchErrorPane.setVisible(false);
        tripSearchErrorPanel.setVisible(false);

        searchForShortestPathButton.addActionListener(e -> {
            try {
                int busStopFrom = (Integer.parseInt(fromBusStopInput.getText()));
                int busStopTo = (Integer.parseInt(toBusStopInput.getText()));
                AbstractMap.SimpleEntry<Double, String> value = shortestPaths.shortestPathFromTo(busStopFrom, busStopTo);
                if(value != null) {
                    shortestPathOutput.setText(value.getValue());
                    shortestPathCostOutput.setText(value.getKey().toString());
                    shortestPathOutputPane.setVisible(true);
                    shortestPathErrorPanel.setVisible(false);
                } else {
                    shortestPathErrorLabel.setText("Either one/both of these stops do not exist, or there is no route between them. Please try again.");
                    shortestPathErrorPanel.setVisible(true);
                    shortestPathOutputPane.setVisible(false);
                }
            } catch (NumberFormatException exception) {
                shortestPathErrorLabel.setText("One or both of these stops is not a valid number. Please try again with different input.");
                shortestPathErrorPanel.setVisible(true);
                shortestPathOutputPane.setVisible(false);
            }
        });
        searchButton.addActionListener(e -> {
            String searchText = searchTextField.getText().toUpperCase();
            ArrayList<Stop> result1 = busStopSearch.searchString(searchText);
            Stop[] result = result1.toArray(new Stop[0]);
            if(result.length != 0) {
                busStopSearchResults.setListData(result);
                busStopSearchErrorPane.setVisible(false);
                busStopReturnPanel.setVisible(true);
            } else {
                busStopReturnPanel.setVisible(false);
                busStopSearchErrorPane.setVisible(true);
            }
        });
        tripSearchButton.addActionListener(e -> {
            String searchText = tripSearchTextInput.getText();
            if(searchText.charAt(0) == '0'){
                searchText = searchText.substring(1);
            }
            searchText = searchText.toUpperCase();
            TripSectionDetails[] result = tripSearch.searchArrivalTime(searchText).toArray(new TripSectionDetails[0]);
            if(result.length > 0) {
                tripSearchResults.setListData(result);
                tripSearchErrorPanel.setVisible(false);
                tripSearchReturnPanel.setVisible(true);
            } else {
                if(searchText.matches(".*[^0123456789:].*")){
                    tripSearchErrorLabel.setText("Invalid input.");
                }else {
                    tripSearchErrorLabel.setText("No trip arrives at this time.");
                }
                tripSearchErrorPanel.setVisible(true);
                tripSearchReturnPanel.setVisible(false);
            }
        });
        errorClose.addActionListener(e -> {
            shortestPathErrorPanel.setVisible(false);
        });
    }
}
