package ar.edu.itba.fitness.buddy.model;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.function.Consumer;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.model.Cycle;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;

public class FullRoutine {
    private final ArrayList<FullCycle> cycles;
    private final int id;

    public FullRoutine(int id) {
        this.id = id;
        cycles = new ArrayList<>();
    }

    public void fillData(App app, LifecycleOwner owner, Runnable callback, Consumer<? super Resource<?>> fail) {
        Log.d("ID2", String.valueOf(id));
        app.getRoutineRepository().getRoutineCycles(id, 0, 10, null, null).observe(owner, (r) -> {
            if (r.getStatus() == Status.SUCCESS) {
                PagedList<Cycle> cyclePage = r.getData();
                if(cyclePage != null) {
                    ArrayList<Cycle> cycleList = (ArrayList<Cycle>) cyclePage.getContent();
                    fillAsync(cycleList, 0, callback, fail, app, owner);
                }
            } else {
                fail.accept(r);
            }
        });
    }

    public int getCycles() {
        return cycles.size();
    }

    public FullCycle getCycle(int idx) {
        if (idx < 0 || idx >= cycles.size())
            return null;

        return cycles.get(idx);
    }

    private void fillAsync(ArrayList<Cycle> cycleList, int idx, Runnable callback, Consumer<? super Resource<?>> fail, App app, LifecycleOwner owner) {
        if (idx >= cycleList.size()) {
            callback.run();
            return;
        }

        FullCycle cycle = new FullCycle(cycleList.get(idx));
        cycle.fillData(app, owner, () -> {
            cycles.add(cycle);
            fillAsync(cycleList, idx+1, callback, fail, app, owner);
        }, fail);
    }
}
