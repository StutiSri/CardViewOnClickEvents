package com.example.stutisrivastava.cardview.adapter;

/**
 * Created by stutisrivastava on 31/10/15.
 */

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stutisrivastava.cardview.R;
import com.example.stutisrivastava.cardview.pojo.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * To create an adapter that a RecyclerView can use, you must extend RecyclerView.Adapter.
 * This adapter follows the view holder design pattern, which means that it you to define a custom class that
 * extends RecyclerView.ViewHolder. This pattern minimizes the number of calls to the costly findViewById method.
 * Earlier in this example, we defined the XML layout for a CardView that represents a person.
 * We are going to reuse that layout now. Inside the constructor of our custom ViewHolder,
 * initialize the views that belong to the items of our RecyclerView.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    /**
     *  Add a constructor to the custom adapter so that it has a handle to the data that the RecyclerView displays.
     *  As we will be giving our data as List of Person objects, use the following code:-
     *  You can use any other data structure as per your requirement.
     */
    List<Person> persons;
    private SparseBooleanArray mSelectedItems;

    public RVAdapter(List<Person> persons){
        this.persons = persons;
        mSelectedItems = new SparseBooleanArray();
    }

    /**
     * Next, override the onCreateViewHolder method. As its name suggests, this method is called when the custom
     * ViewHolder needs to be initialized. We specify the layout that each item of the RecyclerView should use.
     * This is done by inflating the layout using LayoutInflater, passing the output to the constructor of the
     * custom ViewHolder.
     */

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_layout, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    /**
     * Override the onBindViewHolder to specify the contents of each item of the RecyclerView.
     * This method is very similar to the getView method of a ListView's adapter. In our example, here's where
     * you have to set the values of the name, age, and photo fields of the CardView.
     */

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(persons.get(i).getName());
        personViewHolder.personAge.setText(persons.get(i).getAge());
        personViewHolder.personPhoto.setImageResource(persons.get(i).getPhotoId());
        if(mSelectedItems.get(i,false)){
            personViewHolder.rl.setBackgroundColor(Color.CYAN);
        }else{
            personViewHolder.rl.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    /**
     * Removes the item that currently is at the passed in position from the
     * underlying data set.
     *
     * @param position The index of the item to remove.
     */
    public void removeData(int position) {
        persons.remove(position);
        notifyItemRemoved(position);
    }

    public Person getItem(int pos){
        return persons.get(pos);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        RelativeLayout rl;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            rl = (RelativeLayout)itemView.findViewById(R.id.card_list_item);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    /**
     * This method adds or deletes items from mSelectedItems sparseBooleanArray depending upon whether
     * they are getting selected or deseledted.
     * @param pos is the position of selected or deselected item in the list
     * notifyItemChanged(pos) is used to refresh the particular card.
     */

    public void toggleSelection(int pos) {
        if (mSelectedItems.get(pos, false)) {
            mSelectedItems.delete(pos);
        }
        else {
            mSelectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    /**
     * Once we have completed the operations, mSelectedItems is cleared for new selections.
     */

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    /**
     * @return all the selected items from the mSelectedItems array
     */

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }

}
