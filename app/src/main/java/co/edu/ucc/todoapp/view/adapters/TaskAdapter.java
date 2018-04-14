package co.edu.ucc.todoapp.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.edu.ucc.todoapp.R;
import co.edu.ucc.todoapp.view.viewmodels.TaskViewModel;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<TaskViewModel> lstTaskViewModel;

    public TaskAdapter() {
        lstTaskViewModel = new ArrayList<>();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskViewModel taskViewModel = lstTaskViewModel.get(position);
        holder.render(taskViewModel);
    }

    @Override
    public int getItemCount() {
        return lstTaskViewModel.size();
    }

    public void addLstTask(Collection<TaskViewModel> collection) {
        lstTaskViewModel.clear();
        lstTaskViewModel.addAll(collection);
    }

}
