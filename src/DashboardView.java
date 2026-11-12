
import java.util.ArrayList;
import java.util.List;

public class DashboardView implements Observer {

    private List<String> displayedItems;

    /**
     * Creates an empty DashboardView.
     */
    public DashboardView() {
        displayedItems = new ArrayList<String>();
    }

    /**
     * Updates the dashboard with new information.
     */
    @Override
    public void update(List<String> list) {
        displayedItems = new ArrayList<String>(list);

        System.out.println("=== Patient Dashboard ===");

        for (String item : displayedItems) {
            System.out.println(item);
        }
    }

    /**
     * Returns the currently displayed dashboard information.
     */
    public List<String> getDisplayedItems() {
        return new ArrayList<String>(displayedItems);
    }
}
