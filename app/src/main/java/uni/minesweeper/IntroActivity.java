package uni.minesweeper;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.fluidslider.FluidSlider;
import kotlin.Unit;
import uni.minesweeper.model.MinesweeperModel;

public class IntroActivity extends AppCompatActivity {

    private int BOARD_MAX_SIZE = 10;
    private int BOARD_MIN_SIZE = 5;
    private int MINES_MIN = 3;
    private int totalMines;
    private int boardSize;

    FirebaseAuth myAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String userUID;
    String score;
    UserClass user ;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("boardSize", boardSize);
        savedInstanceState.putInt("totalMines", totalMines);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Toolbar toolbar = findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        userUID = intent.getStringExtra("user_key");
        score = intent.getStringExtra("score");

        myAuth = FirebaseAuth.getInstance();
        firebaseUser = myAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance("https://bmeminesweeperhw-default-rtdb.europe-west1.firebasedatabase.app/");
        readUserFromFirebase();

        if (savedInstanceState != null) {
            boardSize = savedInstanceState.getInt("boardSize");
            totalMines = savedInstanceState.getInt("totalMines");
        }

        boardSize = (boardSize == 0) ? BOARD_MIN_SIZE : boardSize;
        totalMines = (totalMines == 0) ? MINES_MIN : totalMines;

        String[] sizesArray = getResources().getStringArray(R.array.sizes);
        String[] levelsArray = getResources().getStringArray(R.array.mines);

        Spinner sizeSpinner = findViewById(R.id.sizeSpinner);
        ArrayAdapter sizeAdapter = new ArrayAdapter(this, R.layout.spinner_item,sizesArray);
        sizeSpinner.setAdapter(sizeAdapter);

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: boardSize = 6; break;
                    case 2: boardSize = 7; break;
                    case 3: boardSize = 8; break;
                    case 4: boardSize = 9; break;
                    case 5: boardSize = 10; break;
                    default: boardSize = 5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner levelSpinner = findViewById(R.id.mineSpinner);
        ArrayAdapter levelAdapter = new ArrayAdapter(this, R.layout.spinner_item,levelsArray);
        levelSpinner.setAdapter(levelAdapter);

        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: totalMines = 8; break;
                    case 2: totalMines = 12; break;
                    case 3: totalMines = 16; break;
                    case 4: totalMines = 20; break;
                    case 5: totalMines = 24; break;
                    default: totalMines = 4;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Button btnPlay = findViewById(R.id.btnPlay);
        final IntroActivity _this = this;
        final MinesweeperModel model = MinesweeperModel.getSingletonInstance();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSize(boardSize);
                model.setTotalMines(totalMines);
                model.resetModel();
                Intent intent = new Intent(_this,PlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user_key",userUID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(IntroActivity.this,RankingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("user_key",myAuth.getCurrentUser().getUid());
        startActivity(intent);
        return true;
    }

    private void readUserFromFirebase(){
        if(userUID!=null) {
            databaseReference = firebaseDatabase.getReference().child("users").child(userUID);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user = snapshot.getValue(UserClass.class);
                    if(score!=null){
                        //updating the score
                        if(Integer.valueOf(score)>Integer.valueOf(user.score)){
                            user.score = score;
                            databaseReference.setValue(user);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
