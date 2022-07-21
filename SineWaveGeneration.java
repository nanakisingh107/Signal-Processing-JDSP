import com.github.psambit9791.jdsp.filter.Butterworth;
import com.github.psambit9791.jdsp.misc.Plotting;
import com.github.psambit9791.jdsp.signal.Generate;
import com.github.psambit9791.jdsp.transform.DiscreteFourier;
import com.github.psambit9791.jdsp.transform._Fourier;

import java.util.Arrays;

public class SineWaveGeneration {
    public static void main(String[] args) {
        int Fs = 100; // total sample numbers
        double[] SampleNumber = new double[Fs]; //creating time vector
        for (int j = 0; j < Fs; j++) {
            SampleNumber[j] = j;
        }
        System.out.println("Sample Numbers:");
        System.out.println(Arrays.toString(SampleNumber));

        //GENERATING SINE WAVES WITH 3 DIFFERENT FREQUENCIES
        Generate gp1 = new Generate(0, 1, Fs);
        int f1 = 7; //frequency of signal 1
        double[] out1 = gp1.generateCosineWave(f1);

        int f2 = 12; //frequency of signal 2
        Generate gp2 = new Generate(0, 1, Fs);
        double[] out2 = gp2.generateSineWave(f2);

        int f3 = 19; //frequency of signal 3
        Generate gp3 = new Generate(0, 1, Fs);
        double[] out3 = gp3.generateSineWave(f3);


        //CREATING COMPOSITE SIGNAL
        double[] result = new double[out1.length];

        for (int i = 0; i < out1.length; ++i) {
            result[i] = out1[i] + out2[i] + out3[i];
        }
        System.out.println("Composite Signal:");
        System.out.println(Arrays.toString(result));

        //PLOTTING COMPOSITE SIGNAL
        int width = 600;
        int height = 300;
        String title = "Composite Signal - Time Domain";
        String x_axis = "Time";
        String y_axis = "Signal";
        Plotting fig0 = new Plotting(width, height, title, x_axis, y_axis);
        fig0.initialisePlot();
        fig0.addSignal("Composite Signal", SampleNumber, result, false);
        fig0.plot();

        //FOURIER TRANSFORM
        _Fourier ft = new DiscreteFourier(result);
        boolean onlyPositive = false;
        ft.transform();
        double[] mag_result = ft.getMagnitude(onlyPositive); //Full Absolute

        //PLOTTING COMPOSITE SIGNAL FREQUENCY DOMAIN
        String title1 = "Composite Signal - Frequency Domain";
        Plotting fig1 = new Plotting(width, height, title1, x_axis, y_axis);
        fig1.initialisePlot();
        fig1.addSignal("Composite Signal", SampleNumber, mag_result, false);
        fig1.plot();

        //FILTERING
        int order = 3; //order of the filter
        int lowCutOff = 10; //Lower Cut-off Frequency
        int highCutOff = 15; //Higher Cut-off Frequency
        Butterworth flt = new Butterworth(result, Fs); //signal is of type double[]
        double[] filtered_signal = flt.bandPassFilter(order, lowCutOff, highCutOff); //get the result after filtering

        //PLOTTING FILTERED SIGNAL
        String title2 = "Filtered Signal - Time Domain";
        Plotting fig2 = new Plotting(width, height, title2, x_axis, y_axis);
        fig2.initialisePlot();
        fig2.addSignal("Filtered Signal", SampleNumber, filtered_signal, false);
        //fig2.addSignal("Composite Signal", SampleNumber, result, false);
        fig2.plot();

        //FOURIER TRANSFORM of FILTERED SIGNAL
        _Fourier ft2 = new DiscreteFourier(filtered_signal);
        ft2.transform();
        double[] mag_filtered = ft2.getMagnitude(onlyPositive); //Full Absolute

        //PLOTTING FILTERED SIGNAL
        String title3 = "Filtered Signal - Frequency Domain";
        Plotting fig3 = new Plotting(width, height, title3, x_axis, y_axis);
        fig3.initialisePlot();
        fig3.addSignal("Filtered Signal", SampleNumber, mag_filtered, false);
        //fig3.addSignal("Composite Signal", SampleNumber, mag_result, false);
        fig3.plot();
    }
}