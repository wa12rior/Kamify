package wa12rior.gmail.com.kamify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Song> songs = new ArrayList<Song>();

//        Adding songs to the list

        songs.add(new Song(R.string.first_title, R.string.author, R.string.first_duration, R.raw.wowa_ballerina));
        songs.add(new Song(R.string.second_title, R.string.author, R.string.second_duration, R.raw.wowa_bluesky));
        songs.add(new Song(R.string.third_title, R.string.author, R.string.third_duration, R.raw.wowa_pleasewait));

//        Setting up adapter

        SongAdapter adapter = new SongAdapter(getApplicationContext(), songs);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

//        Setting click listener on every item in list view

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Starting new intent with song

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Creating, starting new intent and sending data within

                Intent intent = new Intent(getApplicationContext(), NowPlayingActivity.class);
                Song song = songs.get(position);
                intent.putExtra("author", song.getSongAuthor());
                intent.putExtra("name", song.getSongName());
                intent.putExtra("audio", song.getAudioResourceId());

                startActivity(intent);
            }
        });
    }
}
