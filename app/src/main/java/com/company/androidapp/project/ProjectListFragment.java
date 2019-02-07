package com.company.androidapp.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.company.androidapp.R;
import com.company.androidapp.model.Project;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectListFragment extends ListFragment implements OnItemClickListener {

    private Project[] projects;
    private ProgressDialog prDialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateProjects();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(projects[position].getHtmlUrl()));
        startActivity(browserIntent);
    }

    private ArrayAdapter createAdapter(){
        final LayoutInflater inflater = LayoutInflater.from(getActivity());;
            ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(getActivity(), 0, projects){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null)
                    view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                TextView defaultTextView = (TextView) view.findViewById(android.R.id.text1);
                defaultTextView.setText(projects[position].getName());
                return view;
            }
        };
        return adapter;
    }

    private void updateProjects() {
        projects = new Project[0];
        prDialog = new ProgressDialog(getActivity());
        prDialog.setMessage("Please wait...");
        prDialog.show();

        RequestParams params = new RequestParams();
        params.put("q", "android+language:kotlin");
        params.put("page", "1");
        params.put("per_page", "10");
        GitHubRestClient.get("search/repositories", params, new JsonHttpResponseHandler() {

            private void updateView() {
                ProjectListFragment.this.setListAdapter(createAdapter());
                ProjectListFragment.this.getListView().setOnItemClickListener(ProjectListFragment.this);
                if(prDialog != null)
                    prDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
                Toast.makeText(getActivity(), "Ocurrió un problema al realizar la consulta.", Toast.LENGTH_LONG).show();
                updateView();
                t.printStackTrace();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray responseArray = response.getJSONArray("items");;
                    projects = new Project[responseArray.length()];
                    JSONObject responseItem;
                    for(int i = 0; i < responseArray.length(); i++){
                        responseItem = responseArray.getJSONObject(i);
                        projects[i] = new Project(responseItem.getString("name"),
                                                    responseItem.getString("full_name"),
                                                    responseItem.getString("html_url"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Ocurrió un problema al leer la respuesta.", Toast.LENGTH_LONG).show();
                }
                finally {
                    updateView();
                }
            }
        });
    }
}