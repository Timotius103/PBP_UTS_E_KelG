package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import platformpbp.uajy.quickresto.databinding.ActivityReservationMenuBinding;
import platformpbp.uajy.quickresto.model.Restorant;
import platformpbp.uajy.quickresto.model.listRestorantDummy;

public class ReservationMenu extends AppCompatActivity {
    private ArrayList<Restorant>ListRestorant;
    private FloatingActionButton floatback;
    private RecyclerViewAdapter adapter;
    TextView txtFullName;
    ActivityReservationMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListRestorant=new listRestorantDummy().resto;
        super.onCreate(savedInstanceState);
        String username;
        SharePreferenceClass sp=new SharePreferenceClass(this);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_reservation_menu);
        binding.recyclerResto.setLayoutManager((new LinearLayoutManager(this)));
        adapter=new RecyclerViewAdapter(ReservationMenu.this,ListRestorant);
        binding.setBaru(adapter);

        username = sp.getUsernameS();
        txtFullName=findViewById(R.id.UserName);
        txtFullName.setText(username);
        floatback=findViewById(R.id.floating_back);
    }

    public void backfloat(View view){
        startActivity(new Intent(ReservationMenu.this,Home.class));
    }
}