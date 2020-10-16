package jGame.core.sound;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.SwingWorker;

import jGame.logging.ProgramLogger;

/**
 * Worker thread class responsible for playback of audio data.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 * @see SwingWorker
 */
public class SoundWorker extends SwingWorker<String, Object> {

	// The size of the audio buffer.
	private final int BUFFER_SIZE = 128000;

	/**
	 * The URL of the file.
	 * 
	 * @since 1.0.0
	 */
	private URL soundFileURL;

	/**
	 * The <code>InputStream</code> for audio data.
	 * 
	 * @since 1.0.0
	 */
	private AudioInputStream audioStream;

	/**
	 * The format of audio data.
	 * 
	 * @since 1.0.0
	 */
	private AudioFormat audioFormat;

	/**
	 * The data line to write audio data to.
	 * 
	 * @since 1.0.0
	 */
	private SourceDataLine sourceLine;

	/**
	 * The name of the clip to play
	 * 
	 * @since 1.0.0
	 */
	private String clipName;

	@Override
	protected String doInBackground() throws Exception {

		// standard audio playback in Java

		sourceLine.start();

		int numBytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		while (numBytesRead != -1) {
			try {
				numBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				ProgramLogger.writeErrorLog(e, "Couldn't read audio data!");
			}
			if (numBytesRead >= 0) {
				int numBytesWritten = sourceLine.write(abData, 0, numBytesRead);
				ProgramLogger.writeLog("Played " + numBytesWritten + " bits of sound from " + clipName);
			}
		}

		if (sourceLine.isOpen())
			sourceLine.drain();
		sourceLine.close();

		ProgramLogger.writeLog("Finnished playing audio data!");
		return "Finnished playing audio data!";
	}

	@Override
	protected void done() {
		this.cancel(false);
	}

	/**
	 * Constructs a new <code>SoundWorker</code> to play the audio data represented
	 * by <code>clipName</code>.
	 *
	 * @param clipPath the name of the clip to play.
	 * @since 1.0.0
	 */
	public SoundWorker(File clipPath) {

		this.clipName = clipPath.getName();

		try {
			soundFileURL = clipPath.toURI().toURL();// SoundWorker.class.getResource("/sounds/" + clipName);
		} catch (Exception e) {
			ProgramLogger.writeErrorLog(e, "Couldn't create Sound Worker!!!!!!");
			return;
		}

		try {
			audioStream = AudioSystem.getAudioInputStream(soundFileURL);
		} catch (Exception e) {
			ProgramLogger.writeLog(Level.WARNING, "Error happened when getting AudioStream!!!!!");
			return;
		}

		audioFormat = audioStream.getFormat();

		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			ProgramLogger.writeErrorLog(e, "Error opening/getting audio line!!");
		} catch (Exception e) {
			ProgramLogger.writeErrorLog(e);
		}
	}

	/**
	 * Returns a given control from the source data line.
	 * 
	 * @param controlType the type of control to query
	 * @return a {@link Control} object representing a given setting of the source
	 *         data line
	 * @since 1.1.0
	 */
	public Control getControl(Control.Type controlType) {
		return sourceLine.getControl(controlType);
	}

}