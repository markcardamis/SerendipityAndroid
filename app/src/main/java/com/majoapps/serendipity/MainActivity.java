package com.majoapps.serendipity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    ImageView dice_picture;		//reference to dice picture
    Random rng=new Random();	//generate random numbers
    SoundPool dice_sound = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    int sound_id;		//Used to control sound stream return by SoundPool
    Handler handler;	//Post message to start roll
    Handler handlerDiceRoll;
    Timer timer=new Timer();	//Used to implement feedback to user
    boolean rolling=false;		//Is die rolling?
    int buttonSet = 0;
    Button bSet1,bSet2,bSet3,bSet4,bSet5,bSet6,bOptionsDialog;
    LinkedHashMap<String, List<String>> Levels_Category;
    List<String> Levels_List;
    LevelsAdapter adapter;
    String message;
    TextView tvDialogMessage;

    int level = 1;
    Boolean visibilityToggle;
    SharedPreferences settingsValues;
    private Tracker mTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSet1 = (Button)findViewById(R.id.bSet1);
        bSet2 = (Button)findViewById(R.id.bSet2);
        bSet3 = (Button)findViewById(R.id.bSet3);
        bSet4 = (Button)findViewById(R.id.bSet4);
        bSet5 = (Button)findViewById(R.id.bSet5);
        bSet6 = (Button)findViewById(R.id.bSet6);
        bOptionsDialog = (Button)findViewById(R.id.bOptionsDialog);

        settingsValues = PreferenceManager.getDefaultSharedPreferences(this);

        Levels_Category = DataProvider.getInfo();
        Levels_List = new ArrayList<>(Levels_Category.keySet());
        adapter = new LevelsAdapter(this, Levels_Category, Levels_List);

        bSet1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSet = 1;
            }
        });
        bSet2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSet = 2;
            }
        });
        bSet3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSet = 3;
            }
        });
        bSet4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSet = 4;
            }
        });
        bSet5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSet = 5;
            }
        });
        bSet6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSet = 6;
            }
        });

        bOptionsDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                message = "";
                for (int k = 0; k < adapter.getChildrenCount(level-1); k++) {
                    message = message + Integer.toString(k+1) + ". " + adapter.getChild(level-1, k).toString() + "\n";
                }

                // Create custom dialog object
                final Dialog dialog = new Dialog(MainActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                tvDialogMessage = (TextView)dialog.findViewById(R.id.textDialog);
                // Set dialog title
                dialog.setTitle(Levels_List.get(level-1));

                // set values for custom dialog components - text, image and button
                tvDialogMessage.setText(message);


                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                final ImageButton levelUpButton = (ImageButton) dialog.findViewById(R.id.bLevelUpDialog);
                final ImageButton levelDownButton = (ImageButton) dialog.findViewById(R.id.bLevelDownDialog);

                if (level < 2)
                    levelDownButton.setVisibility(View.INVISIBLE);
                else
                    levelDownButton.setVisibility(View.VISIBLE);

                if (level == Levels_Category.size())
                    levelUpButton.setVisibility(View.INVISIBLE);
                else
                    levelUpButton.setVisibility(View.VISIBLE);

                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                        bOptionsDialog.setText(Levels_List.get(level - 1));
                    }
                });

                levelUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        level = level + 1;
                        if (level > Levels_List.size())
                            level = Levels_List.size();

                        dialog.setTitle(Levels_List.get(level - 1));

                        message = "";
                        for (int k = 0; k < adapter.getChildrenCount(level-1); k++) {
                            message = message + Integer.toString(k+1) + ". " + adapter.getChild(level-1, k).toString() + "\n";
                        }
                        tvDialogMessage.setText(message);

                        if (level < 2)
                            levelDownButton.setVisibility(View.INVISIBLE);
                        else
                            levelDownButton.setVisibility(View.VISIBLE);

                        if (level == Levels_Category.size())
                            levelUpButton.setVisibility(View.INVISIBLE);
                        else
                            levelUpButton.setVisibility(View.VISIBLE);

                    }
                });

                levelDownButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        level = level - 1;
                        if (level < 1)
                            level = 1;

                        dialog.setTitle(Levels_List.get(level - 1));

                        message = "";
                        for (int k = 0; k < adapter.getChildrenCount(level-1); k++) {
                            message = message + Integer.toString(k+1) + ". " + adapter.getChild(level-1, k).toString() + "\n";
                        }
                        tvDialogMessage.setText(message);

                        if (level < 2)
                            levelDownButton.setVisibility(View.INVISIBLE);
                        else
                            levelDownButton.setVisibility(View.VISIBLE);

                        if (level == Levels_Category.size())
                            levelUpButton.setVisibility(View.INVISIBLE);
                        else
                            levelUpButton.setVisibility(View.VISIBLE);
                    }
                });
            }
        });



        //load dice sound
        sound_id=dice_sound.load(this,R.raw.shake_dice,1);
        //get reference to image widget
        dice_picture = (ImageView) findViewById(R.id.imageView1);
        dice_picture.setImageResource(R.drawable.dice3droll);
        //link handler to callback
        handler=new Handler(callback);
        handlerDiceRoll = new Handler();

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

		/*
		int childCount1 = adapter.getChildrenCount(0);
	 	for (int i = 0; i < Levels_List.size(); i++) {
			for (int j = 0; j < adapter.getChildrenCount(0); j++) {
				Log.i("" + Levels_List.get(i), adapter.getChild(0, j).toString());
			}
		}
		*/

    }

    //User pressed dice, lets start
    public void HandleClick(View arg0) {
        if(!rolling) {
            rolling=true;
            start();
            //Start rolling sound
            dice_sound.play(sound_id,1.0f,1.0f,0,0,1.0f);
            //Pause to allow image to update
            timer.schedule(new Roll(), 1150);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final Random random = new Random();
            int i = random.nextInt(5) + 1;
            if(rolling) {
                switch (i) {
                    case 1:
                        dice_picture.setImageResource(R.drawable.one);
                        break;
                    case 2:
                        dice_picture.setImageResource(R.drawable.two);
                        break;
                    case 3:
                        dice_picture.setImageResource(R.drawable.three);
                        break;
                    case 4:
                        dice_picture.setImageResource(R.drawable.four);
                        break;
                    case 5:
                        dice_picture.setImageResource(R.drawable.five);
                        break;
                    case 6:
                        dice_picture.setImageResource(R.drawable.six);
                        break;
                    default:
                }
                start();
            }
        }
    };

    public void start() {
        handlerDiceRoll.postDelayed(runnable, 200);
    }

    public void stop() {
        handlerDiceRoll.removeCallbacks(runnable);
    }


    //When pause completed message sent to callback
    class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    //Receives message from timer to start dice roll
    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            rolling=false;	//user can press again
            stop();
            //Get roll result
            //Remember nextInt returns 0 to 5 for argument of 6
            //hence + 1
            if (buttonSet == 0) {
                switch (rng.nextInt(6) + 1) {
                    case 1:
                        dice_picture.setImageResource(R.drawable.one);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 0).toString());
                        break;
                    case 2:
                        dice_picture.setImageResource(R.drawable.two);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 1).toString());
                        break;
                    case 3:
                        dice_picture.setImageResource(R.drawable.three);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 2).toString());
                        break;
                    case 4:
                        dice_picture.setImageResource(R.drawable.four);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 3).toString());
                        break;
                    case 5:
                        dice_picture.setImageResource(R.drawable.five);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 4).toString());
                        break;
                    case 6:
                        dice_picture.setImageResource(R.drawable.six);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 5).toString());
                        break;
                    default:
                }
            }
            else
            {
                switch (buttonSet){
                    case 1:
                        dice_picture.setImageResource(R.drawable.one);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 0).toString());
                        if (visibilityToggle) {
                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory(Levels_List.get(level - 1))
                                    .setAction(adapter.getChild(level - 1, 0).toString())
                                    .build());
                        }
                        break;
                    case 2:
                        dice_picture.setImageResource(R.drawable.two);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 1).toString());
                        if (visibilityToggle) {
                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory(Levels_List.get(level - 1))
                                    .setAction(adapter.getChild(level - 1, 1).toString())
                                    .build());
                        }
                        break;
                    case 3:
                        dice_picture.setImageResource(R.drawable.three);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 2).toString());
                        if (visibilityToggle) {
                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory(Levels_List.get(level - 1))
                                    .setAction(adapter.getChild(level - 1, 2).toString())
                                    .build());
                        }
                        break;
                    case 4:
                        dice_picture.setImageResource(R.drawable.four);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 3).toString());
                        if (visibilityToggle) {
                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory(Levels_List.get(level - 1))
                                    .setAction(adapter.getChild(level - 1, 3).toString())
                                    .build());
                        }
                        break;
                    case 5:
                        dice_picture.setImageResource(R.drawable.five);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 4).toString());
                        if (visibilityToggle) {
                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory(Levels_List.get(level - 1))
                                    .setAction(adapter.getChild(level - 1, 4).toString())
                                    .build());
                        }
                        break;
                    case 6:
                        dice_picture.setImageResource(R.drawable.six);
                        bOptionsDialog.setText(adapter.getChild(level - 1, 5).toString());
                        if (visibilityToggle) {
                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory(Levels_List.get(level - 1))
                                    .setAction(adapter.getChild(level - 1, 5).toString())
                                    .build());
                        }
                        break;
                    default:
                }
            }
            buttonSet = 0;
            return true;
        }
    };

    public void infoPageJump(View view) {
        Intent i = new Intent(MainActivity.this, Settings.class);
        Student student;
        student = new Student(Levels_Category);
        i.putExtra("hashmapMain",student);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            Student sHashmap = data.getParcelableExtra("hashmap");
            if (sHashmap.msLinkedHashMap.size() > 0)
                Levels_Category = sHashmap.msLinkedHashMap;
            if (Levels_Category == null)
                Levels_Category = DataProvider.getInfo();

            Levels_List = new ArrayList<>(Levels_Category.keySet());
            adapter = new LevelsAdapter(this, Levels_Category, Levels_List);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        level = 1;
        bOptionsDialog.setText(Levels_List.get(level - 1));

        visibilityToggle = settingsValues.getBoolean("Settings", Boolean.FALSE);
        if (visibilityToggle) {
            bOptionsDialog.setVisibility(View.VISIBLE);
        }
        else {
            bOptionsDialog.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        dice_sound.pause(sound_id);
    }

    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
