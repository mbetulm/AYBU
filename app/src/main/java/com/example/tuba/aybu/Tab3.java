package com.example.tuba.aybu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3 extends Fragment {
    private ListView lv;
    public ArrayList liste = new ArrayList();
    public ArrayList<String> hrefList2 = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private static String URL="http://ybu.edu.tr/muhendislik/bilgisayar/";
    public static String selectedItem = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_tab1,container,false);

        lv=(ListView)view.findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,liste);

        new Tab3.VeriGetir3().execute();
        // Inflate the layout for this fragment

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = parent.getItemAtPosition(position).toString();

                Uri uri =Uri.parse(URL+hrefList2.get(position));
                Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);








            }

        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class VeriGetir3 extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected Void doInBackground(Void... params) {

            /*try {
                Document doc = Jsoup.connect(URL).get();
                Elements news= doc.select("div.cncItem");
                Elements news2= doc.select("div.cnButton");

                for(int i=0; i<3;i++){
                    liste.add(news.get(i).text());

                }
                for(int a=0; a<1;a++){
                    liste.add(news2.get(a).text());

                }


            } catch (IOException e) {
                e.printStackTrace();
            }*/

            try {
                Document doc = Jsoup.connect(URL).get();
                //Elements ann= doc.select("div.cncItem");
                Elements new2= doc.select("div.contentNews div.cnContent div.cncItem ");
                Elements hrList=doc.select("div.contentNews div.cnContent div.cncItem a");
                for(int i=0; i<hrList.size();i++){
                    liste.add(new2.get(i).text());
                    hrefList2.add(hrList.get(i).attr("href"));
                }

                /*for(int a=0; a<1;a++){
                    liste.add(ann2.get(a).text());

                }*/


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lv.setAdapter(adapter);

        }
    }
}
