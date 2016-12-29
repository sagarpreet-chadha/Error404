package thuglife.teamerror404;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lenovo on 29-12-2016.
 */
public class ItemResponse implements Serializable {

    @Expose
    @SerializedName("ItemsAvailable")
    ArrayList<ItemAvailable> itemAvailableArrayList;

    public ArrayList<ItemAvailable> getItemAvailableArrayList() {
        return itemAvailableArrayList;
    }

    public void setItemAvailableArrayList(ArrayList<ItemAvailable> itemAvailableArrayList) {
        this.itemAvailableArrayList = itemAvailableArrayList;
    }

    @Override
    public String toString() {
        return "ItemResponse{" +
                "itemAvailableArrayList=" + itemAvailableArrayList +
                '}';
    }

    public class ItemAvailable implements Serializable{
        @Expose
        String instance_id;
        @Expose
        String category;
        @Expose
        String name;
        @Expose
        String shop;

        @Override
        public String toString() {
            return "ItemAvailable{" +
                    "instance_id='" + instance_id + '\'' +
                    ", category='" + category + '\'' +
                    ", name='" + name + '\'' +
                    ", shop='" + shop + '\'' +
                    '}';
        }

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
