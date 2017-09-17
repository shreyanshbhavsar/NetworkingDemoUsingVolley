package shreyansh.bhavsar.networkingdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String GET_URL = "http://shreyanshbhavsar.esy.es/allusers.php";
    EditText nameEt,passwordEt;
    Button registerbtn;

    String name,password;
    String REG_URL = "http://shreyanshbhavsar.esy.es/registration.php";

    ProgressDialog dialog;

    List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nameEt = (EditText) findViewById(R.id.usernameet);
        passwordEt = (EditText) findViewById(R.id.passwordet);
        registerbtn = (Button)findViewById(R.id.registerbtn);

        players = new ArrayList<>();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("Please Wait");
                dialog.setMessage("Creating User....");
                dialog.show();





                // code to send data to server
                name = nameEt.getText().toString();
                password = passwordEt.getText().toString();

                //MAke a new Request

                StringRequest request = new StringRequest(Request.Method.POST, REG_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            dialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            int res = object.getInt("success");
                            if (res == 1)
                            {
                                Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params = new HashMap<String, String>();
                        params.put("name",name);
                        params.put("password",password);
                        return params;

                    }
                };

                Volley.newRequestQueue(MainActivity.this).add(request);

            }
        });

    }

     public void getData(View v)
    {

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Fetching Players....");
        dialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, GET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();

                        System.out.println(response);


                        try {
                            JSONObject obj = new JSONObject(response);
                            int res = obj.getInt("success");
                            if(res == 1)
                            {
                                JSONArray array = obj.getJSONArray("data");

                                for(int i=0;i<array.length(); i++)
                                {

                                    JSONObject dataObject = array.getJSONObject(i);
                                    String name = dataObject.getString("name");
                                    String role = dataObject.getString("role");
                                    String country = dataObject.getString("country");
                                    String runs = dataObject.getString("runs");

                                    players.add(new Player(name,role,country,runs));
                                }

                            for(Player player:players)
                            {

                                Toast.makeText(MainActivity.this,""+player,Toast.LENGTH_SHORT).show();

                            }



                            }















                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        Volley.newRequestQueue(MainActivity.this).add(request);
    }
}
