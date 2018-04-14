package co.edu.ucc.todoapp.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.edu.ucc.todoapp.R;
import co.edu.ucc.todoapp.view.viewmodels.TaskViewModel;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.lblTask)
    TextView lblTask;

    @BindView(R.id.imgTask)
    ImageView imgTask;

    @BindView(R.id.chkDone)
    CheckBox chkDone;

    public TaskViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void render(TaskViewModel taskViewModel) {
        lblTask.setText(taskViewModel.getName());
    }
}
