package com.nds.baking.king.net.tos;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Namrata Shah on 5/8/2017.
 */
/*transfer object for ingredient
* */
public class Ingredient {

    @SerializedName("quantity")
    private int quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredientName;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (quantity != that.quantity) return false;
        if (!measure.equals(that.measure)) return false;
        return ingredientName.equals(that.ingredientName);
    }

    @Override
    public int hashCode() {
        int result = quantity;
        result = 31 * result + measure.hashCode();
        result = 31 * result + ingredientName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}
