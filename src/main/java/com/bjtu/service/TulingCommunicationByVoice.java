package com.bjtu.service;

//import javafx.application.Application;
//import javafx.geometry.Orientation;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.FlowPane;
//import javafx.stage.Stage;
 
/**
 * 按住按钮或者按住Ctrl说话
 * 包含丰富的语料库，即使同一个沟通语句，也有不同的回答
 */
public class TulingCommunicationByVoice {
    private VoiceCompose voiceCompose = new VoiceCompose();
    private VoiceRecognition voiceRecognition = new VoiceRecognition();
    public VoiceRecorder voiceRecorder = new VoiceRecorder();
 
//    public void start(Stage primaryStage) {
//        Label label = new Label("按住按钮或者按住Ctrl说话");
//        Button button = new Button("按住说话");
//        FlowPane flowPane = new FlowPane(label, button);
//        flowPane.setAlignment(Pos.CENTER);
//        flowPane.setOrientation(Orientation.HORIZONTAL);
//        flowPane.setPrefHeight(80);
//        flowPane.setPrefWidth(200);
//        flowPane.setVgap(20);
// 
//        button.setOnMousePressed(event -> voiceRecorder.captureAudio());
// 
//        button.setOnMouseReleased(event -> responseMaster());
// 
//        flowPane.setOnKeyPressed(event -> voiceRecorder.captureAudio());
// 
//        flowPane.setOnKeyReleased(event -> responseMaster());
// 
//        primaryStage.setScene(new Scene(flowPane));
//        primaryStage.show();
//    }
 
    public String responseMaster() {
        voiceRecorder.closeCaptureAudio();
        String response = null;
        if (voiceRecognition.recognizeVoice()) {
            String resultText = voiceRecognition.getResultText();
            if (resultText.contains("停止")) {
				return null;
			}
            TulingCommunicationByText tulingCommunicationByText = new TulingCommunicationByText();
            response = tulingCommunicationByText.getResponse(resultText);
            if(!voiceCompose.getMP3ByText(response)){
            	return response;
            }else{
                voiceCompose.playMP3();
            }
        }
        return response;
    }
}