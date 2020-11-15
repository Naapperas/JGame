package jGame.core.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

import jGame.core.sound.SoundPlayer.SoundType;
import jGame.core.utils.MathUtils;
import jGame.logging.ProgramLogger;

/**
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 *
 */
public class SoundPlayer {

	public enum SoundType {

		SFX(2);

		int type;

		SoundType(int type) {
			this.type = type;
		}

		public int getType() {
			return this.type;
		}

	}

	public static int MAIN_VOLUME = 0;
	public static int MUSIC_VOLUME = 1;
	public static int SOUNF_FX_VOLUME = 2;

	public static float[] volumeLevels = { 1.0f, 1.0f, 1.0f };

	private SoundPlayer() {
		// make uninstantiable
	}

	/**
	 * 
	 * @param soundIdentifier
	 * @param type
	 * @since 2.0.0
	 */
	public static void playSound(String soundIdentifier, SoundType type) {

		byte[] soundData = SoundStore.getSound(soundIdentifier);
		AudioFormat soundFormat = SoundStore.getFormat(soundIdentifier);
		
		SoundPlayerThread.startNewPlayer(soundData, soundFormat, type);

	}

	/**
	 * 
	 * @param newVolume
	 * @param volumeID
	 * @since 2.0.0
	 */
	public static void changeVolumeLevel(float newVolume, int volumeID) {
		volumeLevels[volumeID] = newVolume;
	}

}

/**
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 *
 */
class SoundPlayerThread {

	private static int threadCount = 0;

	/**
	 * 
	 * @param soundData
	 * @param soundFormat
	 * @param type
	 * @since 2.0.0
	 */
	public static void startNewPlayer(final byte[] soundData, final AudioFormat soundFormat, SoundType type) {

		// TODO: make it so sound is played right after this method is called (right
		// now, the sound might not play exactly when this is called, undesirable
		// behavior)

		Runnable r = () -> {

			DataLine.Info info = new DataLine.Info(SourceDataLine.class, soundFormat);
			try {
				SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
				sourceLine.open(soundFormat);

				sourceLine.start();

				FloatControl gainControl = (FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN);

				gainControl.setValue(MathUtils.clamp(SoundPlayer.volumeLevels[type.getType()], gainControl.getMinimum(),
						gainControl.getMaximum()));

				sourceLine.write(soundData, 0, soundData.length);

				if (sourceLine.isOpen())
					sourceLine.drain();
				sourceLine.close();
			} catch (Exception e) {
				ProgramLogger.writeErrorLog(e);
			}

			return;

		};

		Thread t = new Thread(r, "SoundPlayer" + ++threadCount);

		t.setDaemon(true);

		t.start();
	}
}
