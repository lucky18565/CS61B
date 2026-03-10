package gh2;

import java.io.File;

/**
 * Plays a local MIDI file for Secret Base using GuitarPlayer.
 *
 * Put your MIDI at project root as "secret_base.mid",
 * or pass a custom path as the first command-line argument.
 */
public class SecretBase {
    public static void main(String[] args) {
        File midiFile;
        if (args.length > 0) {
            midiFile = new File(args[0]);
        } else {
            File[] candidates = new File[] {
                new File("secret_base.mid"),
                new File("secrat base.mid"),
                new File("src/gh2/secret_base.mid"),
                new File("src/gh2/secrat base.mid")
            };

            midiFile = null;
            for (File candidate : candidates) {
                if (candidate.exists()) {
                    midiFile = candidate;
                    break;
                }
            }
        }

        if (midiFile == null || !midiFile.exists()) {
            System.out.println("MIDI file not found.");
            System.out.println("Please provide a valid MIDI path, e.g.: ");
            System.out.println("java gh2.SecretBase path/to/secret_base.mid");
            return;
        }

        GuitarPlayer player = new GuitarPlayer(midiFile);
        player.play();
    }
}
