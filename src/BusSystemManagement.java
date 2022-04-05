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
        // Setup for JFrame
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        // Initialising data structures
        shortestPaths = new ShortestPathsStore();
        busStopSearch = new BusStopSearch();
        tripSearch = new ArrivalTimes();

        // Make response panes invisible to start
        shortestPathOutputPane.setVisible(false);
        shortestPathErrorPanel.setVisible(false);
        busStopSearchErrorPane.setVisible(false);
        tripSearchErrorPanel.setVisible(false);

        /*
         * Function called when the search for shortest path button is pressed
         */
        searchForShortestPathButton.addActionListener(e -> {
            try {
                // Read in the bus stop values
                int busStopFrom = (Integer.parseInt(fromBusStopInput.getText()));
                int busStopTo = (Integer.parseInt(toBusStopInput.getText()));

                // Calculate the shortest path
                AbstractMap.SimpleEntry<Double, String> value = shortestPaths.shortestPathFromTo(busStopFrom, busStopTo);

                if(value != null) { // Check if the shortest path is null
                    if(value.getKey() != Double.POSITIVE_INFINITY) { // If there is a path, return it
                        shortestPathOutput.setText(value.getValue());
                        shortestPathCostOutput.setText(value.getKey().toString());
                        shortestPathOutputPane.setVisible(true);
                        shortestPathErrorPanel.setVisible(false);
                    } else { // If there is no path between the two stops, it will return infinity
                        shortestPathErrorLabel.setText("There is no route between these stops.");
                        shortestPathErrorPanel.setVisible(true);
                        shortestPathOutputPane.setVisible(false);
                    }
                } else { // If the shortest path is null, it's because one of the stops does not exist
                    String response = String.format("Stop %d does not exist. Please try again.", ((shortestPaths.hasVertex(busStopFrom))? busStopTo : busStopFrom));
                    shortestPathErrorLabel.setText(response);
                    shortestPathErrorPanel.setVisible(true);
                    shortestPathOutputPane.setVisible(false);
                }
            } catch (NumberFormatException exception) { // Catch if one of the inputs is not an integer
                shortestPathErrorLabel.setText("One or both of these stops is not a valid number. Please try again with different input.");
                shortestPathErrorPanel.setVisible(true);
                shortestPathOutputPane.setVisible(false);
            }
        });

        /*
         * Function called when the search for bus stop by name button is pressed.
         */
        searchButton.addActionListener(e -> {

            // Get the input
            String searchText = searchTextField.getText().toUpperCase();

            // Search for the input
            ArrayList<Stop> result1 = busStopSearch.searchString(searchText);
            Stop[] result = result1.toArray(new Stop[0]);

            if(result.length != 0) { // If there are any results, show them
                busStopSearchResults.setListData(result);
                busStopSearchErrorPane.setVisible(false);
                busStopReturnPanel.setVisible(true);
            } else { // If there are no results, show an error message
                busStopReturnPanel.setVisible(false);
                busStopSearchErrorPane.setVisible(true);
            }
        });

        /*
         * Function called when the search for trip based on arrival time is pressed.
         */
        tripSearchButton.addActionListener(e -> {

            // Get input text and edit string appropriately
            String searchText = tripSearchTextInput.getText();
            if(searchText.charAt(0) == '0'){
                searchText = searchText.substring(1);
            }
            searchText = searchText.toUpperCase();

            // Search for the string
            TripSectionDetails[] result = tripSearch.searchArrivalTime(searchText).toArray(new TripSectionDetails[0]);

            if(result.length > 0) { // Checking there is any result
                tripSearchResults.setListData(result);
                tripSearchErrorPanel.setVisible(false);
                tripSearchReturnPanel.setVisible(true);
            } else { // If there are no search results
                if(searchText.matches(".*[^0123456789:].*")){ // If the input contains any invalid characters, return appropriate error message
                    tripSearchErrorLabel.setText("Invalid input.");
                } else { // Otherwise, there is just no trip at that time
                    tripSearchErrorLabel.setText("No trip arrives at this time.");
                }
                tripSearchErrorPanel.setVisible(true);
                tripSearchReturnPanel.setVisible(false);
            }
        });

        /*
         * Function called when the ok button is pressed on the shortest path error message.
         */
        errorClose.addActionListener(e -> {
            shortestPathErrorPanel.setVisible(false);
        });
    }
}
