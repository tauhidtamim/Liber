package com.inxs5859.liber.User;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.inxs5859.liber.HelperClasses.BookAdapter;
import com.inxs5859.liber.HelperClasses.BookInfo;
import com.inxs5859.liber.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    TextInputEditText searchBooks;
    TextView message;
    Button searchBtn;
    ProgressBar progressBar;
    ArrayList<BookInfo> bookInfoArrayList;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchBtn = view.findViewById(R.id.search_button);
        searchBooks = view.findViewById(R.id.search_text);
        progressBar = view.findViewById(R.id.LoadingPB);
        recyclerView = view.findViewById(R.id.search_recview);
        message = view.findViewById(R.id.textViewMessage);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                message.setVisibility(View.GONE);
                if (searchBooks.getText().toString().isEmpty()) {
                    searchBooks.setError("Please enter search query");
                    return;
                }

                String searchTerm = "intitle:";
                searchTerm = searchBooks.getText().toString();
                searchTerm = searchTerm + "&maxResults=40";
                getBooksInfo(searchTerm);

                try {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


        return view;
    }

    private void getBooksInfo(String query) {

        bookInfoArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();

        //below is the url for getting data from API in json format.
        url = "https://www.googleapis.com/books/v1/volumes?q=" + query;
        //below line we are  creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        //below line is use to make json object request inside that we are passing url, get method and getting json object. .
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressBar.setVisibility(View.GONE);

                //inside on response method we are extracting all our json data.

                JSONArray items = null;
                try {
                    items = response.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < items.length(); i++) {
                    try {
                        JSONObject item = items.getJSONObject(i);
                        JSONObject volumeObj = item.getJSONObject("volumeInfo");

                        String title = volumeObj.getString("title");
                        String author = "", thumbnail = "";

                        JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");

                        if (imageLinks != null) {
                            thumbnail = imageLinks.optString("smallThumbnail");
                            thumbnail = thumbnail.replace("http:", "https:");
                        }

                        if (imageLinks == null) {
                            continue;
                        }

                        JSONArray authors = volumeObj.getJSONArray("authors");
                        if (authors.length() == 1) {
                            author = authors.getString(0);
                        } else {
                            author = authors.getString(0) + "|" + authors.getString(1);
                        }

                        //after extracting all the data we are saving this data in our model class.
                        BookInfo bookInfo = new BookInfo(title, author, thumbnail, url);
                        //below line is use to pass our model class in our array list.
                        //System.out.println(bookInfo.getmAuthors() + bookInfo.getmTitle());
                        bookInfoArrayList.add(bookInfo);
                        //below line is use to pass our array list in adapter class.
                        BookAdapter adapter = new BookAdapter(bookInfoArrayList, getContext());
                        //below line is use to add linear layout manager for our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        //in below line we are setting layout manager and adapter to our recycler view.
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //also displaying error message in toast.
                Toast.makeText(getContext(), "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        //at last we are adding our json object request in our request queue.
        queue.add(booksObjrequest);

    }
}