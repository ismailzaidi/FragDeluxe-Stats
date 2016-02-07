package com.fragdeluxestats.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fragdeluxestats.R;
import com.fragdeluxestats.activities.CompareActivity;
import com.fragdeluxestats.adapters.RankViewAdapter;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.model.ModelInterfaces;
import com.fragdeluxestats.model.ResourceReference;
import com.fragdeluxestats.model.Utility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class CompareDialogFragment extends DialogFragment implements ModelInterfaces.ItemClickListener, View.OnClickListener {

    private String[] list_limit = {"0/2", "1/2", "2/2"};
    private RankViewAdapter adapter;
    private TextStyleView compareTextCount;
    private RecyclerView rankRecyclerView;
    private EditText userSearchEditText;
    private ArrayList<Player> listOfSelected;
    private CompareListFetcher callback;
    private Button compareButton;
    private boolean isSelected;
    private Player userSteamdID;
    private ArrayList<Player> listOfPlayers;


    public interface CompareListFetcher {
        ArrayList<Player> CompareList();
    }

    public static CompareDialogFragment newInstance(ArrayList<Player> userSteamID) {
        CompareDialogFragment fragment = new CompareDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ResourceReference.COMPARE_USER_ID, userSteamID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable("list", listOfPlayers);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View compareView = inflater.inflate(R.layout.compare_view, container, false);
        if (savedInstanceState != null) {
            listOfPlayers = (ArrayList<Player>) savedInstanceState.getSerializable("list");
        } else {
            listOfPlayers = callback.CompareList();
        }
        rankRecyclerView = (RecyclerView) compareView.findViewById(R.id.recyclerView);
        compareTextCount = (TextStyleView) compareView.findViewById(R.id.userCounter);
        userSearchEditText = (EditText) compareView.findViewById(R.id.userIdEditText);
        compareButton = (Button) compareView.findViewById(R.id.compareButton);
        rankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Player> players = (ArrayList<Player>) getArguments().getSerializable(ResourceReference.COMPARE_USER_ID);
        userSteamdID = players.get(0);
        listOfSelected = new ArrayList<Player>();
        adapter = new RankViewAdapter(listOfPlayers, this);
        rankRecyclerView.setAdapter(adapter);
        compareButton.setOnClickListener(this);
        userSearchEditText.addTextChangedListener(textListener);

        return compareView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (CompareDialogFragment.CompareListFetcher) context;
    }

    @Override
    public void onClickListener(View view, int position, LinearLayout background) {
        Log.v("rank", "Player: " + adapter.getObjectItem(position).getName());
        Player player = adapter.getObjectItem(position);
        int size = listOfSelected.size();
        if (size < 3) {
            Log.v("PLAYER_STAT", "Player IsSelected: " + player.isSelected());
            if (!player.equals(userSteamdID)) {
                if (player.isSelected()) {
                    player.setSelected(false);
                    background.setSelected(false);
                } else {
                    player.setSelected(true);
                    background.setSelected(true);
                }
                boolean isSelected = player.isSelected();
                updateUser(isSelected, player);
            }
        }
    }

    private void updateUser(boolean isSelected, Player player) {

        int size = listOfSelected.size();
        String parentText = compareTextCount.getText().toString();
        if (isSelected) {
            if (size < 2) {
                if (!player.equals(userSteamdID)) {
                    listOfSelected.add(player);
                    for (int i = 0; i < list_limit.length; i++) {
                        if (parentText.equals(list_limit[i]) && !parentText.equals(list_limit[list_limit.length - 1])) {
                            compareTextCount.setText(list_limit[i + 1]);
                            break;
                        }
                    }
                }
            }
        } else {
            boolean isExist = listOfSelected.contains(player) || listOfSelected.contains(userSteamdID);
            if (isExist) {
                listOfSelected.remove(player);
                for (int i = 0; i < list_limit.length; i++) {
                    if (parentText.equals(list_limit[i]) && !parentText.equals(list_limit[0])) {
                        compareTextCount.setText(list_limit[i - 1]);
                        break;
                    }
                }
            }
        }
        Log.v("ranksize", "Size: " + listOfSelected.size() + " Player Name: " + player.getName() + " Selected Value: " + isSelected);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == compareButton.getId()) {
            int size = listOfSelected.size();
            if (size > 0 && size <= 2) {
                if (Utility.isNetworkAvailable(getContext())) {
                    listOfSelected.add(userSteamdID);
                    Collections.reverse(listOfSelected);
                    Intent intent = new Intent(getActivity().getApplicationContext(), CompareActivity.class);
                    intent.putExtra(ResourceReference.COMPARE_ARRAY, listOfSelected);
                    startActivity(intent);
                    getDialog().dismiss();
                } else {
                    Utility.ShowInternetError(getContext());
                }
            }
        }
    }

    private TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String input = charSequence.toString();
            adapter.getFilter().filter(input);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
