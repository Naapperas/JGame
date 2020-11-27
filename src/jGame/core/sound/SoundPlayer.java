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

	/**
	 * 
	 * @author nunoa
	 *
	 */
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

	protected static float[] volumeLevels = { 1.0f, 1.0f, /* SFX */1.0f };
	static boolean hasSound = true;

	private SoundPlayer() {
		// make uninstantiable
	}

	/**
	 * Plays the given sound using the given sound type(used only for volume level
	 * change). The sound is only played once i.e. it is not looped.
	 * 
	 * @param soundIdentifier the name of the sound to play
	 * @param type            the type of sound to play (SFX, BackgroundTheme, etc)
	 * @since 2.0.0
	 */
	public static void playSound(String soundIdentifier, SoundType type) {
		playSound(soundIdentifier, type, false);
	}

	/**
	 * Plays the given sound using the given sound type(used only for volume level
	 * change). The sound may be looped or not, according to {@code looping}.
	 * 
	 * @param soundIdentifier the name of the sound to play
	 * @param type            the type of sound to play (SFX, BackgroundTheme, etc)
	 * @param looping         weather this sound data should be looped or not
	 * @since 2.0.0
	 */
	public static void playSound(String soundIdentifier, SoundType type, boolean looping) {

		if (!hasSound) {
			ProgramLogger.writeLog("No sounds avaliable to play");
			return;
		}

		byte[] soundData = SoundStore.getSound(soundIdentifier);
		AudioFormat soundFormat = SoundStore.getFormat(soundIdentifier);
		
		SoundPlayerThread.startNewPlayer(soundData, soundFormat, type, looping);

	}

	/**
	 * Changes the volume level for the given type of sound.
	 * 
	 * @param newVolume the new volume to use
	 * @param volumeID  the volume type
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
	 * @param looping
	 * @since 2.0.0
	 */
	public static void startNewPlayer(final byte[] soundData, final AudioFormat soundFormat, SoundType type,
			boolean looping) {

		// TODO: make it so sound is played right after this method is called (right
		// now, the sound might not play exactly when this is called, undesirable
		// behavior)

		Runnable r = () -> {

			ProgramLogger.writeLog("Playing audio!");

			try {
				SourceDataLine sourceLine = (SourceDataLine) AudioSystem
						.getLine(new DataLine.Info(SourceDataLine.class, soundFormat));
				sourceLine.open(soundFormat);

				sourceLine.start();
				
				do {
					FloatControl gainControl = (FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN);

					gainControl.setValue(MathUtils.clamp(SoundPlayer.volumeLevels[type.getType()],
							gainControl.getMinimum(), gainControl.getMaximum()));

					sourceLine.write(soundData, 0, soundData.length);

					if (sourceLine.isOpen())
						sourceLine.drain();
					
				} while (looping);

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
