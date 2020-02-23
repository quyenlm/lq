package com.tencent.qqgamemi.mgc.step;

import com.tencent.component.utils.log.LogUtil;
import java.util.ArrayList;
import java.util.List;

public class StepContainer<PARAM> {
    static final String LOG_TAG = "StepContainer";
    private PARAM[] mParams;
    private List<Step<PARAM>> mSteps = new ArrayList();
    private long startTime;

    public void setSteps(List<Step<PARAM>> steps) {
        if (steps == null) {
            throw new NullPointerException("you set a null step list!");
        }
        this.mSteps = steps;
    }

    public void setStepParams(PARAM... params) {
        this.mParams = params;
    }

    public void allSteps(List<Step<PARAM>> steps) {
        this.mSteps.addAll(steps);
    }

    public void addStep(Step<PARAM> step) {
        this.mSteps.add(step);
    }

    public void runSteps() {
        this.startTime = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        for (Step<PARAM> step : this.mSteps) {
            step.execute(this.mParams);
            builder.append(step.getExecutionInfo() + "\n");
        }
        LogUtil.i(LOG_TAG, "StepContainer: all steps finished, duration(ms)=" + (System.currentTimeMillis() - this.startTime) + "\n" + ", details:\n" + builder);
    }
}
