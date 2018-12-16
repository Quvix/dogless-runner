package eu.quvix.doglessrunner;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SoundManager {
    private Map<Integer, MediaPlayer> sounds;
    private static SoundManager SELF = new SoundManager();
    private Integer soundtrack;
    private boolean soundEffectsEnabled = true;
    private boolean soundtrackEnabled = true;

    private SoundManager() {
        sounds = new ArrayMap<>();
    }

    public static SoundManager getInstance() {
        return SELF;
    }

    public void loadSounds(Context context) {
        Field[] ID_Fields = R.raw.class.getFields();
        int[] resArray = new int[ID_Fields.length];
        for (Field ID_Field : ID_Fields) {
            try {
                sounds.put(ID_Field.getInt(null), MediaPlayer.create(context, ID_Field.getInt(null)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void playSoundEffect(int id) {
        MediaPlayer sound = sounds.get(id);
        if(sound != null) {
            sound.setLooping(false);
            sound.start();
        }
    }

    public void playSoundtrack(int id) {
        MediaPlayer sound = sounds.get(id);
        if(sound != null) {
            if(soundtrack != null) {
                sounds.get(soundtrack).stop();
            }
            soundtrack = id;
            sound.setLooping(true);
            if(soundtrackEnabled) {
                sound.setVolume(0.4f, 0.4f);
                sound.start();
            }


        }
    }

    public void setSoundEffectsEnabled(boolean on) {
        soundEffectsEnabled = on;
        float volume = (soundEffectsEnabled ? 1 : 0);
        for(Map.Entry<Integer, MediaPlayer> e : sounds.entrySet()) {
            if(!e.getKey().equals(soundtrack)) {
                e.getValue().setVolume(volume, volume);
            }
        }
    }

    public void setSoundtrackEnabled(boolean on) {
        soundtrackEnabled = on;
        if(soundtrack != null) {
            if(soundtrackEnabled) {
                sounds.get(soundtrack).setVolume(0.4f, 0.4f);
                sounds.get(soundtrack).start();
            } else {
                sounds.get(soundtrack).pause();
            }
        }
    }

    public boolean toggleSoundEffectsVolume() {
        soundEffectsEnabled = !soundEffectsEnabled;
        setSoundEffectsEnabled(soundEffectsEnabled);
        return soundEffectsEnabled;
    }

    public boolean toggleSoundtracksVolume() {
        soundtrackEnabled = !soundtrackEnabled;
        setSoundtrackEnabled(soundtrackEnabled);
        return soundtrackEnabled;
    }
}
