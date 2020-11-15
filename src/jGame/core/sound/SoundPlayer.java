package jGame.core.sound;

import javax.sound.sampled.AudioFormat;

/**
 * 
 * @author nunoa
 *
 */
public class SoundPlayer {

	private SoundPlayer() {
		// make uninstantiable
	}

	/**
	 * 
	 * @param soundIdentifier
	 */
	public static void playSound(String soundIdentifier) {

		byte[] soundData = SoundStore.getSound(soundIdentifier);
		AudioFormat soundFormat = SoundStore.getFormat(soundIdentifier);
		
	}

}
