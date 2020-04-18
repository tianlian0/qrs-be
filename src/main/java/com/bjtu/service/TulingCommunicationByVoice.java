package com.bjtu.service;

public class TulingCommunicationByVoice {
    private VoiceCompose voiceCompose = new VoiceCompose();
    private VoiceRecognition voiceRecognition = new VoiceRecognition();
    public VoiceRecorder voiceRecorder = new VoiceRecorder();
 
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