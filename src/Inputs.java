/**
 * Created by Måns on 2016-11-16.
 */
public enum Inputs {
    //analog sensors
    JOYX (0),
    JOYY (1),
    DIAL (2),
    LIGHT (3),
    SLIDER (6),
    //Digital
    SWITCHER (0);

    public final byte value;

    Inputs(int value) {
        this.value = (byte) value;
    }
}
