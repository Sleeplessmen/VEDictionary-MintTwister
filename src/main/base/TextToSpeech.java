package base;

import java.io.*;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;
import javax.sound.sampled.*;
public class TextToSpeech {

    public static void playWav(String filePath) {
        try {
            File audioFile = new File(filePath);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioInputStream.getFormat());

            try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                line.open(audioInputStream.getFormat());
                line.start();

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                    line.write(buffer, 0, bytesRead);
                }

                line.drain();
                line.stop();
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void generate(String text) throws Exception {

        VoiceProvider tts = new VoiceProvider("fe623bc3a3fc42a1aec2fd5c1124e635");
        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream("voice.wav");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
    }
    public static void main(String args[]) throws Exception {
        generate("nigger");
        playWav("voice.wav");
    }
}