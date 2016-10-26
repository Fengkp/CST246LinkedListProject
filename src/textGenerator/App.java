package textGenerator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Links.LinkList;
import Links.StringList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class App extends Application {
    private File selectedFile;
    private File outputFile = new File("output/output.txt");
    private File dataFolder = new File("data");
    private StringList lyricList;
    private LinkList masterList = new LinkList();
    private boolean listOpen;
    private int wordCount = 0;
    private String keyWord = null;
    final Button genParaBtn = new Button("Generate");

    public StringList addList() throws IOException {
        Scanner scr = new Scanner(selectedFile);
        lyricList = new StringList();
        String key = null;
        boolean prevWordIsKey = false;

        while (scr.hasNext()) {
            if (lyricList.isEmpty()) {
                key = scr.next();
                if (!masterList.isEmpty()) {
                    while (masterList.find(key)) {
                        if (scr.hasNext())
                            key = scr.next();
                        else
                            return null;
                    }
                }
                lyricList.insertLast(key);
                prevWordIsKey = true;
            }
            else {

                String temp = scr.next();
                if (prevWordIsKey) {
                    lyricList.insertLast(temp);
                    prevWordIsKey = false;
                }
                else if (key.equals(temp) && scr.hasNext())
                    lyricList.insertLast(scr.next());
            }
        }
        scr.close();
        return lyricList;
    }

    public void startList() {
        try {
            StringList temp;
            if (selectedFile.exists()) {
                Scanner masterScr = new Scanner(selectedFile);

                while (masterScr.hasNext()) {
                    if (masterList.isEmpty())
                        masterList.insertLast(addList());
                    else {
                        temp = addList();
                        if (temp != null)
                            masterList.insertLast(temp);
                        else {
                            masterScr.close();
                            break;
                        }
                    }
                }

            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkNumber(int wordCount) {
        try {
            Integer.parseInt(String.valueOf(wordCount));
        }
        catch(Exception c) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException{
        Menu fileMenu = new Menu("File");
        MenuBar menuBar = new MenuBar();
        Label textKeyWordLbl = new Label("Key Word: " + keyWord);
        Label wordCountLbl = new Label("Word Count: " + String.valueOf(wordCount));


        Label paragraphLengthLbl = new Label("Paragraph Length: ");
        TextField paragraphLengthText = new TextField();
        paragraphLengthText.setPromptText("ex. '30'");
        paragraphLengthText.setOnAction(e -> {
            try {
                wordCount = Integer.parseInt(paragraphLengthText.getText());
                wordCountLbl.setText("Word Count: " + String.valueOf(wordCount));
                paragraphLengthText.clear();
            }
            catch(Exception s) {
                ErrorPopUp.display("Invalid input");
                paragraphLengthText.clear();
            }
        });


        Label keyWordLbl = new Label("Keyword: ");
        TextField keyWordText = new TextField();
        keyWordText.setPromptText("ex. 'how'");
        keyWordText.setOnAction(e -> {
            keyWord = keyWordText.getText().toLowerCase();
            if (masterList.isEmpty()) {
                ErrorPopUp.display("File not imported");
                keyWordText.clear();
            }
            else if (masterList.find(keyWord)) {
                textKeyWordLbl.setText("Key Word: " + keyWord);
                keyWordText.clear();
            }
            else {
                ErrorPopUp.display("Keyword does not exist");
                keyWordText.clear();
            }
        });


        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(dataFolder);
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));


        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.wrapTextProperty().set(true);

        // Buttons
        genParaBtn.setOnAction(e -> {
            //textArea.setText(masterList.getRandomWord().dData.generateParagraph(wordCount));
            if (!masterList.isEmpty())
                textArea.setText(masterList.getLink(keyWord).dData.generateParagraph(wordCount));
            else
                ErrorPopUp.display("File not imported");
        });


        //Menu Actions
        MenuItem openMItem = new MenuItem("Open File...");
        openMItem.setOnAction(e -> {
            selectedFile = fc.showOpenDialog(null);
            if (!listOpen) {
                startList();
                listOpen = true;
            }
        });
        MenuItem saveMItem = new MenuItem(("Save"));
        saveMItem.setOnAction(e -> {
            try {
                PrintWriter output = new PrintWriter(outputFile);
                output.print(textArea.getText());
                System.out.println(textArea.getText());
                output.close();
            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        MenuItem exitMItem = new MenuItem("Exit");
        exitMItem.setOnAction(e -> stage.close());
        fileMenu.getItems().addAll(openMItem, saveMItem, exitMItem);
        menuBar.getMenus().add(fileMenu);


        HBox keyWordHBox = new HBox();
        keyWordHBox.getChildren().addAll(keyWordLbl, keyWordText);
        keyWordHBox.setAlignment(Pos.CENTER_RIGHT);
        HBox wordCountHBox = new HBox();
        wordCountHBox.getChildren().addAll(paragraphLengthLbl, paragraphLengthText);
        VBox functionVBox = new VBox();
        functionVBox.getChildren().addAll(keyWordHBox, wordCountHBox, genParaBtn);
        functionVBox.setAlignment(Pos.CENTER_RIGHT);
        functionVBox.setSpacing(10);
        functionVBox.setPadding(new Insets(10));

        VBox textLabelsVBox = new VBox();
        textLabelsVBox.getChildren().addAll(textKeyWordLbl, wordCountLbl);
        VBox textVBox = new VBox();
        textVBox.getChildren().addAll(textLabelsVBox, textArea);
        textVBox.setSpacing(3);
        textVBox.setPadding(new Insets(10));


        BorderPane layout = new BorderPane();
        layout.setLeft(functionVBox);
        layout.setTop(menuBar);
        layout.setRight(textVBox);


        Scene scene = new Scene(layout, 600, 250);
        stage.setScene(scene);
        stage.setTitle("Text Generator");
        stage.show();
    }

}
