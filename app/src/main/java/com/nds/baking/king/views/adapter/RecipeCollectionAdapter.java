package com.nds.baking.king.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeModel;

import java.util.List;

/**
 * Created by Namrata Shah on 5/10/2017.
 */

public class RecipeCollectionAdapter extends RecyclerView.Adapter<RecipeCollectionAdapter.RecipeCollectionViewHolder> {

    private List<RecipeModel> mRecipeModels;
    private Context mContext;
    private AdapterView.OnItemClickListener cellClickListener;

    public RecipeCollectionAdapter(Context context, List<RecipeModel> recipeModelList, AdapterView.OnItemClickListener clickListener) {
        this.mContext = context;
        this.mRecipeModels = recipeModelList;
        this.cellClickListener = clickListener;
    }

    @Override
    public RecipeCollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View gridCellView = layoutInflater.inflate(R.layout.recipe_grid_cell, parent, false);
        return new RecipeCollectionViewHolder(gridCellView);
    }

    @Override
    public void onBindViewHolder(RecipeCollectionViewHolder holder, int position) {
        RecipeModel recipeModel = mRecipeModels.get(position);
        holder.mRecipeTitleTextView.setText(recipeModel.getRecipeName());
        if(recipeModel.isImgURL())
            Glide.with(mContext).load(recipeModel.getRecipeImgURL()).error(R.drawable.ic_launcher).into(holder.mRecipeIcon);
    }

    @Override
    public int getItemCount() {
        if (mRecipeModels == null)
            return 0;
        else
            return mRecipeModels.size();
    }

    public class RecipeCollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mRecipeTitleTextView;
        ImageView mRecipeIcon;

        public RecipeCollectionViewHolder(View itemView) {
            super(itemView);
            mRecipeTitleTextView = (TextView) itemView.findViewById(R.id.recipeName);
            mRecipeIcon = (ImageView) itemView.findViewById(R.id.recipeIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cellClickListener.onItemClick(null, v, getAdapterPosition(), v.getId());
        }
    }
}
