package com.myprog.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameLogic {
    private Activity activity;
    private static int playerTextViewCounter = 0;
    private static int enemyTextViewCounter = 0;
    private int playerTurn = 2;
    private int enemyTurn = 1;
    private int whoIsWin;
    private int whoTurn = 0;
    private int gameField[][];
    private ImageView arrayImage[] = new ImageView[9];
    private ImageView button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private TextView playerCounter, enemyCounter;
    private Button buttonStart1, buttonStart2;
    Handler handler = new Handler();

    GameLogic(final Activity activity){
        this.activity = activity;
    }

    public void starGame(){
        this.gameField = new int[3][3];
        playerCounter = activity.findViewById(R.id.playerTextViewCounter);
        enemyCounter = activity.findViewById(R.id.enemyTextViewCounter);
        playerCounter.setText(String.valueOf(playerTextViewCounter));
        enemyCounter.setText(String.valueOf(enemyTextViewCounter));

        button1 = activity.findViewById(R.id.imageView1);
        button2 = activity.findViewById(R.id.imageView2);
        button3 = activity.findViewById(R.id.imageView3);
        button4 = activity.findViewById(R.id.imageView4);
        button5 = activity.findViewById(R.id.imageView5);
        button6 = activity.findViewById(R.id.imageView6);
        button7 = activity.findViewById(R.id.imageView7);
        button8 = activity.findViewById(R.id.imageView8);
        button9 = activity.findViewById(R.id.imageView9);

        arrayImage[0] = button1;
        arrayImage[1] = button2;
        arrayImage[2] = button3;
        arrayImage[3] = button4;
        arrayImage[4] = button5;
        arrayImage[5] = button6;
        arrayImage[6] = button7;
        arrayImage[7] = button8;
        arrayImage[8] = button9;

        for(int i = 0; i < 9; i++) {
            arrayImage[i].setEnabled(true);
            arrayImage[i].setImageDrawable(null);
            arrayImage[i].setColorFilter(Color.argb(255,0,0,0));
        }

        if(whoTurn == 0) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_start);
            dialog.show();
            buttonStart1 = dialog.findViewById(R.id.buttonDialogStart1);
            buttonStart2 = dialog.findViewById(R.id.buttonDialogStart2);

            buttonStart1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whoTurn = 1;
                    dialog.dismiss();
                    gameLogic();
                }
            });
            buttonStart2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whoTurn = 2;
                    dialog.dismiss();
                    gameLogic();
                }
            });
        }
        else{
            gameLogic();
        }
    }


    private boolean checkIsDraw(){
        for (int i = 0; i < gameField.length; i++) {
            for(int t = 0; t < gameField.length; t++){
                if(gameField[i][t] == 0)
                   return false;
            }
        }
        whoIsWin = 0;
        return true;
    }

    private boolean checkIsWin() {
        //Горизонтальные линии
        for (int i = 0; i < gameField.length; i++) {
            if (gameField[i][0] == gameField[i][1]
                    & gameField[i][1] == gameField[i][2]
                    & gameField[i][0] != 0) {
                if(gameField[i][0] == playerTurn){
                    arrayImage[fromArrayToIndex(i, 0)].setColorFilter(Color.argb(255,0,255,0));
                    arrayImage[fromArrayToIndex(i, 1)].setColorFilter(Color.argb(255,0,255,0));
                    arrayImage[fromArrayToIndex(i, 2)].setColorFilter(Color.argb(255,0,255,0));
                    whoIsWin = playerTurn;
                } else {
                    arrayImage[fromArrayToIndex(i, 0)].setColorFilter(Color.argb(255,255,0,0));
                    arrayImage[fromArrayToIndex(i, 1)].setColorFilter(Color.argb(255,255,0,0));
                    arrayImage[fromArrayToIndex(i, 2)].setColorFilter(Color.argb(255,255,0,0));
                    whoIsWin = enemyTurn;
                }

                return true;
            }
        }
        //Вертиклаьные линии
        for (int i = 0; i < gameField.length; i++) {
            if (gameField[0][i] == gameField[1][i]
                    & gameField[1][i] == gameField[2][i]
                    & gameField[0][i] != 0) {
                if(gameField[0][i] == playerTurn){
                    whoIsWin = playerTurn;
                    arrayImage[fromArrayToIndex(0, i)].setColorFilter(Color.argb(255,0,255,0));
                    arrayImage[fromArrayToIndex(1, i)].setColorFilter(Color.argb(255,0,255,0));
                    arrayImage[fromArrayToIndex(2, i)].setColorFilter(Color.argb(255,0,255,0));
                } else {
                    whoIsWin = enemyTurn;
                    arrayImage[fromArrayToIndex(0, i)].setColorFilter(Color.argb(255,255,0,0));
                    arrayImage[fromArrayToIndex(1, i)].setColorFilter(Color.argb(255,255,0,0));
                    arrayImage[fromArrayToIndex(2, i)].setColorFilter(Color.argb(255,255,0,0));
                }
                return true;
            }
        }
        //Проверка диагональных линий
        if (gameField[0][0] == gameField[1][1]
                & gameField[1][1] == gameField[2][2]
                & gameField[0][0] != 0) {
            if(gameField[1][1] == playerTurn){
                whoIsWin = playerTurn;
                arrayImage[0].setColorFilter(Color.argb(255,0,255,0));
                arrayImage[4].setColorFilter(Color.argb(255,0,255,0));
                arrayImage[8].setColorFilter(Color.argb(255,0,255,0));
            }else {
                arrayImage[0].setColorFilter(Color.argb(255,255,0,0));
                arrayImage[4].setColorFilter(Color.argb(255,255,0,0));
                arrayImage[8].setColorFilter(Color.argb(255,255,0,0));
                whoIsWin = enemyTurn;
            }
            return true;
        }
        if (gameField[0][2] == gameField[1][1]
                & gameField[1][1] == gameField[2][0]
                & gameField[0][2] != 0) {
            if(gameField[1][1] == playerTurn){
                whoIsWin = playerTurn;
                arrayImage[2].setColorFilter(Color.argb(255,0,255,0));
                arrayImage[4].setColorFilter(Color.argb(255,0,255,0));
                arrayImage[6].setColorFilter(Color.argb(255,0,255,0));
            } else {
                whoIsWin = enemyTurn;
                arrayImage[2].setColorFilter(Color.argb(255,255,0,0));
                arrayImage[4].setColorFilter(Color.argb(255,255,0,0));
                arrayImage[6].setColorFilter(Color.argb(255,255,0,0));
            }
            return true;
        }
        return false;
    }

    public void dialogCreate(){
        final Dialog dialog = new Dialog(activity);
        LinearLayout linearLayout;

        for(int i = 0; i < 9; i++){
            arrayImage[i].setEnabled(false);
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        whoTurn++;

        if(whoIsWin != 0){
            handler.postDelayed(new Runnable() {
                public void run() {
                    dialog.show();
                }
            }, 1500);
        } else{
            handler.postDelayed(new Runnable() {
                public void run() {
                    dialog.show();
                }
            }, 500);
        }


        switch (whoIsWin){
            case 0:
                dialog.setContentView(R.layout.dialog_draw);
                linearLayout = dialog.findViewById(R.id.dialog_draw);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        starGame();
                    }
                });
                break;
            case 1:
                enemyTextViewCounter++;
                dialog.setContentView(R.layout.dialog_lose);
                linearLayout = dialog.findViewById(R.id.dialog_lose);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        starGame();
                    }
                });
                break;
            case 2:
                playerTextViewCounter++;
                dialog.setContentView(R.layout.dialog_win);
                linearLayout = dialog.findViewById(R.id.dialog_win);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        starGame();
                    }
                });
                break;
        }
    }

    private boolean isTheCellFree(int i, int t) {
        if (gameField[i][t] != 0) {
            return false;
        }
        return true;
    }

    public int[] checkTwoInaRow(){
        // Проверка на ряд, своя или игрока
        int indexTwoInaRow[] = new int[2];
        indexTwoInaRow = isTwoInaRow(enemyTurn);
        String log = new String(indexTwoInaRow[0] + " " + indexTwoInaRow[1]);
        Log.d("checkTwoInaRow1", log);
        if(indexTwoInaRow[0] != -1){
            return indexTwoInaRow;
        }
        indexTwoInaRow = isTwoInaRow(playerTurn);
        log = (indexTwoInaRow[0] + " " + indexTwoInaRow[1]);
        Log.d("checkTwoInaRow2", log);
        if(indexTwoInaRow[0] != -1){
            return indexTwoInaRow;
        }
        indexTwoInaRow[0] = -1;
        return indexTwoInaRow;
    }

    private int[] isTwoInaRow(int whoTurn) {
        int indexTwoInaRow[] = {-1, -1};

        // Горизонтальные линии
        for (int i = 0; i < gameField.length; i++) {
            if((gameField[i][0] == gameField[i][1]) &
                    gameField[i][0] == whoTurn &
                    gameField[i][2] == 0 ){
                indexTwoInaRow[0] = i;
                indexTwoInaRow[1] = 2;
                Log.d("g1", "1");
                return indexTwoInaRow;
            }
            if((gameField[i][1] == gameField[i][2]) &
                    gameField[i][1] == whoTurn &
                    gameField[i][0] == 0){
                indexTwoInaRow[0] = i;
                indexTwoInaRow[1] = 0;
                Log.d("g2", "2");
                return indexTwoInaRow;
            }
            if((gameField[i][0] == gameField[i][2]) &
                    gameField[i][0] == whoTurn &
                    gameField[i][1] == 0){
                indexTwoInaRow[0] = i;
                indexTwoInaRow[1] = 1;
                Log.d("g3", "3");
                return indexTwoInaRow;
            }
        }

        // Вертикальные линии
        for (int i = 0; i < gameField.length; i++) {
            if((gameField[0][i] == gameField[1][i]) &
                    gameField[0][i] == whoTurn &
                    gameField[2][i] == 0 ){
                indexTwoInaRow[0] = 2;
                indexTwoInaRow[1] = i;
                Log.d("v1", "1");
                return indexTwoInaRow;
            }
            if((gameField[1][i] == gameField[2][i]) &
                    gameField[1][i] == whoTurn &
                    gameField[0][i] == 0){
                indexTwoInaRow[0] = 0;
                indexTwoInaRow[1] = i;
                Log.d("v2", "2");
                return indexTwoInaRow;
            }
            if((gameField[0][i] == gameField[2][i]) &
                    gameField[0][i] == whoTurn &
                    gameField[1][i] == 0){
                indexTwoInaRow[0] = 1;
                indexTwoInaRow[1] = i;
                Log.d("v3", "3");
                return indexTwoInaRow;
            }
        }

        //Диагонали
        if((gameField[0][0] == gameField[1][1]) &
                gameField[0][0] == whoTurn &
                gameField[2][2] == 0){
            indexTwoInaRow[0] = 2;
            indexTwoInaRow[1] = 2;
            Log.d("d1", "1");
            return indexTwoInaRow;
        }
        if((gameField[1][1] == gameField[2][2]) &
                gameField[1][1] == whoTurn &
                gameField[0][0] == 0){
            indexTwoInaRow[0] = 0;
            indexTwoInaRow[1] = 0;
            Log.d("d2", "2");
            return indexTwoInaRow;
        }
        if((gameField[0][0] == gameField[2][2]) &
                gameField[0][0] == whoTurn &
                gameField[1][1] == 0){
            indexTwoInaRow[0] = 1;
            indexTwoInaRow[1] = 1;
            Log.d("d3", "3");
            return indexTwoInaRow;
        }
        if((gameField[2][0] == gameField[1][1]) &
                gameField[2][0] == whoTurn &
                gameField[0][2] == 0){
            indexTwoInaRow[0] = 0;
            indexTwoInaRow[1] = 2;
            Log.d("d4", "4");
            return indexTwoInaRow;
        }
        if((gameField[1][1] == gameField[0][2]) &
                gameField[1][1] == whoTurn &
                gameField[2][0] == 0){
            indexTwoInaRow[0] = 2;
            indexTwoInaRow[1] = 0;
            Log.d("d5", "5");
            return indexTwoInaRow;
        }
        if((gameField[2][0] == gameField[0][2]) &
                gameField[2][0] == whoTurn &
                gameField[1][1] == 0){
            indexTwoInaRow[0] = 1;
            indexTwoInaRow[1] = 1;
            Log.d("d6", "6");
            return indexTwoInaRow;
        }
        return indexTwoInaRow;
    }

    private void enemyTurn() {
        int res[] = checkTwoInaRow();
        int tmp;
        String text = new String(res[0]+ " " + res[1]);
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
        if(res[0] == -1){
            int indexRandomCell1;
            int indexRandomCell2;
            do {
                indexRandomCell1 = (int) (Math.random() * 3);
                indexRandomCell2 = (int) (Math.random() * 3);
            } while (!(isTheCellFree(indexRandomCell1, indexRandomCell2)));
            gameField[indexRandomCell1][indexRandomCell2] = enemyTurn;
            res[0] = indexRandomCell1;
            res[1] = indexRandomCell2;
            tmp = fromArrayToIndex(indexRandomCell1, indexRandomCell2);
            arrayImage[tmp].setImageResource(R.drawable.cross);
            arrayImage[tmp].setEnabled(false);
            if(checkIsDraw() | checkIsWin())
                dialogCreate();
        }else{
            gameField[res[0]][res[1]] = enemyTurn;
            tmp = fromArrayToIndex(res[0], res[1]);
            arrayImage[tmp].setImageResource(R.drawable.cross);
            arrayImage[tmp].setEnabled(false);
            if(checkIsDraw() | checkIsWin())
                dialogCreate();
        }
    }

    private int fromArrayToIndex(int a, int b){
        switch(a){
            case 0: {
                switch (b) {
                    case 0:
                        return 0;
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                }
            }
            case 1: {
                switch (b) {
                    case 0:
                        return 3;
                    case 1:
                        return 4;
                    case 2:
                        return 5;
                }
            }
            case 2: {
                switch (b) {
                    case 0:
                        return 6;
                    case 1:
                        return 7;
                    case 2:
                        return 8;
                }
            }
        }
        return -1;
    }

    //Переименовать
    private void gameLogic() {
        if(whoTurn % 2 == 0){
            enemyTurn();
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setImageResource(R.drawable.circle);
                button1.setEnabled(false);
                gameField[0][0] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
                }
            });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setImageResource(R.drawable.circle);
                button2.setEnabled(false);
                gameField[0][1] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button3.setImageResource(R.drawable.circle);
                button3.setEnabled(false);
                gameField[0][2] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button4.setImageResource(R.drawable.circle);
                button4.setEnabled(false);
                gameField[1][0] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button5.setImageResource(R.drawable.circle);
                button5.setEnabled(false);
                gameField[1][1] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button6.setImageResource(R.drawable.circle);
                button6.setEnabled(false);
                gameField[1][2] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button7.setImageResource(R.drawable.circle);
                button7.setEnabled(false);
                gameField[2][0] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button8.setImageResource(R.drawable.circle);
                button8.setEnabled(false);
                gameField[2][1] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button9.setImageResource(R.drawable.circle);
                button9.setEnabled(false);
                gameField[2][2] = 2;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(checkIsDraw() | checkIsWin()){
                            dialogCreate();
                        }else{
                            enemyTurn();
                        }
                    }
                }, 500);
            }
        });
    }
}
