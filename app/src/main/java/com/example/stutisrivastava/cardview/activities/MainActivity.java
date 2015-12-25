package com.example.stutisrivastava.cardview.activities;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.stutisrivastava.cardview.R;
import com.example.stutisrivastava.cardview.adapter.RVAdapter;
import com.example.stutisrivastava.cardview.pojo.Person;
import com.example.stutisrivastava.cardview.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity implements RecyclerView.OnItemTouchListener, View.OnClickListener
{

    private List<Person> mPersons;
    private RVAdapter adapter;
    private ActionMode mActionMode;
    RecyclerView rv;
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();

        //To obtain a handle to RecyclerView in your Activity,
        rv = (RecyclerView) findViewById(R.id.rv);

        //If you are sure that the size of the RecyclerView won't be changing, you can add the following to improve performance:
        rv.setHasFixedSize(true);

        /** Unlike a ListView, a RecyclerView needs a LayoutManager to manage the positioning of its items.
         You could define your own LayoutManager by extending the RecyclerView.LayoutManager class.
         However, in most cases, you could simply use one of the predefined LayoutManager subclasses:
            LinearLayoutManager
            GridLayoutManager
            StaggeredGridLayoutManager
         In this tutorial, I am going to use a LinearLayoutManager.
         This LayoutManager subclass will, by default, make your RecyclerView look like a ListView.**/
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        /**
         * initialize and use the adapter by calling the adapter's constructor and the RecyclerView's setAdapter method:
         */

        adapter = new RVAdapter(mPersons);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(this, new RecycleViewGestureListener());

    }

    // This method creates an ArrayList that has eight Person objects
        private void initializeData () {
            mPersons = new ArrayList<>();
            mPersons.add(new Person("Stuti Srivastava", "21 years old", R.drawable.ic_launcher));
            mPersons.add(new Person("Saumya Sinha", "23 years old", R.drawable.ic_launcher));
            mPersons.add(new Person("Hera Fatima", "23 years old", R.drawable.ic_launcher));
            mPersons.add(new Person("Shubham Srivastava", "23 years old", R.drawable.ic_launcher));
            mPersons.add(new Person("Ayushi Birla", "22 years old", R.drawable.ic_launcher));
            mPersons.add(new Person("Milina", "25 years old", R.drawable.ic_launcher));
            mPersons.add(new Person("Pankhudi", "19 years old", R.drawable.ic_launcher));
            mPersons.add(new Person("Satyyam Srivastava", "25 years old", R.drawable.ic_launcher));
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu_activity_main, menu);
            return true;
        }

        /**
         * Called each time the action mode is shown. Always called after onCreateActionMode, but
         * may be called multiple times if the mode is invalidated.
         **/
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        /**
         * Called when the user selects a contextual menu item
         */

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.MENU_DEL_MODES:
                    List<Integer> selectedItemPositions = adapter.getSelectedItems();
                    /**
                     * We are going to sort the positions so that random selections like 4,3,8,1,5 do not create
                     * problem for us. Next we will remove items starting from the back so as not to disturb the positions
                     * of higher index elements when a lower index element is removed i.e in the sequence 1,3,4,5,8
                     * if we start removing from the start after removing item at pos 1, all the items to the right of this index
                     * would have moved one position left. So now if we remove the element at index 3, we actually would be removing
                     * the element that was at position 4 when the selections were made.
                     */
                    Collections.sort(selectedItemPositions);
                    int i = selectedItemPositions.size()-1;
                    for (;i>=0;i--)
                        adapter.removeData(selectedItemPositions.get(i));
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        /**
         * Called when the user exits the action mode
         * @param mode - mode to be destroyed
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            adapter.clearSelections();
        }
    };

    /**
     * This method calls the onTouchEvent(e) of gestureDetector when a motion event is detected
     * @param rv recycler view on which the touch event took place
     * @param e motion event that took place
     * @return
     */

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onClick(View view) {
        /**
         * Best practice would be to ensure that the view has the same is as R.id.card_list_item
         */
            int idx = rv.getChildPosition(view);
            /**
             * If the selection process is going on then use myToggleSelection method to toggle the selections
             * made by the user
             */
            if (mActionMode != null) {
                myToggleSelection(idx);
                return;
            }
            /**
             * else, identify the clicked item, get its info and start a new activity using that information.
             */
            Person data = adapter.getItem(idx);

            Intent startIntent = new Intent(this, PersonDetailsActivity.class);
            startIntent.putExtra(Constants.KEY_NAME,data.getName());
            startIntent.putExtra(Constants.KEY_AGE,data.getAge());

            this.startActivity(startIntent);
    }

    private class RecycleViewGestureListener extends GestureDetector.SimpleOnGestureListener{

        /**
         * Identify the view that was clicked and then call onCick(View) on it
         * @param e
         * @return
         */

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = rv.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        /**
         * Activate multiple selections.
         * Toggle selection of this view using method myToggleSelection(pos)
         * @param e
         */

        public void onLongPress(MotionEvent e) {
            View view = rv.findChildViewUnder(e.getX(), e.getY());
            if (mActionMode != null) {
                return;
            }
            mActionMode = startActionMode(mActionModeCallback);
            int idx = rv.getChildPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }

    /**
     * Method belongs to MainActivity
     * It adds or removes items from mSelectedItems arrayusing the adapter method toggleSelection.
     * It also sets the title of ContextMenuBar as 1 selected, 2 selected etcbased on the number of selections
     * @param idx
     */

    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
        String title = getString(R.string.selected_count, adapter.getSelectedItemCount());
        mActionMode.setTitle(title);
    }

}
