package GUI;

import Kernel.Graph;
import Kernel.NavigationMap;
import Kernel.PathUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.DirectoryChooser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Controller {
    // Map and vertices
    @FXML
    private Pane linePane;
    @FXML
    private Button A;
    @FXML
    private Button B;
    @FXML
    private Button C;
    @FXML
    private Button D;
    @FXML
    private Button E;
    @FXML
    private Button F;
    @FXML
    private Button G;
    @FXML
    private Button H;
    @FXML
    private Button I;
    @FXML
    private Button J;
    @FXML
    private Button K;
    @FXML
    private Button L;
    @FXML
    private Button M;
    @FXML
    private Button N;
    @FXML
    private Button O;
    @FXML
    private Button P;
    @FXML
    private Button Q;
    @FXML
    private Button R;
    @FXML
    private Button S;
    @FXML
    private Button T;
    @FXML
    private Button U;
    @FXML
    private Button V;
    @FXML
    private Button W;
    @FXML
    private Button X;
    @FXML
    private Button Y;
    @FXML
    private Button Z;

    private ObservableList<Character> vertices = FXCollections.observableArrayList();

    // Confirm and reset
    @FXML
    private Button resetButton;
    @FXML
    private Button confirmButton;

    // Travel mode
    @FXML
    private RadioButton onFootButton;
    @FXML
    private RadioButton byCarButton;
    @FXML
    private RadioButton byBusButton;

    // Method one
    @FXML
    private Tab methodOneTab;
    @FXML
    private ComboBox departureBox;
    @FXML
    private ComboBox destinationBox;
    @FXML
    private Label resultLabel;

    private boolean waitDeparture;

    // Method two
    @FXML
    private Tab methodTwoTab;
    @FXML
    private ComboBox mergeBox;
    @FXML
    private Button mergeOutPutButton;
    @FXML
    private Label mergeOutPutLabel;

    // Method three
    @FXML
    private Tab methodThreeTab;
    @FXML
    private Button entireOutPutButton;
    @FXML
    private Label entireOutPutLabel;

    @FXML
    private void initialize() {
        // init vertices
        for (char c = 'A'; c < 'Z' + 1; ++c)
            vertices.add(c);

        // init button event
        initMapButton();
        initOutputButton();

        departureBox.setItems(vertices);
        destinationBox.setItems(vertices);
        waitDeparture = true;

        mergeBox.setItems(vertices);
    }

    // Reset
    public void reset() {
        // clear map lines
        linePane.getChildren().clear();

        // reset travel mode
        onFootButton.setSelected(true);

        // reset method one tab
        departureBox.setValue(null);
        destinationBox.setValue(null);
        resultLabel.setText(null);

        // reset method two tab
        mergeBox.setValue(null);
        mergeOutPutLabel.setText("未选定路径");

        // reset method three tab
        entireOutPutLabel.setText("未选定路径");
    }

    // Confirm
    public void confirm() {
        Graph graph;

        if (onFootButton.isSelected()) {
            graph = NavigationMap.pedestrianMap;
        } else if (byCarButton.isSelected()) {
            graph = NavigationMap.vehicleMap;
        } else {
            graph = NavigationMap.busLineMap;
        }

        if (methodOneTab.isSelected()) {
            doMethodOne(graph);
        } else if (methodTwoTab.isSelected()) {
            doMethodTwo(graph);
        } else {
            doMethodThree(graph);
        }
    }

    private void initMapButton() {
        for (char c = 'A'; c <= 'Z'; ++c) {
            Button button = getMapButton(c);
            assert button != null;
            button.setOnAction((e) -> setComboBoxValue(button));
        }
    }

    private Button getMapButton(char c) {
        switch (c) {
            case 'A':
                return A;
            case 'B':
                return B;
            case 'C':
                return C;
            case 'D':
                return D;
            case 'E':
                return E;
            case 'F':
                return F;
            case 'G':
                return G;
            case 'H':
                return H;
            case 'I':
                return I;
            case 'J':
                return J;
            case 'K':
                return K;
            case 'L':
                return L;
            case 'M':
                return M;
            case 'N':
                return N;
            case 'O':
                return O;
            case 'P':
                return P;
            case 'Q':
                return Q;
            case 'R':
                return R;
            case 'S':
                return S;
            case 'T':
                return T;
            case 'U':
                return U;
            case 'V':
                return V;
            case 'W':
                return W;
            case 'X':
                return X;
            case 'Y':
                return Y;
            case 'Z':
                return Z;
            default:
                return null;
        }
    }

    private void initOutputButton () {
        mergeOutPutButton.setOnAction((e -> setOutputPath(mergeOutPutLabel)));
        entireOutPutButton.setOnAction((e -> setOutputPath(entireOutPutLabel)));
    }

    private void setOutputPath(Label outputPathLabel) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(null);
        if (dir != null) {
            outputPathLabel.setText(dir.getAbsolutePath());
        }
    }

    private void setComboBoxValue(Button button) {
        if (methodOneTab.isSelected()) {
            char value = button.getText().charAt(0);
            if (waitDeparture) {
                departureBox.setValue(value);
                waitDeparture = false;
            } else {
                destinationBox.setValue(value);
                waitDeparture = true;
            }
        } else if (methodTwoTab.isSelected()) {
            char value = button.getText().charAt(0);
            mergeBox.setValue(value);
        }
    }

    public void exchangeEndpoint() {
        Object temp = departureBox.getValue();
        departureBox.setValue(destinationBox.getValue());
        destinationBox.setValue(temp);
    }

    private void drawPath(int[] path) {
        for (int i = 0; i < path.length - 1; ++i) {
            Button startMapButton = getMapButton(vertices.get(path[i]));
            Button endMapButton = getMapButton(vertices.get(path[i + 1]));

            Line line = new Line();
            line.setStartX(startMapButton.getLayoutX() + 13.5);
            line.setStartY(startMapButton.getLayoutY() + 13.5);
            line.setEndX(endMapButton.getLayoutX() + 13.5);
            line.setEndY(endMapButton.getLayoutY() + 13.5);
            line.setStrokeWidth(5);
            line.setStroke(Color.LAWNGREEN);

            linePane.getChildren().add(line);
        }
    }

    private String getModeName() {
        if (onFootButton.isSelected())
            return "步行";
        else if (byCarButton.isSelected())
            return "驾车";
        else
            return "公交";
    }

    private void doMethodOne(Graph graph) {
        if (null == departureBox.getValue()) {
            resultLabel.setText("请指定出发地");
            return;
        }

        if (null == destinationBox.getValue()) {
            resultLabel.setText("请指定目的地");
            return;
        }

        linePane.getChildren().clear();

        int start = (char) departureBox.getValue() - 'A';
        int end = (char) destinationBox.getValue() - 'A';

        PathUtils.Result result = PathUtils.shortestPath(graph, start, end);

        if (result == null) {
            resultLabel.setText("无法到达");
            return;
        }

        int[] path = result.path;
        String resultString;

        if (!byBusButton.isSelected()) {
            // on foot or by car
            resultString = String.format("%.2f", result.dist) + "km";
            drawPath(path);
        } else {
            // by bus
            resultString = result.dist + "min\n";
            Graph auxiliary = NavigationMap.vehicleMap;

            double dist = 0;
            for (int i = 0; i < path.length - 1; ++i) {
                result = PathUtils.shortestPath(auxiliary, path[i], path[i + 1]);
                drawPath(result.path);
                dist += result.dist;
            }

            resultString += String.format("%.2f", dist) + "km";
        }

        resultLabel.setText(resultString);
    }

    private void doMethodTwo(Graph graph) {
        char mergeChar = (char) mergeBox.getValue();
        int merge = mergeChar - 'A';
        String filePath = mergeOutPutLabel.getText();

        if (filePath.equals("未选定路径") || filePath.equals("无效路径"))
            return;

        if (filePath.equals("保存成功")) {
            mergeOutPutLabel.setText("未选定路径");
            return;
        }

        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            mergeOutPutLabel.setText("无效路径");
            return;
        }

        String fileName = "汇聚地点：" + mergeChar + "(" + getModeName() + ")";
        File file = new File(dir, fileName + ".txt");
        int count = 0;
        while (file.exists()) {
            file = new File(dir, fileName + " (" + (++count) + ").txt");
        }

        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            StringBuilder content = new StringBuilder();
            content.append(fileName).append("\n");
            for (int i = 0; i < vertices.size(); ++i) {
                if (i == merge)
                    continue;

                PathUtils.Result result = PathUtils.shortestPath(graph, i, merge);
                if (null == result)
                    continue;

                int[] path = result.path;
                content.append(vertices.get(path[0])).append(" -> ").append(mergeChar).append(": ");

                if (!byBusButton.isSelected()) {
                    // on foot or by car
                    content.append(String.format("%.2f", result.dist)).append("km\n");
                    for (int j = 0; j < path.length - 1; ++j) {
                        content.append(vertices.get(path[j])).append(" -> ");
                    }
                    content.append(mergeChar).append("\n\n");
                } else {
                    // by bus
                    content.append(String.format("%.2f", result.dist)).append("min, ");
                    Graph auxiliary = NavigationMap.vehicleMap;
                    StringBuilder midway = new StringBuilder();

                    double dist = 0;
                    for (int j = 0; j < path.length - 1; ++j) {
                        result = PathUtils.shortestPath(auxiliary, path[j], path[j + 1]);
                        assert result != null;

                        int[] midwayPath = result.path;
                        for (int k = 0; k < midwayPath.length - 1; ++k) {
                            midway.append(vertices.get(midwayPath[k])).append(" -> ");
                        }
                        char midEnd = vertices.get(midwayPath[midwayPath.length - 1]);
                        midway.append(midEnd).append("\n");
                        dist += result.dist;
                    }
                    content.append(String.format("%.2f", dist)).append("km\n").append(midway).append("\n");
                }
            }
            outputStream.write(content.toString().getBytes());
            outputStream.close();
            mergeOutPutLabel.setText("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            mergeOutPutLabel.setText("无效路径");
        }
    }

    private void doMethodThree(Graph graph) {
        String filePath = entireOutPutLabel.getText();

        if (filePath.equals("未选定路径") || filePath.equals("无效路径"))
            return;

        if (filePath.equals("保存成功")) {
            entireOutPutLabel.setText("未选定路径");
            return;
        }

        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            entireOutPutLabel.setText("无效路径");
            return;
        }

        String fileName = "任意两点最短路途" + "(" + getModeName() + ")";
        File file = new File(dir, fileName + ".txt");
        int count = 0;
        while (file.exists()) {
            file = new File(dir, fileName + " (" + (++count) + ").txt");
        }


        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            StringBuilder content = new StringBuilder();
            double[][] distMatrix = PathUtils.allPairShortestPath(graph);

            for (int i = 0; i < vertices.size(); ++i) {
                for (int j = 0; j < vertices.size(); ++j) {
                    if (i == j || distMatrix[i][j] == Double.MAX_VALUE)
                        continue;

                    content.append(vertices.get(i)).append(" -> ").append(vertices.get(j)).append(": ");

                    if (!byBusButton.isSelected()) {
                        // on foot or by car
                        content.append(String.format("%.2f", distMatrix[i][j])).append("km\n");
                    } else {
                        // by bus
                        content.append(String.format("%.2f", distMatrix[i][j])).append("min, ");

                        PathUtils.Result result = PathUtils.shortestPath(graph, i, j);
                        assert result != null;

                        int[] path = result.path;
                        Graph auxiliary = NavigationMap.vehicleMap;
                        double dist = 0;
                        for (int k = 0; k < path.length - 1; ++k) {
                            result = PathUtils.shortestPath(auxiliary, path[k], path[k + 1]);
                            assert result != null;
                            dist += result.dist;
                        }
                        content.append(String.format("%.2f", dist)).append("km\n");
                    }
                }
            }
            outputStream.write(content.toString().getBytes());
            outputStream.close();
            entireOutPutLabel.setText("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            mergeOutPutLabel.setText("无效路径");
        }
    }
}
