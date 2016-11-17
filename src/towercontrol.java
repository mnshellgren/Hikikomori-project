import com.phidgets.InterfaceKitPhidget;
import com.phidgets.*;
import com.phidgets.event.*;

public class towercontrol {

    public static final void main(String args[]) throws Exception {

        AdvancedServoPhidget servoX = new AdvancedServoPhidget();

        servoX.openAny();
        servoX.waitForAttachment();

        servoX.setPosition(0,110);
        servoX.setEngaged(0,true);

        AccelerometerPhidget acc = new AccelerometerPhidget();
        acc.openAny();
        acc.waitForAttachment();

        AdvancedServoPhidget servoY = new AdvancedServoPhidget();


        servoY.openAny();
        servoY.waitForAttachment();
        System.out.println(servoY.getPositionMax(0));

            acc.addAccelerationChangeListener(new AccelerationChangeListener() {
                @Override
                public void accelerationChanged(AccelerationChangeEvent ae) {
                    if (ae.getIndex() == 1) {
                        double acc = ae.getValue() * 220;

                        try {
                            servoX.setPosition(0, acc);
                        } catch (PhidgetException e) {
                            e.printStackTrace();
                        }

                    }
                    if (ae.getIndex() == 0) {
                        double acc = (1 + ae.getValue()) * 110;

                        String toWrite = Double.toString((acc));
                        System.out.println(toWrite);
                        try {
                            servoY.setPosition(0, acc);
                        } catch (PhidgetException e) {
                            e.printStackTrace();
                        }
                    }
                }
                });
                System.in.read();
            }

    }

