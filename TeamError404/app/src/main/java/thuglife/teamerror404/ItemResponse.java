package thuglife.teamerror404;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Lenovo on 29-12-2016.
 */
public class ItemResponse {
    @SerializedName("ItemsAvailable")
    ArrayList<ItemAvailable> itemAvailableArrayList;

    public ArrayList<ItemAvailable> getItemAvailableArrayList() {
        return itemAvailableArrayList;
    }

    private class ItemAvailable {
        String instance_id;
        String category;
        String name;
        String shop;

        public String getInstance_id() {
            return instance_id;
        }

        public void setInstance_id(String instance_id) {
            this.instance_id = instance_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShop() {
            return shop;
        }

        public void setShop(String shop) {
            this.shop = shop;
        }
    }
}
