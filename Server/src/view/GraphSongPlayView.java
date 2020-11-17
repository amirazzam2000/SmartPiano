package view;

import model.Statistics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Class for displaying the graph with the amount of total songs played
 * <p>
 * @author Arcadia Youlten
 * @version %I% %G%
 */

public class GraphSongPlayView extends JPanel {

    private final int MAIN_PANEL_WIDTH = 800;
    private final int MAIN_PANEL_HEIGHT = 600;
    private final int margin = 25;
    private final Color pointColor = new Color(43, 4, 94, 203);
    private final Color lineColor = new Color(44, 102, 230, 180);
    private final Color gridColor = new Color(200, 200, 200, 200);
    private final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private final int pointWidth = 8;
    private int minPlays = Integer.MAX_VALUE;
    private int maxPlays = Integer.MIN_VALUE;

    private ArrayList<Statistics> stats;

    /**
     * Auxiliary method for knowing the lower boundary of the graph
     */

    private void getMinPlays() {
        for (Statistics statistics : stats) {
            this.minPlays = Math.min(this.minPlays, statistics.getNumSongs());
        }
    }

    /**
     * Auxiliary method for knowing the upper boundary of the graph
     */

    private void getMaxPlays() {
        for (Statistics statistics : stats) {
            this.maxPlays = Math.max(this.maxPlays, statistics.getNumSongs());
        }
    }

    /**
     * This method constructs 1 graph with the provided statistics.
     * This graph displays the number of total songs played.
     *
     * @see GraphView
     *
     * @param statistics the statistics to be displayed.
     * @return Returns the constructed panel to be placed in the graph frame
     */

    public JPanel addPanels(ArrayList<Statistics> statistics){

        this.stats = statistics;

        getMinPlays();
        getMaxPlays();

        JPanel mainPanel = this;

        mainPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH ,
                MAIN_PANEL_HEIGHT));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(mainPanel);

        return panel;

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<Point> graphPoints = convertPoints();
        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(margin *2, margin, MAIN_PANEL_WIDTH - (3* margin), MAIN_PANEL_HEIGHT - 3 * margin);
        drawHatchMarks(g2, graphPoints);
        drawLines(g2, graphPoints);
        drawDots(g2, graphPoints);

    }

    /**
     * Draws hatch marks on the graph to show precise placement of points and values.
     * Labels are also added
     * Care is taken to avoid divide by zero errors
     * @param g2 - Graphics2D allows for control over the graphics library.
     * @param graphPoints - A list of all of the points we want to display on our graph
     */
    private void drawHatchMarks(Graphics2D g2, List<Point> graphPoints) {

        g2.setColor(Color.BLACK);
        String xLabel;
        String yLabel;
        //for y axis
        //horizontal lines
        int numberYDivisions = 25;
        for (int i = 0; i < numberYDivisions +1; i++) {
            int y_val = MAIN_PANEL_HEIGHT - ((i * (MAIN_PANEL_HEIGHT - margin*3 )) / numberYDivisions + margin *2);
            if (stats.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(margin *2, y_val, MAIN_PANEL_WIDTH - margin, y_val);
                g2.setColor(Color.BLACK);
                yLabel = ((int) ((minPlays + (maxPlays - minPlays) * ((i * 1.0) / numberYDivisions)) * 100)) / 100+ "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, (margin *2) - labelWidth - 5, y_val + (metrics.getHeight() / 2));
            }
        }

        // and for x axis
        //vertical lines

        for (int i = 0; i < stats.size(); i++) {
            if (stats.size() > 1) {
                int x0 = i * (MAIN_PANEL_WIDTH - margin * 3) / (stats.size() - 1) + margin *2;
                int y0 = MAIN_PANEL_HEIGHT - margin *2;
                if ((i % ((int) ((stats.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, MAIN_PANEL_HEIGHT - margin *2, x0, margin);
                    g2.setColor(Color.BLACK);
                    xLabel = stats.get(i).getDate();
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
            }
            else {
                int x0 =
                        i * (MAIN_PANEL_WIDTH - margin *3) /2 + margin *2;
                int y0 = MAIN_PANEL_HEIGHT - margin*2;
                g2.setColor(gridColor);
                g2.drawLine(x0, MAIN_PANEL_HEIGHT - margin *2 - 1 - pointWidth, x0, margin);
                g2.setColor(Color.BLACK);
                xLabel = stats.get(i).getDate();
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
        }

    }

    /**
     * Draws each plot point
     * @param g2 - Graphics2D allows for control over the graphics library.
     * @param graphPoints - A list of all of the points we want to display on our graph
     */

    private void drawDots(Graphics2D g2, List<Point> graphPoints) {
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            g2.fillOval(x, y, pointWidth, pointWidth);
        }
    }

    /**
     * Draws the axes and the lines that connect each dot to each other
     * @param g2 - Graphics2D allows for control over the graphics library.
     * @param graphPoints - A list of all of the points we want to display on our graph
     */

    private void drawLines(Graphics2D g2, List<Point> graphPoints) {

        // create x and y axes
        g2.setColor(Color.BLACK);
        g2.drawLine(margin *2, MAIN_PANEL_HEIGHT - margin *2, margin *2, margin);
        g2.drawLine(margin *2, MAIN_PANEL_HEIGHT - margin *2, MAIN_PANEL_WIDTH - margin, MAIN_PANEL_HEIGHT - margin*2);

        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

    }

    /**
     * Aux method for calculating the scale of the Y axis
     * @return - scale of the y axis
     */
    private double getyScale() {
        double yScale;
        if (maxPlays != minPlays)
            yScale =
                    ((double) MAIN_PANEL_HEIGHT - 3 * margin) / (maxPlays - minPlays);
        else
            yScale = ((double) MAIN_PANEL_HEIGHT - 3 * margin);
        return yScale;
    }

    /**
     * Aux method for calculating the scale of the X axis
     * @return - scale of the x axis
     */
    private double getxScale() {
        double xScale;
        if (stats.size() > 1)
            xScale = ((double) MAIN_PANEL_WIDTH - (3 * margin)) / (stats.size() - 1);
        else
            xScale = ((double) MAIN_PANEL_WIDTH - (3 * margin)) / 2;

        return xScale;
    }

    /**
     *  Aux method for converting all of the different statistics to points that can be plotted on a graph
     *  The x value is the index of the list, multiplied by the x scale with the margin -- allows for the correct positioning based on the number
     *  of values
     *  The y value is the number of song plays with reference to the yScale
     *  We save this information in an arraylist so that we can access all of the points at once later
     * @return - A list of all of the points we want to display on our graph
     */
    private List<Point> convertPoints(){

        double xScale = getxScale();
        double yScale = getyScale();

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < stats.size(); i++) {
            int x1 = (int) (i * xScale + margin *2);
            int y1 = (int) ((maxPlays - stats.get(i).getNumSongs()) * yScale + margin);
            graphPoints.add(new Point(x1, y1));
        }
        return graphPoints;
    }
}
