package com.nds.baking.king.views.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeStepModel;

import java.util.List;

/**
 * Created by Namrata Shah on 5/21/2017.
 */

public class RecipeStepsDescriptionAdapter extends RecyclerView.Adapter<RecipeStepsDescriptionAdapter.RecipeStepsViewHolder> {

    private List<RecipeStepModel> mRecipeStepModelList;
    private Context mContext;
    private AdapterView.OnItemClickListener stepClickListener;

    public RecipeStepsDescriptionAdapter(Context context, List<RecipeStepModel> stepsList, AdapterView.OnItemClickListener clickListener){
        this.mContext = context;
        this.mRecipeStepModelList = stepsList;
        this.stepClickListener = clickListener;
    }

    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_steps_row, parent, false);
        RecipeStepsViewHolder recipeStepsViewHolder = new RecipeStepsViewHolder(view);
        return recipeStepsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int position) {
        if(position == 0){
            if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
                holder.recipeStep.setTextAppearance(mContext,R.style.ingredient_title);
            }else{
                holder.recipeStep.setTextAppearance(R.style.ingredient_title);
            }
        }
        holder.recipeStep.setText(mRecipeStepModelList.get(position).getShortDescription());
    }


    @Override
    public int getItemCount() {
        if(mRecipeStepModelList != null)
            return mRecipeStepModelList.size();
        return 0;
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeStep;

        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            recipeStep = (TextView)itemView.findViewById(R.id.recipeStep);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            stepClickListener.onItemClick(null, v, getAdapterPosition(), v.getId());
        }
    }
}
