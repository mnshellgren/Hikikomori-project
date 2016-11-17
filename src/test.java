import com.phidgets.InterfaceKitPhidget;
import com.phidgets.*;
import com.phidgets.event.*;

/**
 * Created by Måns on 2016-11-16.
 */
public class test {

    private double joyX = 0;
    private double joyY = 0;
    private double slidervalue = 0;
    private double dial = 0;
    private double light = 0;
    private boolean switcher = false;
    private boolean[] lamps = new boolean[7];
    private double acc = 0;

    public static final void main(String args[]) throws Exception {

        InterfaceKitPhidget ik = new InterfaceKitPhidget();
        test t = new test();

        ik.openAny();
        ik.waitForAttachment();

        TextLCDPhidget screen = new TextLCDPhidget();

        screen.openAny();
        screen.waitForAttachment();
        screen.setScreenSize(TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20);
        screen.setBacklight(true);
        screen.setBrightness(200);

        t.joyX = ik.getSensorValue(Inputs.JOYX.value);
        t.joyY = ik.getSensorValue(Inputs.JOYY.DIAL.value);
        t.light = ik.getSensorValue(Inputs.LIGHT.value);
        t.slidervalue = ik.getSensorValue(Inputs.SLIDER.value);
        t.switcher = ik.getInputState(Inputs.SWITCHER.value);

        screen.setDisplayString(0,Double.toString(t.dial));
        screen.setDisplayString(1,Double.toString(t.light));
        screen.setDisplayString(2,Double.toString(t.slidervalue));

        ik.addInputChangeListener(new InputChangeListener() {
            @Override
            public void inputChanged(InputChangeEvent ie) {
                switch(ie.getIndex()) {
                    case 0:
                        t.switcher = ie.getState();
                        break;

                }
            }
        });

        AccelerometerPhidget acc = new AccelerometerPhidget();

        acc.openAny();
        acc.waitForAttachment();

        acc.addAccelerationChangeListener(new AccelerationChangeListener() {
            @Override
            public void accelerationChanged(AccelerationChangeEvent ae) {
                try{
                    if (ae.getIndex() == 2){
                        double acc = Math.floor(ae.getValue() * 10);

                            String toWrite = Double.toString((acc));
                            screen.setDisplayString(3, Double.toString(ae.getValue()));
                            t.acc = acc;

                    }
                }catch (PhidgetException p){
                    p.printStackTrace();
                }
            }
        });
        ik.addSensorChangeListener(new SensorChangeListener() {
            @Override
            public void sensorChanged(SensorChangeEvent se)
            {
                switch(se.getIndex()) {
                    case 0: t.joyX = se.getValue();
                        System.out.println("x value: "+ t.joyX);
                        break;
                    case 1: t.joyY = se.getValue();
                        System.out.println("y value: "+ t.joyY);
                        break;
                    case 2: t.dial = se.getValue();
                        System.out.println("dial: " + t.dial);
                        break;
                    case 3: t.light = se.getValue();
                        System.out.println("light: "+t.light);
                        break;
                    case 6:
                        t.slidervalue = se.getValue();
                        System.out.println("slider "  + t.slidervalue);
                        break;
                    default:
                        System.err.print("unknown error");
                        System.exit(0);

                }
            }

            //System.out.println(se.getValue());
            //System.out.println(se.getIndex());
        });
        //screen.setDisplayString(0, "abcdefghijklmnopqrst");

        while(t.slidervalue > 800 || t.slidervalue < 200){
            ik.setOutputState(0,t.switcher);
            screen.setDisplayString(0,Double.toString(t.dial));
            screen.setDisplayString(1,Double.toString(t.light));
            screen.setDisplayString(2,Double.toString(t.slidervalue));

        }

        System.out.println("good bye");

        ik.close();
        ik = null;
    }

}
