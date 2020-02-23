package com.tencent.qqgamemi.mgc.step;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class InitializeStepTable {
    private List<Step<Context>> mInitSteps = new ArrayList();
    private StepContainer<Context> mStepContainer = new StepContainer<>();

    public void addStep(IntializeStep step) {
        this.mInitSteps.add(step);
    }

    public void runAll(Context context) {
        this.mStepContainer.setStepParams(context);
        this.mStepContainer.setSteps(this.mInitSteps);
        this.mStepContainer.runSteps();
    }

    public static abstract class IntializeStep<INSTANCE> extends Step<Context> {
        private INSTANCE object;

        public IntializeStep setObject(INSTANCE object2) {
            this.object = object2;
            return this;
        }

        /* access modifiers changed from: protected */
        public INSTANCE getObject() {
            return this.object;
        }

        /* access modifiers changed from: protected */
        public Context getContext() {
            return ((Context[]) getPARAM())[0];
        }
    }
}
