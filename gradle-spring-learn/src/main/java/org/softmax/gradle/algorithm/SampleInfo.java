package org.softmax.gradle.algorithm;

/**
 * @author Jarvis
 */
public class SampleInfo {

    public double[][] features;
    public double[] targets;
    public double[] levels;

    public SampleInfo(double[][] features, double[] targets, double[] level) {
        this.features = features;
        this.targets = targets;
        this.levels = level;
    }

    public double[] getLevels() {
        return levels;
    }

    public void setLevels(double[] levels) {
        this.levels = levels;
    }

    public double[][] getFeatures() {
        return features;
    }

    public void setFeatures(double[][] features) {
        this.features = features;
    }

    public double[] getTargets() {
        return targets;
    }

    public void setTargets(double[] targets) {
        this.targets = targets;
    }
}
