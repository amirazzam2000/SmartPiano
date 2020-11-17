package view;

import controller.GraphController;
import controller.ManageSongsController;
import model.Statistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class that manages the creation of both graphs (minutes played and number of plays)
 * uses GraphMinutesView and GraphSongPlayView to create both graphs, and then joins them into one panel
 * <p>
 * @author Arcadia Youlten
 * @version %I% %G%
 */

public class GraphView {

    private final GraphSongPlayView graphSongPlayView;
    private final GraphMinutesView graphMinutesView;
    private final JButton jBBack;

    /**
     * Initializes the buttons.
     * The back button is assigned an image
     * Initializes both "sub-views" for this class, one for each graph
     */

    public GraphView(){
        Image image = ViewUtils.getScaledImageFromPath("Shared/src/view/assets/noun_back_1227057.bmp", null, 25, 25);
        this.jBBack = new JButton(new ImageIcon(image));

        graphMinutesView = new GraphMinutesView();
        graphSongPlayView = new GraphSongPlayView();
    }

    /**
     * This method constructs 2 graphs with the provided statistics.
     * One graph is for the amount of minutes played per day, and the other is for the number of songs played.
     * The 2 graphs are in separate tabs.
     * Implements a back button to return to a previous menu
     *
     * @see GraphMinutesView
     * @see GraphSongPlayView
     *
     * @param statistics the statistics to be displayed.
     * @return Returns the constructed panel to be placed in the main frame
     */

    public JPanel addPanel(ArrayList<Statistics> statistics){

        JTabbedPane jTabbedPane = new JTabbedPane();
        JPanel jMaster = new JPanel();
        jMaster.setLayout(new BoxLayout(jMaster, BoxLayout.Y_AXIS));
        jMaster.setSize(MainView.GRAPH_WINDOW_WIDTH ,
                MainView.GRAPH_WINDOW_HEIGHT);

        JPanel graph1 = new JPanel();
        graph1 = graphSongPlayView.addPanels(statistics);

        JPanel graph2 = new JPanel();
        graph2 = graphMinutesView.addPanels(statistics);

        jTabbedPane.addTab("Number of Songs Played", graph1);
        jTabbedPane.addTab("Number of Minutes Played", graph2);
        jMaster.add(jTabbedPane);
        jMaster.add(jBBack);

        return jMaster;
    }

    /**
     * Method for registering the controller/action listener to each of the buttons in this view
     * @see GraphController for the actions performed with each button
     */
    public void registerButtonsGraph(ActionListener actionListener) {
        jBBack.addActionListener(actionListener);
        jBBack.setActionCommand(MainView.BACK);
    }


}
